package dk.frv.eavdam.menus;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

/**
 * This class represents a menu item that opens a frame where the user can view
 * the list of AIS VHF Health Check rules.
 */
public class ListOfRulesMenuItem extends JMenuItem {

    public static final long serialVersionUID = 1L;

    public ListOfRulesMenuItem(EavdamMenu eavdamMenu) {        
        super("AIS VHF Datalink Rules");                
        addActionListener(new ListOfRulesActionListener(eavdamMenu));
    }
	
}
 
class ListOfRulesActionListener implements ActionListener {

    private EavdamMenu eavdamMenu;                                                   
    private JDialog dialog;
     
    public ListOfRulesActionListener(EavdamMenu eavdamMenu) {
        super();
        this.eavdamMenu = eavdamMenu;
    }
                   		   
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof ListOfRulesMenuItem) {

            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "AIS VHF Datalink Rules", false);

			JEditorPane editorPane = new JEditorPane();
			editorPane.setEditable(false);
			URL rulesURL = ListOfRulesMenuItem.class.getClassLoader().getResource("share/data/rules.html");
			if (rulesURL != null) {
				try {
					editorPane.setPage(rulesURL);
				} catch (IOException ex) {
					System.err.println(ex.getMessage());
					ex.printStackTrace();
				}
			} else {
				System.out.println("Did not find rules.html");
			}

			JScrollPane editorScrollPane = new JScrollPane(editorPane);
			editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			editorScrollPane.setPreferredSize(new Dimension(600, 440));
			editorScrollPane.setMinimumSize(new Dimension(100, 50));			
			
            dialog.getContentPane().add(editorScrollPane);
                                                                         
            int frameWidth = 640;
            int frameHeight = 480;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
                (int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
            dialog.setVisible(true);
        }
    }

}

