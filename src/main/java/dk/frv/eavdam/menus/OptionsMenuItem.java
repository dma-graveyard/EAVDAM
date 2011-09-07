package dk.frv.eavdam.menus;

import dk.frv.eavdam.data.Options;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.JAXBException;

/**
 * This class represents a menu item that opens a frame where the user can edit
 * application's options.
 */
public class OptionsMenuItem extends JMenuItem {

    public static final long serialVersionUID = 3L;

    public OptionsMenuItem(EavdamMenu eavdamMenu) {        
        super("Options");                
        addActionListener(new OptionsActionListener(this, eavdamMenu));
    }
    
    /**
     * Loads options
     *
     * @return  Options
     */
    public static Options loadOptions() {

        Options op = new Options();

        StringBuilder contents = new StringBuilder();
    
        try {
            BufferedReader input =  new BufferedReader(new FileReader("data/options.txt"));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    if (line.startsWith("icons:")) {
                        String temp = line.substring(7);
                        if (temp.equals("large")) {
                            op.setIconsSize(Options.LARGE_ICONS);
                        } else if (temp.equals("small")) {
                            op.setIconsSize(Options.SMALL_ICONS);
                        }
                    }
                }
            } finally {
                input.close();
            }    
        } catch (IOException ex){
            System.out.println("Options file could not be loaded");
        }
    
        return op;
    }    

}
 
class OptionsActionListener implements ActionListener, DocumentListener {

    private EavdamMenu eavdamMenu;
                                                         
    private JDialog dialog;
    
    private JComboBox iconsSizeComboBox;

    private JButton saveButton;
    private JButton cancelButton;
    
    private Options options;                   
    
    private OptionsMenuItem optionsMenuItem;
              
    public OptionsActionListener(OptionsMenuItem optionsMenuItem, EavdamMenu eavdamMenu) {
        super();
        this.optionsMenuItem = optionsMenuItem;
        this.eavdamMenu = eavdamMenu;
    }
                   
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof OptionsMenuItem) {

            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "Options", true);
        
            options = optionsMenuItem.loadOptions();               
        
            iconsSizeComboBox = new JComboBox
                (new String[] {"Large", "Small"});
                
            if (options.getIconsSize() == Options.LARGE_ICONS) {
                iconsSizeComboBox.setSelectedIndex(0);
            } else if (options.getIconsSize() == Options.SMALL_ICONS) {
                iconsSizeComboBox.setSelectedIndex(1);
            }
                
            iconsSizeComboBox.addActionListener(this);            

            
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());                  
            
            JPanel p1 = new JPanel(new GridBagLayout());
            p1.setBorder(BorderFactory.createTitledBorder("General"));
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;                   
            c.anchor = GridBagConstraints.LINE_START;
            c.insets = new Insets(5,5,5,5);
            p1.add(new JLabel("Icons size:"), c);
            c.gridx = 1;                
            p1.add(iconsSizeComboBox, c);           
            
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            panel.add(p1, c);

            saveButton = new JButton("Save", null);        
            saveButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            saveButton.setHorizontalTextPosition(AbstractButton.CENTER);
            saveButton.setPreferredSize(new Dimension(100, 20));
            saveButton.setMaximumSize(new Dimension(100, 20));
            saveButton.addActionListener(this);
                
            cancelButton = new JButton("Cancel", null);        
            cancelButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            cancelButton.setHorizontalTextPosition(AbstractButton.CENTER);
            cancelButton.setPreferredSize(new Dimension(100, 20));
            cancelButton.setMaximumSize(new Dimension(100, 20));                              
            cancelButton.addActionListener(this);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(saveButton);
            saveButton.setEnabled(false);
            buttonPanel.add(cancelButton);                    
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 2;
            c.anchor = GridBagConstraints.CENTER;                    
            panel.add(buttonPanel, c);

            dialog.getContentPane().add(panel);
                                                                         
            int frameWidth = 320;
            int frameHeight = 240;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
            dialog.setVisible(true);
        
        } else if (saveButton != null && e.getSource() == saveButton) {
            
            boolean success = saveOptions();
            
            if (success) {
                saveButton.setEnabled(false);
                eavdamMenu.getStationLayer().updateIconsOnMap();
                dialog.dispose();
            }

        } else if (cancelButton != null && e.getSource() == cancelButton) {
            if (saveButton.isEnabled()) {
                int response = JOptionPane.showConfirmDialog(dialog, "Do you want to save the changes before exiting?", "Confirm action", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    boolean success = saveOptions();
                    if (success) {
                        dialog.dispose();
                    }
                } else if (response == JOptionPane.NO_OPTION) {                    
                    dialog.dispose();
                }
            } else {
                dialog.dispose();                  
            }
        
        } else if (saveButton != null) {
            if (isChanged()) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
        }
    }

    public void changedUpdate(DocumentEvent e) {
        if (saveButton != null) {
            if (isChanged()) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
        }
    }  
    
    public void insertUpdate(DocumentEvent e) {
        if (saveButton != null) {
            if (isChanged()) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
        }
    } 
    
    public void removeUpdate(DocumentEvent e) {
        if (saveButton != null) {
            if (isChanged()) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
        }
    }

    /** 
     * Saves options.
     *
     * @return  True if the save was succesful, false otherwise
     */        
    private boolean saveOptions() {

        try {
            File file = new File("data");
            if (!file.exists()) {
                file.mkdir();
            }
            String data = "icons: ";
            if (iconsSizeComboBox.getSelectedIndex() == 0) {
                data += "large\n";
            } else if (iconsSizeComboBox.getSelectedIndex() == 1) {
                data += "small\n";
            }
            File optionsFile = new File("data/options.txt");
            FileWriter fw = new FileWriter(optionsFile);
            fw.write(data);
            fw.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return true;
    }

    /** 
     * Checks whether the form fields have changed.
     *
     * @return  True if the fields have changed, false if not
     */    
    private boolean isChanged() {
        
        if (options == null) {
            return true;
        }
        
        if (options.getIconsSize() == Options.LARGE_ICONS && iconsSizeComboBox.getSelectedIndex() != 0) {
            return true;
        } else if (options.getIconsSize() == Options.SMALL_ICONS && iconsSizeComboBox.getSelectedIndex() != 1) {
            return true;
        }
       
        return false;        
    }
}

