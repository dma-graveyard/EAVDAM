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

import com.bbn.openmap.gui.OpenMapFrame;
import dk.frv.eavdam.data.AISDatalinkCheckIssue;
import dk.frv.eavdam.data.AISDatalinkCheckRule;
import dk.frv.eavdam.data.AISDatalinkCheckSeverity;
import dk.frv.eavdam.data.AISFrequency;
import dk.frv.eavdam.data.AISStation;
import dk.frv.eavdam.data.AISTimeslot;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.utils.DBHandler;
import dk.frv.eavdam.utils.HealthCheckHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Class for showing the issues found in an AIS VHF datalink health check.
 */
public class IssuesMenuItem extends JMenuItem {

    public static final long serialVersionUID = 1L;

	public static int ISSUES_WINDOW_WIDTH = 1024;
	public static int ISSUES_WINDOW_HEIGHT = 1000;	
	
	/**
	 * The issues found in the latest AIS VHF datalink health check
	 */
	public static List<AISDatalinkCheckIssue> issues = null;
	
	/**
	 * Whether data has changed after the health check meaning that the issues may be outdated
	 */
	public static Boolean healthCheckMayBeOutdated = null;
	
	/**
	 * Whether to not show warnings that the issues of the latest health check may be outdated.
	 * Set if the user chooses to ignore warnings from the dialog warning about possibly outdated issues.
	 */
	public static Boolean ignoreHealthCheckMayBeOutdated = null;
	
	/**
	 * When the latest health check was completed
	 */
	public static GregorianCalendar lastHealthCheckDoneAt = null;

	/**
	 * Which were the rules processed in the latest health check
	 */
	public static List<AISDatalinkCheckRule> lastHealthCheckRules = null;

	/**
	 * What was the top left latitude in the latest health check
	 */
	public static Double lastHealthCheckTopLeftLatitude = null;

	/**
	 * What was the top left longitude in the latest health check
	 */
	public static Double lastHealthCheckTopLeftLongitude = null;
	
	/**
	 * What was the lower right latitude in the latest health check
	 */	
	public static Double lastHealthCheckLowerRightLatitude = null;
	
	/**
	 * What was the lower right longitude in the latest health check
	 */	
	public static Double lastHealthCheckLowerRightLongitude = null;

    public IssuesMenuItem(EavdamMenu eavdamMenu) {        
        super("AIS VHF Datalink Issues");                
        addActionListener(new IssuesMenuItemActionListener(eavdamMenu));
    }
	
}
 
 
/**
 * Class for showing the issues found in an AIS VHF datalink health check. The issues can also be exported to a CSV file.
 */
class IssuesMenuItemActionListener implements ActionListener {

    private EavdamMenu eavdamMenu;
    private JDialog dialog;
		
	private JButton exportToCSVButton;
	private ExportIssuesToCSVDialog exportIssuesToCSVDialog;	
    
