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
package dk.frv.eavdam.app;

import com.bbn.openmap.gui.MapPanelChild;
import dk.frv.eavdam.buttons.InitiateHealthCheckButton;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationType;
import dk.frv.eavdam.data.Antenna;
import dk.frv.eavdam.data.AntennaType;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.layers.OMBaseStation;
import dk.frv.eavdam.utils.HealthCheckHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.AbstractButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

/**
 * Class for the side panel of the open map frame.
 */
public class SidePanel extends JPanel implements MapPanelChild, ActionListener {

	private static final long serialVersionUID = 1L;

    private JEditorPane infoPane;
	
	private JPanel progressIndicatorPane;
	private JButton cancelButton;
	private JProgressBar progressBar;
	
	private HealthCheckHandler hch;

	public JPanel getProgressIndicatorPane() {
		return progressIndicatorPane;
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public void setHealthCheckHandler(HealthCheckHandler hch) {
		this.hch = hch;
	}
	
	/**
	 * Creates the side panel.
	 */	
	public SidePanel() {
	        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        infoPane = new JEditorPane("text/html",
            "<p><strong>Click a station to view data:<strong></p>" +
            "<table cellspacing=1 cellpadding=1><tr><td>Organization:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Name:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Type:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Latitude:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Longitude:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">MMSI:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Power:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Antenna type:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Antenna height:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Terrain height:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Heading:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Angle:</td><td>...</td></tr>" +            
            "<tr><td valign=\"top\">Gain:</td><td>...</td></tr>" +
            "<tr><td valign=\"top\">Timeslots reserved on CH A (AIS1):</td><td>...</td></tr>" +
			"<tr><td valign=\"top\">Timeslots reserved on CH B (AIS2):</td><td>...</td></tr></table>");
        infoPane.setBackground(new Color(238, 238, 238));
        infoPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        infoPane.setPreferredSize(new Dimension(230, 500));
        infoPane.setMaximumSize(new Dimension(230, 500));
        infoPane.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(infoPane);
        infoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoScrollPane.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        infoScrollPane.setPreferredSize(new Dimension(230, 500));
        infoScrollPane.setMaximumSize(new Dimension(230, 500));
        add(infoScrollPane);       
        	
		progressIndicatorPane = new JPanel();								
		progressIndicatorPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;                   
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5,5,5,5);			
		JLabel titleLabel = new JLabel("<html><body><p>The AIS VHF datalink health check is being executed...<p></body></html>");
		titleLabel.setPreferredSize(new Dimension(230, 30));
		titleLabel.setMaximumSize(new Dimension(230, 30));
		titleLabel.setMinimumSize(new Dimension(230, 30));
		progressIndicatorPane.add(titleLabel, c);
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);			
		progressBar.setPreferredSize(new Dimension(230, 20));
		progressBar.setMaximumSize(new Dimension(230, 20));
		progressBar.setMinimumSize(new Dimension(230, 20));					
		progressIndicatorPane.add(progressBar, c);
		add(progressIndicatorPane);
		c.gridy = 2;
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		progressIndicatorPane.add(cancelButton, c);
		progressIndicatorPane.setVisible(false);
		
        // efficiensea logo
        
