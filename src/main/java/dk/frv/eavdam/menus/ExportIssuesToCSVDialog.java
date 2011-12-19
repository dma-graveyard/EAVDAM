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

import au.com.bytecode.opencsv.CSVWriter;
import dk.frv.eavdam.data.AISDatalinkCheckIssue;
import dk.frv.eavdam.data.AISDatalinkCheckRule;
import dk.frv.eavdam.data.AISDatalinkCheckSeverity;
import dk.frv.eavdam.data.AISFrequency;
import dk.frv.eavdam.data.AISStation;
import dk.frv.eavdam.data.AISTimeslot;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import dk.frv.eavdam.layers.StationLayer;
import dk.frv.eavdam.utils.DBHandler;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;


/**
 * Class for showing a menu where the user can export the issues found in an AIS VHF datalink health check to a CVS file.
 */
public class ExportIssuesToCSVDialog extends JDialog implements ActionListener {

    public static final long serialVersionUID = 1L;

	public static int WINDOW_WIDTH = 560;
	public static int WINDOW_HEIGHT = 150;		
	
	private JDialog parent;
	
	private JComboBox csvDelimeterComboBox;
	
	private JButton exportButton;
	private JButton cancelButton;
	
	public ExportIssuesToCSVDialog(JDialog parent) {

		super(parent, "Export to CSV", true);

		this.parent = parent;

		JPanel cvsSettingsPanel = new JPanel();
		cvsSettingsPanel.setPreferredSize(new Dimension(560, 85));	
		cvsSettingsPanel.setMinimumSize(new Dimension(560, 85));
		cvsSettingsPanel.setMaximumSize(new Dimension(560, 85));			
		cvsSettingsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("CSV settings")));
		cvsSettingsPanel.setLayout(new GridBagLayout());	
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;                   
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5,5,5,5);				
		c.weightx = 0.001;
		cvsSettingsPanel.add(new JLabel("Delimeter: "), c);
		c.gridx = 1;
		csvDelimeterComboBox = new JComboBox();
		csvDelimeterComboBox.addItem(";");
		csvDelimeterComboBox.addItem(",");
		csvDelimeterComboBox.addItem("tab");
		csvDelimeterComboBox.addItem("#");
		c.weightx = 1;		
		cvsSettingsPanel.add(csvDelimeterComboBox, c);		
		
		getContentPane().add(cvsSettingsPanel, BorderLayout.CENTER);
			
		exportButton = new JButton("Export");
		exportButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		buttonPanel.add(exportButton);
		buttonPanel.add(cancelButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		pack();
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exportButton) {	
			JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));  
			fileChooser.addChoosableFileFilter(new IssuesCSVFilter());
			int returnVal = fileChooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (fileChooser.getSelectedFile() != null) {  
					File fileToSave = fileChooser.getSelectedFile();  
					if (fileToSave.exists()) {
						int response = JOptionPane.showConfirmDialog(this, "Do you want overwrite the existing file?", "Confirm action", JOptionPane.YES_NO_OPTION);
						if (response == JOptionPane.YES_OPTION) {
							exportToCSVFile(fileToSave);
						}
					} else {
						exportToCSVFile(fileToSave);
					}

				}
			}
		} else if (e.getSource() == cancelButton) {
			this.dispose();
		}
	}	

	/** 
	 * Saves the issues to a file.
	 *
	 * @param file  File to which to save the issues
	 */
	private void exportToCSVFile(File file) {

		try {
		
			char delimeter = ';';
			if (((String) csvDelimeterComboBox.getSelectedItem()).equals("tab")) {
				delimeter = '\t';
			} else {
				char[] temp = ((String) csvDelimeterComboBox.getSelectedItem()).toCharArray();
				if (temp.length == 1) {
					delimeter = temp[0];
				}
			}
			
			CSVWriter writer = new CSVWriter(new FileWriter(file), delimeter);
			
			List<String> titles = new ArrayList<String>();
			titles.add("Rule violated");
			titles.add("Severity");
			titles.add("AIS stations involved");
			titles.add("Timeslots involved");
			titles.add("Acknowledged");			
			
			String[] titlesArray = new String[titles.size()];
			titles.toArray(titlesArray);
			writer.writeNext(titlesArray, false);			
			
			if (IssuesMenuItem.issues != null) {
			
				for (AISDatalinkCheckIssue issue : IssuesMenuItem.issues) {
			
					List<String> temp = new ArrayList<String>();
			
					AISDatalinkCheckRule ruleViolated = issue.getRuleViolated();
					AISDatalinkCheckSeverity severity = issue.getSeverity();
					List<AISStation> involvedStations = issue.getInvolvedStations();
					List<AISTimeslot> involvedTimeslots = issue.getInvolvedTimeslots();
					boolean acknowledged = issue.isAcknowledged();
			
					String ruleViolatedStr = "";
					if (ruleViolated != null) {
						if (ruleViolated == AISDatalinkCheckRule.RULE1) {
							ruleViolatedStr = "Conflicting stations";
						} else if (ruleViolated == AISDatalinkCheckRule.RULE2) {
							ruleViolatedStr = "Reservation, but no intended use";
						} else if (ruleViolated == AISDatalinkCheckRule.RULE3) {
							ruleViolatedStr = "Intended FATDMA use, but no reservation";
						} else if (ruleViolated == AISDatalinkCheckRule.RULE4) {
							ruleViolatedStr = "Simultaneous use of several frequencies";
						} else if (ruleViolated == AISDatalinkCheckRule.RULE5) {
							ruleViolatedStr = "Slots reserved outside IALA A-124 recommended default FATDMA schemes";
						} else if (ruleViolated == AISDatalinkCheckRule.RULE6) {
							ruleViolatedStr = "Slots reserved outside overall slot pattern for fixed statons (IALA A-124)";
						} else if (ruleViolated == AISDatalinkCheckRule.RULE7) {					
							ruleViolatedStr = "Free Bandwith below 50";
						}
					}
					temp.add(ruleViolatedStr);

					String severityStr = "";
					if (severity != null) {
						if (severity == AISDatalinkCheckSeverity.SEVERE) {
							severityStr = "Severe";		
						} else if (severity == AISDatalinkCheckSeverity.MAJOR) {
							severityStr = "Major";	
						} else if (severity == AISDatalinkCheckSeverity.MAJOR) {
							severityStr = "Minor";	
						}
					}
					temp.add(severityStr);
					
					String involvedStationsStr = "";
					if (involvedStations != null) {
						if (involvedStations.size() == 1) {
							AISStation involvedStation = involvedStations.get(0);
							involvedStationsStr = involvedStation.getOrganizationName() + ": " + involvedStation.getStationName();
						} else if (involvedStations.size() > 1) {
							for (int i=0; i<involvedStations.size(); i++) {
								AISStation involvedStation = involvedStations.get(i);
								involvedStationsStr += involvedStation.getOrganizationName() + ": " + involvedStation.getStationName();
								if (i<involvedStations.size()-1) {
									involvedStationsStr += ", ";
								}
							}
						}
					}					
					temp.add(involvedStationsStr);
				
					String involvedTimeslotsStr = "";
					if (involvedTimeslots != null) {
						for (int i=0; i<involvedTimeslots.size(); i++) {
							AISTimeslot involvedTimeslot = involvedTimeslots.get(i);
							String frequency = "NULL";
							if (involvedTimeslot.getFrequency() != null) {
								if (involvedTimeslot.getFrequency() == AISFrequency.AIS1) {
									frequency = "AIS1";
								} else if (involvedTimeslot.getFrequency() == AISFrequency.AIS2) {
									frequency = "AIS2";
								}
							}							
							if (i == 0) {
								involvedTimeslotsStr += "{";
							}
							involvedTimeslotsStr += "(" + frequency + "," + involvedTimeslot.getSlotNumber() + ")";

							if (i<involvedTimeslots.size()-1) {
								involvedTimeslotsStr += ", ";
							} else {
								involvedTimeslotsStr += "}";	
							}
						}
					}
					temp.add(involvedTimeslotsStr);
				
					String acknowledgedStr = "";
					if (acknowledged) {
						acknowledgedStr = "Yes";
					} else {
						acknowledgedStr = "No";
					}
					temp.add(acknowledgedStr);
				
					String[] tempArray = new String[temp.size()];
					temp.toArray(tempArray);
					writer.writeNext(tempArray, false);						
				}
			}

			writer.close();
			this.dispose();
			JOptionPane.showMessageDialog(parent, "The issues were exported succesfully!", "Operation succesful", JOptionPane.INFORMATION_MESSAGE); 		
		} catch (IOException e) {
			this.dispose();
			JOptionPane.showMessageDialog(parent, "The following error occurred when exporting issues: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 					
		}
	}
	
}


/**
 * Class for filtering csv files.
 */
class IssuesCSVFilter extends FileFilter {

	/**
	 * Accepts files that end with .csv
	 *
	 * @param file  Some file
	 * @return      True if the filename ends with .csv, false otherwise
	 */
	public boolean accept(File file) {
        String filename = file.getName();
        return filename.toLowerCase().endsWith(".csv");
    }
	
	/**
	 * Returns description for the filter.
	 *
	 * @return  Description for the filter
	 */
    public String getDescription() {
        return "*.csv";
    }
	
}