	public IssuesMenuItemActionListener(EavdamMenu eavdamMenu) {
        super();
        this.eavdamMenu = eavdamMenu;
    }
     
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof IssuesMenuItem) {
						
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension dimension = toolkit.getScreenSize();

			if (dimension.width-100 < IssuesMenuItem.ISSUES_WINDOW_WIDTH) {
				IssuesMenuItem.ISSUES_WINDOW_WIDTH = dimension.width-100;
			}
			if (dimension.width-100 < IssuesMenuItem.ISSUES_WINDOW_WIDTH) {
				IssuesMenuItem.ISSUES_WINDOW_WIDTH = dimension.width-100;
			}
			if (dimension.height-100 < IssuesMenuItem.ISSUES_WINDOW_HEIGHT) {
				IssuesMenuItem.ISSUES_WINDOW_HEIGHT = dimension.height-100;
			}
			if (dimension.height-100 < IssuesMenuItem.ISSUES_WINDOW_HEIGHT) {
				IssuesMenuItem.ISSUES_WINDOW_HEIGHT = dimension.height-100;
			}		
		
            dialog = new JDialog(eavdamMenu.getOpenMapFrame(), "AIS VHF Datalink Issues", false);
			
			JScrollPane scrollPane = getScrollPane();
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			
			JPanel containerPane = new JPanel();
			containerPane.setBorder(BorderFactory.createEmptyBorder());
			containerPane.setLayout(new BorderLayout());		 
			containerPane.add(scrollPane, BorderLayout.NORTH);			

			exportToCSVButton = new JButton("Export issues");
			exportToCSVButton.setToolTipText("Exports issues to CSV file format");
			exportToCSVButton.addActionListener(this);			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
			buttonPanel.add(exportToCSVButton);
			dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			
			dialog.getContentPane().add(containerPane, BorderLayout.CENTER);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.setBounds((int) screenSize.getWidth()/2 - IssuesMenuItem.ISSUES_WINDOW_WIDTH/2,
                (int) screenSize.getHeight()/2 - IssuesMenuItem.ISSUES_WINDOW_HEIGHT/2,
				IssuesMenuItem.ISSUES_WINDOW_WIDTH, IssuesMenuItem.ISSUES_WINDOW_HEIGHT);
            dialog.setVisible(true);
			
			if (IssuesMenuItem.healthCheckMayBeOutdated != null && IssuesMenuItem.healthCheckMayBeOutdated.booleanValue() == true &&
					IssuesMenuItem.ignoreHealthCheckMayBeOutdated != null && IssuesMenuItem.ignoreHealthCheckMayBeOutdated.booleanValue() == false &&
					IssuesMenuItem.issues != null && !IssuesMenuItem.issues.isEmpty()) {
                int response = JOptionPane.showConfirmDialog(dialog, "Data has changed after the latest health check\n" +
					"and the issues list may therefore be outdated.\n\nIgnore this warning in the future?", "Warning", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {					
					IssuesMenuItem.ignoreHealthCheckMayBeOutdated = new Boolean(true);
				}
			}
		
		} else if (e.getSource() == exportToCSVButton) {

            exportIssuesToCSVDialog = new ExportIssuesToCSVDialog(dialog);
			
     		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            exportIssuesToCSVDialog.setBounds((int) screenSize.getWidth()/2 - ExportIssuesToCSVDialog.WINDOW_WIDTH/2,
				(int) screenSize.getHeight()/2 - ExportIssuesToCSVDialog.WINDOW_HEIGHT/2, ExportIssuesToCSVDialog.WINDOW_WIDTH,
				ExportIssuesToCSVDialog.WINDOW_HEIGHT);
            exportIssuesToCSVDialog.setVisible(true);	
		}
		
	}

	private JScrollPane getScrollPane() {

		if (IssuesMenuItem.issues == null) {
			EAVDAMData data = DBHandler.getData();
			IssuesMenuItem.issues = data.getAISDatalinkCheckIssues();
		}
		
    	List<AISDatalinkCheckIssue> tempList = new ArrayList<AISDatalinkCheckIssue>();
    	for(AISDatalinkCheckIssue issue : IssuesMenuItem.issues){
    		if(tempList.size() == 0){
    			tempList.add(issue);
    		}else{
    			for(int i = 0 ; i < tempList.size(); ++i){
    				if(!issue.isAcknowledged() && tempList.get(i).isAcknowledged()){
    					tempList.add(i, issue);
    					break;
    				}
    				
    				if(i == tempList.size() - 1){
    					tempList.add(issue);
    					break;
    				}
    			}
    		}
    	}
    	IssuesMenuItem.issues = tempList;
				
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());                  
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;    
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5,5,5,5);
		
		if (IssuesMenuItem.issues == null || IssuesMenuItem.issues.isEmpty()) {
		
			panel.add(new JLabel("<html><body><h1>AIS VHF Datalink Issues</h1></body></html>"), c);
			c.anchor = GridBagConstraints.LINE_START;
			
			c.gridy = 1;
			panel.add(new JLabel("No issues."), c);
		
		} else {
		
			//c.anchor = GridBagConstraints.CENTER;		
			c.gridwidth = 7;
			panel.add(new JLabel("<html><body><h1>AIS VHF Datalink Issues</h1></body></html>"), c);
			c.anchor = GridBagConstraints.LINE_START;
			
			c.gridy = 1;
			c.insets = new Insets(5,0,15,5);			
			String numberOfIssuesLabel = "Number of issues: " + IssuesMenuItem.issues.size();
			if (IssuesMenuItem.issues.size() > 100) {
				numberOfIssuesLabel += " (displaying 100 first)";
			}
			panel.add(new JLabel(numberOfIssuesLabel), c);			
			
			c.gridy = 2;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(0,0,0,0);
			JLabel ruleViolatedTitleLabel = new JLabel(" Rule violated  ");
			ruleViolatedTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
			ruleViolatedTitleLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			panel.add(ruleViolatedTitleLabel, c);
			c.gridx = 1;           		
			JLabel severityTitleLabel = new JLabel(" Severity  ");
			severityTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
			severityTitleLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			panel.add(severityTitleLabel, c);
			c.gridx = 2;
			JLabel aisStationsInvolvedTitleLabel = new JLabel(" AIS stations involved  ");
			aisStationsInvolvedTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
			aisStationsInvolvedTitleLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			panel.add(aisStationsInvolvedTitleLabel, c);				
			c.gridx = 3;
			JLabel timeslotsInvolvedTitleLabel = new JLabel(" Timeslots involved  ");
			timeslotsInvolvedTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
			timeslotsInvolvedTitleLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			panel.add(timeslotsInvolvedTitleLabel, c);
			c.gridx = 4;   
			JLabel acknowledgedTitleLabel = new JLabel(" Acknowledged  ");
			acknowledgedTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
			acknowledgedTitleLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			panel.add(acknowledgedTitleLabel, c);				
			c.gridx = 5;         
			JLabel acknowledgeButtonTitleLabel = new JLabel("  ");
			acknowledgeButtonTitleLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			panel.add(acknowledgeButtonTitleLabel, c);
			/*
			c.gridx = 6;   
			JLabel deleteTitleLabel = new JLabel("  ");
			deleteTitleLabel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			panel.add(deleteTitleLabel, c);
			*/
			
			int count = 0;
			
			for (AISDatalinkCheckIssue issue : IssuesMenuItem.issues) {
			
				if (count == 100) {
					break;
				} else {
					count++;
				}
			
				int id = issue.getId();
				AISDatalinkCheckRule ruleViolated = issue.getRuleViolated();
				AISDatalinkCheckSeverity severity = issue.getSeverity();
				List<AISStation> involvedStations = issue.getInvolvedStations();
				List<AISTimeslot> involvedTimeslots = issue.getInvolvedTimeslots();
				boolean acknowledged = issue.isAcknowledged();
			
				c.gridy++;
				c.gridx = 0;
			
				String ruleViolatedStr = "";
				if (ruleViolated != null) {
					if (ruleViolated == AISDatalinkCheckRule.RULE1) {
						ruleViolatedStr = "<html><body>&nbsp;&nbsp;Conflicting stations&nbsp;&nbsp;</html></body>";
					} else if (ruleViolated == AISDatalinkCheckRule.RULE2) {
						ruleViolatedStr = "<html><body>&nbsp;&nbsp;Reservation, but no intended use&nbsp;&nbsp;</html></body>";
					} else if (ruleViolated == AISDatalinkCheckRule.RULE3) {
						ruleViolatedStr = "<html><body>&nbsp;&nbsp;Intended FATDMA use, but no&nbsp;&nbsp;<br>&nbsp;&nbsp;reservation&nbsp;&nbsp;</html></body>";
					} else if (ruleViolated == AISDatalinkCheckRule.RULE4) {
						ruleViolatedStr = "<html><body>&nbsp;&nbsp;Simultaneous use of several&nbsp;&nbsp;<br>&nbsp;&nbsp;frequencies&nbsp;&nbsp;</html></body>";
					} else if (ruleViolated == AISDatalinkCheckRule.RULE5) {
						ruleViolatedStr = "<html><body>&nbsp;&nbsp;Slots reserved outside IALA A-124&nbsp;&nbsp;<br>&nbsp;&nbsp;recommended default FATDMA schemes&nbsp;&nbsp;</html></body>";
					} else if (ruleViolated == AISDatalinkCheckRule.RULE6) {
						ruleViolatedStr = "<html><body>&nbsp;&nbsp;Slots reserved outside overall slot&nbsp;&nbsp;<br>&nbsp;&nbsp;pattern for fixed stations (IALA A-124)&nbsp;&nbsp;</html></body>";
					} else if (ruleViolated == AISDatalinkCheckRule.RULE7) {					
						ruleViolatedStr = "<html><body>&nbsp;&nbsp;Free Bandwith below 50%&nbsp;&nbsp;</html></body>";
					}
				}

				JLabel ruleViolatedLabel = new JLabel(ruleViolatedStr);
				ruleViolatedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				ruleViolatedLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));        
				panel.add(ruleViolatedLabel, c);
				
				c.gridx = 1;
				
				String severityStr = "";
				if (severity != null) {
					if (severity == AISDatalinkCheckSeverity.SEVERE) {
						severityStr = "  Severe  ";		
					} else if (severity == AISDatalinkCheckSeverity.MAJOR) {
						severityStr = "  Major  ";	
					} else if (severity == AISDatalinkCheckSeverity.MINOR) {
						severityStr = "  Minor  ";	
					}
				}				
				
				JLabel severityLabel = new JLabel(severityStr);
				severityLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				severityLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
				panel.add(severityLabel, c);		
				
				c.gridx = 2;
				
				String involvedStationsStr = "";
				if (involvedStations != null) {
					if (involvedStations.size() == 1) {
						AISStation involvedStation = involvedStations.get(0);
						involvedStationsStr = "  " + involvedStation.getOrganizationName() + ": " + involvedStation.getStationName() + "  ";
					} else if (involvedStations.size() > 1) {
						involvedStationsStr = "<html><body>";
						for (int i=0; i<involvedStations.size(); i++) {
							AISStation involvedStation = involvedStations.get(i);
							involvedStationsStr += "&nbsp;&nbsp;" + involvedStation.getOrganizationName() + ": " + involvedStation.getStationName() + "&nbsp;&nbsp;";
							if (i<involvedStations.size()-1) {
								involvedStationsStr += "<br>";
							}
						}
						involvedStationsStr += "</body></html>";
					}
				}					
				
				JLabel involvedStationsLabel = new JLabel(involvedStationsStr);
				involvedStationsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				involvedStationsLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
				panel.add(involvedStationsLabel, c);
			
				c.gridx = 3;
			
				String involvedTimeslotsStr = "";
				if (involvedTimeslots != null) {
					involvedTimeslotsStr = "<html><body>";
					for (int i=0; i<involvedTimeslots.size(); i++) {
						if (i > 0 && i%2 == 0) {
							involvedTimeslotsStr += "&nbsp;&nbsp;<br>";
						} else {
							involvedTimeslotsStr += " ";
						}
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
							involvedTimeslotsStr += "&nbsp;&nbsp;{";
						} else {
							involvedTimeslotsStr += "&nbsp;&nbsp;";
						}
						involvedTimeslotsStr += "(" + frequency + "," + involvedTimeslot.getSlotNumber() + ")";

						if (i<involvedTimeslots.size()-1) {
							involvedTimeslotsStr += ",";
						} else {
							involvedTimeslotsStr += "}&nbsp;&nbsp;";	
						}
					}
					involvedTimeslotsStr += "</body></html>";					
				}
				
				JLabel involvedTimeslotsLabel = new JLabel(involvedTimeslotsStr);
				involvedTimeslotsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				involvedTimeslotsLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
				panel.add(involvedTimeslotsLabel, c);	

				c.gridx = 4;
			
				String acknowledgedStr = "";
				if (acknowledged) {
					acknowledgedStr = "  Yes  ";
				} else {
					acknowledgedStr = "  No  ";
				}
				JLabel acknowledgedLabel = new JLabel(acknowledgedStr);
				acknowledgedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
				acknowledgedLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
				panel.add(acknowledgedLabel, c);		
			
				c.gridx = 5;
			
				JButton acknowledgeButton = new JButton("Acknowledge");
				acknowledgeButton.setPreferredSize(new Dimension(90, 25));
				acknowledgeButton.setMaximumSize(new Dimension(90, 25));
				acknowledgeButton.setMinimumSize(new Dimension(90, 25));					
				acknowledgeButton.setMargin(new Insets(1,1,1,1));  					
				acknowledgeButton.setFont(new Font("Arial", Font.PLAIN, 12));
				acknowledgeButton.setFocusPainted(false);
				acknowledgeButton.setAction(new AcknowledgeAction("Acknowledge", this, dialog, issue));
				if (acknowledged) {
					acknowledgeButton.setEnabled(false);
				}
				Box verticalBox = Box.createVerticalBox();			
				verticalBox.add(Box.createVerticalGlue());
				verticalBox.add(acknowledgeButton);
				verticalBox.add(Box.createVerticalGlue());
				JPanel temp = new JPanel(new BorderLayout());
				temp.add(verticalBox, BorderLayout.CENTER);
				temp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(5, 5, 5, 5)));	
				panel.add(temp, c);
				
				
				/*
				c.gridx = 6;
			
				JButton deleteButton = new JButton("Delete");
				deleteButton.setPreferredSize(new Dimension(60, 25));
				deleteButton.setMaximumSize(new Dimension(60, 25));
				deleteButton.setMinimumSize(new Dimension(60, 25));
				deleteButton.setMargin(new Insets(1,1,1,1));  				
				deleteButton.setFont(new Font("Arial", Font.PLAIN, 12));							
				deleteButton.setFocusPainted(false);				
				deleteButton.setAction(new DeleteAction("Delete", this, dialog, issue));				
				Box verticalBox2 = Box.createVerticalBox();			
				verticalBox2.add(Box.createVerticalGlue());
				verticalBox2.add(deleteButton);
				verticalBox2.add(Box.createVerticalGlue());
				JPanel temp2 = new JPanel(new BorderLayout());
				temp2.add(verticalBox2, BorderLayout.CENTER);
				temp2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(5, 5, 5, 5)));	
				panel.add(temp2, c)
				*/
			}	
		}			
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		if (scrollPane.getViewport().getViewSize().getHeight() > IssuesMenuItem.ISSUES_WINDOW_HEIGHT-90) {
			scrollPane.setPreferredSize(new Dimension(IssuesMenuItem.ISSUES_WINDOW_WIDTH, IssuesMenuItem.ISSUES_WINDOW_HEIGHT-90));
			scrollPane.setMaximumSize(new Dimension(IssuesMenuItem.ISSUES_WINDOW_WIDTH, IssuesMenuItem.ISSUES_WINDOW_HEIGHT-90));
		}
		scrollPane.validate();
		
		return scrollPane;
	}
	
	/**
	 * Updates the scroll panel showing the issues.
	 */
	protected void updateScrollPane() {
		JScrollPane scrollPane = getScrollPane();		
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
			
		JPanel containerPane = new JPanel();
		containerPane.setBorder(BorderFactory.createEmptyBorder());
		containerPane.setLayout(new BorderLayout());		 
		containerPane.add(scrollPane, BorderLayout.NORTH);			

		exportToCSVButton = new JButton("Export issues");
		exportToCSVButton.setToolTipText("Exports issues to CSV file format");
		exportToCSVButton.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		buttonPanel.add(exportToCSVButton);
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);		
		
		dialog.setContentPane(containerPane);
		dialog.validate();
	}
	
}