        URL efficienSeaImgURL = getClass().getResource("/share/data/images/efficiensea.png");        
        if (efficienSeaImgURL != null) {
            ImageIcon icon = new ImageIcon(efficienSeaImgURL, "");           
            JLabel iconLabel = new JLabel(icon);
            JPanel iconPanel = new JPanel();  
            iconPanel.setLayout(new BorderLayout());
            iconPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));             
            iconPanel.add(iconLabel);
            iconPanel.setMinimumSize(new Dimension(250, 75));
            iconPanel.setPreferredSize(new Dimension(250, 75));
            iconPanel.setMaximumSize(new Dimension(250, 75));
            add(iconPanel);
        } else {
            System.err.println("Couldn't find efficiensea.png file");
        }     
        
        // eu baltic logo
        
        URL euBalticImgURL = getClass().getResource("/share/data/images/euBaltic.png");        
        if (euBalticImgURL != null) {
            ImageIcon icon = new ImageIcon(euBalticImgURL, "");           
            JLabel iconLabel = new JLabel(icon);
            JPanel iconPanel = new JPanel();  
            iconPanel.setLayout(new BorderLayout());
            iconPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));             
            iconPanel.add(iconLabel);
            iconPanel.setMinimumSize(new Dimension(250, 50));
            iconPanel.setPreferredSize(new Dimension(250, 50));
            iconPanel.setMaximumSize(new Dimension(250, 50));
            add(iconPanel);
        } else {
            System.err.println("Couldn't find euBaltic.png file");
        }             	    
        
	}

	@Override
	public String getPreferredLocation() {
		return BorderLayout.EAST;
	}

	@Override
	public void setPreferredLocation(String arg0) {}

	@Override
	public String getParentName() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (cancelButton != null && e.getSource() == cancelButton) {
			hch.setCancelled(true);
			progressIndicatorPane.setVisible(false);
		}
	}
	
	/**
	 * Sets the station information to the side panel.
	 *
	 * @param omBaseStation  The station of which information is set
	 */
	public void showInfo(OMBaseStation omBaseStation) {
		EAVDAMUser owner = omBaseStation.getOwner();
	    AISFixedStationData stationData = omBaseStation.getStationData();
	    
	    String infoText = "<p><strong>Click a station to view data:</strong></p>" +
            "<table cellspacing=1 cellpadding=1><tr><td valign=\"top\">Organization:</td><td valign=\"top\">";
        if (owner != null && owner.getOrganizationName() != null) {
            infoText += owner.getOrganizationName();
        } else {
            infoText += "...";
        }
        infoText += "</td></tr><tr><td valign=\"top\">Name:</td><td valign=\"top\">";		
		if (stationData.getStationName() != null) {
            infoText += stationData.getStationName();
        } else {
            infoText += "...";
        }
        infoText += "</td></tr><tr><td valign=\"top\">Type:</td><td valign=\"top\">";
        if (stationData.getStationType() != null) {
            if (stationData.getStationType() == AISFixedStationType.BASESTATION) {
                infoText += "AIS Base Station";
            } else if (stationData.getStationType() == AISFixedStationType.REPEATER) {
                infoText += "AIS Repeater";
            } else if (stationData.getStationType() == AISFixedStationType.RECEIVER) {
                infoText += "AIS Receiver station";
            } else if (stationData.getStationType() == AISFixedStationType.ATON) {
                infoText += "AIS AtoN station";
            }
        } else {
            infoText += "...";
        }
        infoText += "</td></tr><tr><td valign=\"top\">Latitude:</td><td valign=\"top\">" + stationData.getLat() +
            "</td></tr><tr><td valign=\"top\">Longitude:</td><td valign=\"top\">" + stationData.getLon() + "</td></tr>" +
            "<tr><td>MMSI:</td><td>";
        if (stationData.getMmsi() != null) {    
            infoText += stationData.getMmsi();
        } else {
            infoText += "...";
        }
        infoText += "</td></tr><tr><td valign=\"top\">Power:</td><td valign=\"top\">";
        if (stationData.getTransmissionPower() != null) {
            infoText += stationData.getTransmissionPower().toString();
        } else {
            infoText += "...";
        }
        infoText +="</td></tr><tr><td valign=\"top\">Antenna type:</td><td valign=\"top\">";
        if (stationData.getAntenna() != null) {
            Antenna antenna = stationData.getAntenna();            
            if (antenna.getAntennaType() != null) {
                AntennaType antennaType = antenna.getAntennaType();
                if (antenna.getAntennaType() == AntennaType.OMNIDIRECTIONAL) {
                    infoText += "Omnidirectional";
                } else if (antenna.getAntennaType() == AntennaType.DIRECTIONAL) {
                    infoText += "Directional";
                }
            } else {
                infoText += "...";
            }
            infoText += "</td></tr><tr><td valign=\"top\">Antenna height:</td><td valign=\"top\">" +
                antenna.getAntennaHeight() + "</td></tr>" +
                "<tr><td>Terrain height:</td><td valign=\"top\">" + antenna.getTerrainHeight() +
                "</td></tr><tr><td valign=\"top\">Heading:</td><td valign=\"top\">";
            if (antenna.getHeading() != null) {                
                infoText += antenna.getHeading().toString();
            } else {
                infoText += "...";
            }
            infoText += "</td></tr><tr><td valign=\"top\">Angle:</td><td valign=\"top\">";
            if (antenna.getFieldOfViewAngle() != null) {                
                infoText += antenna.getFieldOfViewAngle().toString();
            } else {
                infoText += "...";
            }  
            infoText += "</td></tr><tr><td valign=\"top\">Gain:</td><td valign=\"top\">";
            if (antenna.getGain() != null) {                
                infoText += antenna.getGain().toString();
            } else {
                infoText += "...";
            }
            infoText += "</td></tr>";         
        } else {
            infoText += "...</td></tr>" +
            "<tr><td valign=\"top\">Antenna height:</td><td valign=\"top\">...</td></tr>" +
            "<tr><td valign=\"top\">Terrain height:</td><td valign=\"top\">...</td></tr>" +
            "<tr><td valign=\"top\">Heading:</td><td>...</td valign=\"top\"></tr>" +
            "<tr><td valign=\"top\">Angle:</td><td>...</td valign=\"top\"></tr>" +            
            "<tr><td valign=\"top\">Gain:</td><td>...</td valign=\"top\"></tr>";
        }
		if (stationData.getReservedBlocksForChannelA() != null && !stationData.getReservedBlocksForChannelA().isEmpty()) {
			String temp = "";
			for (int i=0; i<stationData.getReservedBlocksForChannelA().size(); i++) {
				Integer reservedBlock = stationData.getReservedBlocksForChannelA().get(i);
				if (i > 0) {
					temp += ", ";
				}
				temp += reservedBlock.toString();
			}
			infoText += "<tr><td valign=\"top\">Timeslots reserved on CH A (";
			if (stationData.getFATDMAChannelA() != null && stationData.getFATDMAChannelA().getChannelName() != null) {
				infoText += stationData.getFATDMAChannelA().getChannelName();
			} else {
				infoText += "NULL";
			}
			infoText += "):</td><td valign=\"top\">" + temp + "</td></tr>";
		} else {			 
			 infoText += "<tr><td valign=\"top\">Timeslots reserved on CH A (";
			if (stationData.getFATDMAChannelA() != null && stationData.getFATDMAChannelA().getChannelName() != null) {
				infoText += stationData.getFATDMAChannelA().getChannelName();
			} else {
				infoText += "NULL";
			}			 
			infoText += "):</td><td valign=\"top\">...</td></tr>";
		}
		if (stationData.getReservedBlocksForChannelB() != null && !stationData.getReservedBlocksForChannelB().isEmpty()) {
			String temp = "";
			for (int i=0; i<stationData.getReservedBlocksForChannelB().size(); i++) {
				Integer reservedBlock = stationData.getReservedBlocksForChannelB().get(i);
				if (i > 0) {
					temp += ", ";
				}
				temp += reservedBlock.toString();
			}
			infoText += "<tr><td valign=\"top\">Timeslots reserved on CH B (";
			if (stationData.getFATDMAChannelB() != null && stationData.getFATDMAChannelB().getChannelName() != null) {
				infoText += stationData.getFATDMAChannelB().getChannelName();
			} else {
				infoText += "NULL";
			}						
			infoText += "):</td><td valign=\"top\">" + temp + "</td></tr>";
		} else {			 
			infoText += "<tr><td valign=\"top\">Timeslots reserved on CH B (";
			if (stationData.getFATDMAChannelB() != null && stationData.getFATDMAChannelB().getChannelName() != null) {
				infoText += stationData.getFATDMAChannelB().getChannelName();
			} else {
				infoText += "NULL";
			}				 			 
			infoText += "):</td><td valign=\"top\">...</td></tr>";
		}
		infoText += "</table>";					
					
        infoPane.setText(infoText);
	}
	
}
