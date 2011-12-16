/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.menus;

import com.bbn.openmap.Layer;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.OtherUserStations;
import dk.frv.eavdam.data.Simulation;
import dk.frv.eavdam.layers.OMAISShapeLayer;
import dk.frv.eavdam.utils.DBHandler;
import dk.frv.eavdam.utils.ShapeFileFilter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * This class represents a menu item where the user can select which shape layers are shown on the map.
 */
public class ShapeLayersMenu extends JMenuItem {

    public static final long serialVersionUID = 3L;
	
	public final static String SHAPE_FILE_NAME = "initialShapeFiles.txt";
	
	private List<String> shapeFilePathNames;	
	private List<JCheckBox> shapeLayersCheckBoxes;		
	
    public ShapeLayersMenu(EavdamMenu eavdamMenu) {        
        super("Shape File Layers");		
		
		shapeFilePathNames = new ArrayList<String>();
		shapeLayersCheckBoxes  = new ArrayList<JCheckBox>(); 

		try {
			FileInputStream fstream = new FileInputStream(SHAPE_FILE_NAME);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				File temp = new File(strLine);
				if (temp.exists()) {
					shapeFilePathNames.add(strLine);
					JCheckBox shapeLayersCheckBox = new JCheckBox(temp.getName());
					shapeLayersCheckBox.setSelected(false);
					shapeLayersCheckBoxes.add(shapeLayersCheckBox);
				}
			}
			in.close();
		} catch (Exception e) {}
		
        addActionListener(new ShapeLayersActionListener(this, eavdamMenu));
	}
	
	public List<String> getShapeFilePathNames() {
		return shapeFilePathNames;
	}
	
	public List<JCheckBox> getShapeLayersCheckBoxes() {
		return shapeLayersCheckBoxes;
	}

}
	
class ShapeLayersActionListener implements ActionListener {

    private EavdamMenu eavdamMenu;	
    private ShapeLayersMenu shapeLayersMenu;

	private JDialog dialog;

	private JFileChooser fileChooser;	
	
	private List<Boolean> initialSelections;
	private List<JButton> deleteButtons;
	
	private JButton openButton;	
	private JButton saveButton;
	private JButton cancelButton;
	