/**
 * Class for acknowledging an issue.
 */
class AcknowledgeAction extends AbstractAction {

    public static final long serialVersionUID = 1L;

	private IssuesMenuItemActionListener issuesMenuItemActionListener;
	private AISDatalinkCheckIssue issue;
	private JDialog dialog;

    public AcknowledgeAction(String name, IssuesMenuItemActionListener issuesMenuItemActionListener, JDialog dialog, AISDatalinkCheckIssue issue) {
        super(name);
		this.issuesMenuItemActionListener = issuesMenuItemActionListener;
		this.issue = issue;
		this.dialog = dialog;
    }
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to acknowledge this issue?", "Confirm action", JOptionPane.YES_NO_OPTION);	
		if (response == JOptionPane.YES_OPTION) {
			List<AISDatalinkCheckIssue> issues = new ArrayList<AISDatalinkCheckIssue>();
			issues.add(issue);			
			DBHandler.acknowledgeIssues(issues);
			IssuesMenuItem.issues = null;
			issuesMenuItemActionListener.updateScrollPane();           
        } else if (response == JOptionPane.NO_OPTION) {}
    }
}

/*
class DeleteAction extends AbstractAction {

    public static final long serialVersionUID = 1L;

	private IssuesMenuItemActionListener issuesMenuItemActionListener;
	private AISDatalinkCheckIssue issue;
	private JDialog dialog;

    public DeleteAction(String name, IssuesMenuItemActionListener issuesMenuItemActionListener, JDialog dialog, AISDatalinkCheckIssue issue) {
        super(name);
		this.issuesMenuItemActionListener = issuesMenuItemActionListener;
		this.issue = issue;
		this.dialog = dialog;
    }
    public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to delete this issue?", "Confirm action", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
			List<AISDatalinkCheckIssue> issues = new ArrayList<AISDatalinkCheckIssue>();
			issues.add(issue);			
			DBHandler.deleteIssues(issues);
			IssuesMenuItem.issues.remove(issue);
			issuesMenuItemActionListener.updateScrollPane();           
        } else if (response == JOptionPane.NO_OPTION) {}
    }
}
*/