    public ShapeLayersActionListener(ShapeLayersMenu shapeLayersMenu, EavdamMenu eavdamMenu) {
        super();
        this.eavdamMenu = eavdamMenu;		
        this.shapeLayersMenu = shapeLayersMenu;
		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileFilter(new ShapeFileFilter());
	}
                   
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof ShapeLayersMenu) {
	
			updateDialog();
			
		} else if (e.getSource() == openButton) {
		
			int returnVal = fileChooser.showOpenDialog(dialog);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
				if (file.getName().endsWith(".shp")) {
					if (!shapeLayersMenu.getShapeFilePathNames().contains(file.getPath())) {
						shapeLayersMenu.getShapeFilePathNames().add(file.getPath());				
						JCheckBox shapeLayersCheckBox = new JCheckBox(file.getName());
						shapeLayersCheckBox.setSelected(false);
						shapeLayersMenu.getShapeLayersCheckBoxes().add(shapeLayersCheckBox);
						updateDialog();
					}
				}
            }		
		
		} else if (e.getSource() == saveButton) {
		
			try {
				new File(ShapeLayersMenu.SHAPE_FILE_NAME).delete();
			} catch (Exception ex) {}
			try {
				FileWriter fstream = new FileWriter(ShapeLayersMenu.SHAPE_FILE_NAME);
				BufferedWriter out = new BufferedWriter(fstream);
				for (String s: shapeLayersMenu.getShapeFilePathNames()) {
					out.write(s + "\n");					
				}
				out.close();
			} catch (Exception ex) {}
		
			Layer[] currentLayers = eavdamMenu.getLayerHandler().getMapLayers();
			for (Layer currentLayer : currentLayers) {
				if (currentLayer instanceof OMAISShapeLayer) {
					eavdamMenu.getLayerHandler().removeLayer(currentLayer);
				}
			}
		
			for (int i=0; i<shapeLayersMenu.getShapeLayersCheckBoxes().size(); i++) {
				JCheckBox shapeLayersCheckBox = shapeLayersMenu.getShapeLayersCheckBoxes().get(i);
				if (shapeLayersCheckBox.isSelected() && i < shapeLayersMenu.getShapeFilePathNames().size()) {
					OMAISShapeLayer shapeLayer = new OMAISShapeLayer(shapeLayersMenu.getShapeFilePathNames().get(i));
					eavdamMenu.getLayerHandler().addLayer(shapeLayer);
				}
			}
			dialog.dispose();    
			
		} else if (e.getSource() == cancelButton) {
		
			for (int i=0; i<shapeLayersMenu.getShapeLayersCheckBoxes().size(); i++) {
				if (i < initialSelections.size()) {
					JCheckBox shapeLayersCheckBox = shapeLayersMenu.getShapeLayersCheckBoxes().get(i);
					shapeLayersCheckBox.setSelected(initialSelections.get(i).booleanValue());
				}
			}
			
			dialog.dispose();
		
		} else if (deleteButtons != null) {
		
			for (int i=0; i<deleteButtons.size(); i++) {		
				if (e.getSource() == deleteButtons.get(i)) {
					if (i<shapeLayersMenu.getShapeLayersCheckBoxes().size()) {
						shapeLayersMenu.getShapeLayersCheckBoxes().remove(i);
					}
					if (i<shapeLayersMenu.getShapeFilePathNames().size()) {
						shapeLayersMenu.getShapeFilePathNames().remove(i);
					}
					if (i < initialSelections.size()) {
						initialSelections.remove(i);
					}
				}
			}
			updateDialog();			
		}
	}		
	
	private void updateDialog() {
	
		if (dialog != null) {
            dialog.dispose();
        }

		dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "Shape File Layers", true);			
	
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());  
		GridBagConstraints c = new GridBagConstraints();    
		c.gridx = 0;			
		c.gridy = 0;	
		c.gridwidth = 3;
		c.insets = new Insets(5, 5, 5, 5);				
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		openButton = new JButton("Load a shape file", new ImageIcon("share/data/images/open.gif"));
		openButton.addActionListener(this);	
		panel.add(openButton, c);
		
		JPanel shapeLayersPane = new JPanel();
		shapeLayersPane.setLayout(new GridBagLayout());   
		c.insets = new Insets(0, 0, 0, 0);
		
		GridBagConstraints horizontalFill = new GridBagConstraints();
		horizontalFill.anchor = GridBagConstraints.WEST;
		horizontalFill.fill = GridBagConstraints.HORIZONTAL;   		
		horizontalFill.gridx = 2;
		
		deleteButtons = new ArrayList<JButton>();		
		if (initialSelections == null) {
			initialSelections = new ArrayList<Boolean>();				
			for (JCheckBox shapeLayersCheckBox : shapeLayersMenu.getShapeLayersCheckBoxes()) {			
				initialSelections.add(new Boolean(shapeLayersCheckBox.isSelected()));
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				c.gridwidth = 1;
				c.gridx = 0;				
				shapeLayersPane.add(shapeLayersCheckBox, c);
				c.gridx = 1;
				c.anchor = GridBagConstraints.LINE_START;
				JButton deleteButton = new JButton(new ImageIcon("share/data/images/delete.png"));
				deleteButton.setBorder(null);
				deleteButton.setBorderPainted(false);
				deleteButton.setPreferredSize(new Dimension(16,16));
				deleteButton.addActionListener(this);
				deleteButtons.add(deleteButton);
				shapeLayersPane.add(deleteButton, c);			
				shapeLayersPane.add(Box.createHorizontalGlue(), horizontalFill);
				c.gridy++;
			}
		} else {
			for (JCheckBox shapeLayersCheckBox : shapeLayersMenu.getShapeLayersCheckBoxes()) {
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				c.gridwidth = 1;
				c.gridx = 0;
				shapeLayersPane.add(shapeLayersCheckBox, c);
				c.gridx = 1;				
				c.anchor = GridBagConstraints.LINE_START;				
				JButton deleteButton = new JButton(new ImageIcon("share/data/images/delete.png"));
				deleteButton.setBorder(null);
				deleteButton.setBorderPainted(false);
				deleteButton.setPreferredSize(new Dimension(16,16));
				deleteButton.addActionListener(this);
				deleteButtons.add(deleteButton);
				shapeLayersPane.add(deleteButton, c);				
				shapeLayersPane.add(Box.createHorizontalGlue(), horizontalFill);
				c.gridy++;
			}			
		}

		JScrollPane scrollPane = new JScrollPane(shapeLayersPane);
		scrollPane.setBorder(BorderFactory.createTitledBorder(""));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setMinimumSize(new Dimension(400, 360));
		scrollPane.setPreferredSize(new Dimension(400, 360));
		scrollPane.setMaximumSize(new Dimension(400, 360));        
		
		c.gridy = 2;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(5, 5, 5, 5);					
		panel.add(scrollPane, c);

		saveButton = new JButton("Save and exit", null);        
		saveButton.setVerticalTextPosition(AbstractButton.BOTTOM);
		saveButton.setHorizontalTextPosition(AbstractButton.CENTER);
		saveButton.setPreferredSize(new Dimension(130, 20));
		saveButton.setMaximumSize(new Dimension(130, 20));
		saveButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel", null);        
		cancelButton.setVerticalTextPosition(AbstractButton.BOTTOM);
		cancelButton.setHorizontalTextPosition(AbstractButton.CENTER);
		cancelButton.setPreferredSize(new Dimension(100, 20));
		cancelButton.setMaximumSize(new Dimension(100, 20));                              
		cancelButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.add(saveButton, c);
		c.gridx = 1;
		buttonPanel.add(cancelButton, c);
		
		c.gridx = 0;
		c.gridy = 3;	
		c.anchor = GridBagConstraints.CENTER;     
		panel.add(buttonPanel, c);
		
		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.add(panel, BorderLayout.NORTH);
	
		dialog.getContentPane().add(containerPanel);

		int frameWidth = 440;
		int frameHeight = 500;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.setBounds((int) screenSize.getWidth()/2 - frameWidth/2,
			(int) screenSize.getHeight()/2 - frameHeight/2, frameWidth, frameHeight);
		dialog.setVisible(true);
	}
			
}
