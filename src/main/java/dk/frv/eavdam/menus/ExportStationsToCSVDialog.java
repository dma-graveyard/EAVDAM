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
import dk.frv.eavdam.data.ActiveStation;
import dk.frv.eavdam.data.AISAtonStationFATDMAChannel;
import dk.frv.eavdam.data.AISBaseAndReceiverStationFATDMAChannel;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationType;
import dk.frv.eavdam.data.Antenna;
import dk.frv.eavdam.data.AntennaType;
import dk.frv.eavdam.data.AtonMessageBroadcastRate;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.FATDMAReservation;
import dk.frv.eavdam.data.OtherUserStations;
import dk.frv.eavdam.data.Simulation;
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

public class ExportStationsToCSVDialog extends JDialog implements ActionListener {

    public static final long serialVersionUID = 1L;

	public static int WINDOW_WIDTH = 560;
	public static int WINDOW_HEIGHT = 525;		
	
	private JDialog parent;
	private StationLayer stationLayer;
	
	private JCheckBox organizationCheckBox;
	private JCheckBox stationNameCheckBox;
	private JCheckBox stationTypeCheckBox;
	private JCheckBox positionCheckBox;
	private JCheckBox mmsiNumberCheckBox;
	private JCheckBox transmissionPowerCheckBox;
	private JCheckBox antennaTypeCheckBox;
	private JCheckBox antennaHeightCheckBox;
	private JCheckBox terrainHeightCheckBox;
	private JCheckBox antennaHeadingCheckBox;
	private JCheckBox antennaFieldOfViewAngleCheckBox;
	private JCheckBox antennaGainCheckBox;	
	private JCheckBox fatdmaParametersCheckBox;	
	
	private JComboBox csvDelimeterComboBox;
	
	private JButton exportButton;
	private JButton cancelButton;
	
	public ExportStationsToCSVDialog(JDialog parent, StationLayer stationLayer) {

		super(parent, "Export to CSV", true);

		this.parent = parent;
		this.stationLayer = stationLayer;
		
		JPanel parametersPanel = new JPanel();
		parametersPanel.setPreferredSize(new Dimension(560, 355));	
		parametersPanel.setMinimumSize(new Dimension(560, 355));
		parametersPanel.setMaximumSize(new Dimension(560, 355));				
		parametersPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("Select which parameters to include in the file")));
		parametersPanel.setLayout(new BoxLayout(parametersPanel, BoxLayout.PAGE_AXIS));			
			
		organizationCheckBox = new JCheckBox("Organization (owner)");
		organizationCheckBox.setSelected(true);
		parametersPanel.add(organizationCheckBox);
		stationNameCheckBox = new JCheckBox("Station name");
		stationNameCheckBox.setSelected(true);
		parametersPanel.add(stationNameCheckBox);
		stationTypeCheckBox = new JCheckBox("Station type");
		stationTypeCheckBox.setSelected(true);
		parametersPanel.add(stationTypeCheckBox);
		positionCheckBox = new JCheckBox("Position");
		positionCheckBox.setSelected(true);		
		parametersPanel.add(positionCheckBox);
		mmsiNumberCheckBox = new JCheckBox("MMSI number");
		mmsiNumberCheckBox.setSelected(true);		
		parametersPanel.add(mmsiNumberCheckBox);
		transmissionPowerCheckBox = new JCheckBox("Transmission power");
		transmissionPowerCheckBox.setSelected(true);		
		parametersPanel.add(transmissionPowerCheckBox);
		antennaTypeCheckBox = new JCheckBox("Antenna type");
		antennaTypeCheckBox.setSelected(true);		
		parametersPanel.add(antennaTypeCheckBox);
		antennaHeightCheckBox = new JCheckBox("Antenna height above terrain");
		antennaHeightCheckBox.setSelected(true);		
		parametersPanel.add(antennaHeightCheckBox);		
		terrainHeightCheckBox = new JCheckBox("Terrain height above sealevel");
		terrainHeightCheckBox.setSelected(true);		
		parametersPanel.add(terrainHeightCheckBox);
		antennaHeadingCheckBox = new JCheckBox("Antenna heading (for directional antennas)");
		antennaHeadingCheckBox.setSelected(true);		
		parametersPanel.add(antennaHeadingCheckBox);
		antennaFieldOfViewAngleCheckBox = new JCheckBox("Antenna field of view angle (for directional antennas)");
		antennaFieldOfViewAngleCheckBox.setSelected(true);		
		parametersPanel.add(antennaFieldOfViewAngleCheckBox);		
		antennaGainCheckBox = new JCheckBox("Antenna gain (for directional antennas)");
		antennaGainCheckBox.setSelected(true);		
		parametersPanel.add(antennaGainCheckBox);		
		fatdmaParametersCheckBox = new JCheckBox("FATDMA parameters (frequency, startslot, block size, increment, usage)");
		fatdmaParametersCheckBox.setSelected(true);		
		parametersPanel.add(fatdmaParametersCheckBox);

		JPanel cvsSettingsPanel = new JPanel();
		cvsSettingsPanel.setPreferredSize(new Dimension(560, 65));	
		cvsSettingsPanel.setMinimumSize(new Dimension(560, 65));
		cvsSettingsPanel.setMaximumSize(new Dimension(560, 65));			
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
		
		JPanel containerPanel = new JPanel();
		containerPanel.setLayout(new BorderLayout());
		containerPanel.add(parametersPanel, BorderLayout.NORTH);			
		containerPanel.add(cvsSettingsPanel, BorderLayout.CENTER);	
		getContentPane().add(containerPanel, BorderLayout.CENTER);
			
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

			if (!organizationCheckBox.isSelected() && !stationNameCheckBox.isSelected() && !stationTypeCheckBox.isSelected() &&
					!positionCheckBox.isSelected() && !mmsiNumberCheckBox.isSelected() && !transmissionPowerCheckBox.isSelected() &&
					!antennaTypeCheckBox.isSelected() && !antennaHeightCheckBox.isSelected() && !terrainHeightCheckBox.isSelected() &&
					!antennaHeadingCheckBox.isSelected() && !antennaFieldOfViewAngleCheckBox.isSelected() &&
					!antennaGainCheckBox.isSelected() && !fatdmaParametersCheckBox.isSelected()) {
				JOptionPane.showMessageDialog(parent, "No parameters selected for the file", "Error", JOptionPane.ERROR_MESSAGE); 				
			} else {
				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));  
				fileChooser.addChoosableFileFilter(new StationsCSVFilter());
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
			}

		} else if (e.getSource() == cancelButton) {
			this.dispose();
		}
	}	

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
			
			if (organizationCheckBox.isSelected()) {
				titles.add("Organization (owner)");
			}
			if (stationNameCheckBox.isSelected()) {
				titles.add("Station name");
			}
			if (stationTypeCheckBox.isSelected()) {
				titles.add("Station type");
			}
			titles.add("Station status");
			if (positionCheckBox.isSelected()) {
				titles.add("Latitude");
				titles.add("Longitude");
			}
			if (mmsiNumberCheckBox.isSelected()) {
				titles.add("MMSI number");
			}
			if (transmissionPowerCheckBox.isSelected()) {
				titles.add("Transmission power");
			}			
			if (antennaTypeCheckBox.isSelected()) {
				titles.add("Antenna type");
			}	
			if (antennaHeightCheckBox.isSelected()) {
				titles.add("Antenna height above terrain");
			}	
			if (terrainHeightCheckBox.isSelected()) {
				titles.add("Terrain height above sealevel");
			}	
			if (antennaHeadingCheckBox.isSelected()) {
				titles.add("Antenna heading");
			}	
			if (antennaFieldOfViewAngleCheckBox.isSelected()) {
				titles.add("Antenna field of view angle");
			}				
			if (antennaGainCheckBox.isSelected()) {
				titles.add("Antenna gain");
			}				
			if (fatdmaParametersCheckBox.isSelected()) {
				titles.add("FATDMA frequency");
				titles.add("FATDMA startslot");
				titles.add("FATDMA block size");
				titles.add("FATDMA increment");
				titles.add("FATDMA usage");			
			}	
						
			String[] titlesArray = new String[titles.size()];
			titles.toArray(titlesArray);
			writer.writeNext(titlesArray, false);
			
			EAVDAMData data = DBHandler.getData();		
		
			if (data != null) {
		
				Map<String, Boolean> currentSelections = stationLayer.getCurrentSelections();

				boolean showAISBaseStation = stationLayer.getShowAISBaseStationCheckBox().isSelected();
				boolean showAISRepeater = stationLayer.getShowAISRepeaterCheckBox().isSelected();
				boolean showAISReceiverStation = stationLayer.getShowAISReceiverStationCheckBox().isSelected();
				boolean showAISAtonStation = stationLayer.getShowAISAtonStationCheckBox().isSelected();			
			
				List<ActiveStation> activeStations = data.getActiveStations();
				if (activeStations != null) {
					for (ActiveStation as : activeStations) {
						if (as.getStations() != null) {
							for (AISFixedStationData stationData : as.getStations()) {
								if ((stationData.getStationType() == AISFixedStationType.BASESTATION && showAISBaseStation) ||
										(stationData.getStationType() == AISFixedStationType.REPEATER && showAISRepeater) ||
										(stationData.getStationType() == AISFixedStationType.RECEIVER && showAISReceiverStation) ||
										(stationData.getStationType() == AISFixedStationType.ATON && showAISAtonStation)) {
									if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_ACTIVE) {
										if (currentSelections.containsKey("Own operative stations /// " + stationData.getStationName())) {
											if (currentSelections.get("Own operative stations /// " + stationData.getStationName()).booleanValue() == true) {
												List<String[]> stationDataArrs = getStationDataArrs(stationData);
												for (String[] stationDataArr : stationDataArrs) {
													writer.writeNext(stationDataArr, false);
												}		
											}
										} else {
											List<String[]> stationDataArrs = getStationDataArrs(stationData);
											for (String[] stationDataArr : stationDataArrs) {
												writer.writeNext(stationDataArr, false);
											}							
										}
									} else if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_PLANNED) {
										if (currentSelections.containsKey("Own planned stations /// " + stationData.getStationName())) {
											if (currentSelections.get("Own planned stations /// " + stationData.getStationName()).booleanValue() == true) {
												List<String[]> stationDataArrs = getStationDataArrs(stationData);
												for (String[] stationDataArr : stationDataArrs) {
													writer.writeNext(stationDataArr, false);
												}													
											}							
										} else {
											List<String[]> stationDataArrs = getStationDataArrs(stationData);
											for (String[] stationDataArr : stationDataArrs) {
												writer.writeNext(stationDataArr, false);
											}									
										}
									}
								}
							}
						}
					}
				}

				if (data.getSimulatedStations() != null) {
					for (Simulation s : data.getSimulatedStations()) {
						List<AISFixedStationData> stations = s.getStations();
						for (AISFixedStationData stationData : stations) {
							if ((stationData.getStationType() == AISFixedStationType.BASESTATION && showAISBaseStation) ||
									(stationData.getStationType() == AISFixedStationType.REPEATER && showAISRepeater) ||
									(stationData.getStationType() == AISFixedStationType.RECEIVER && showAISReceiverStation) ||
									(stationData.getStationType() == AISFixedStationType.ATON && showAISAtonStation)) {					
								if (currentSelections.containsKey("Simulation: " + s.getName() + " /// " + stationData.getStationName())) {
									if (currentSelections.get("Simulation: " + s.getName() + " /// " + stationData.getStationName()).booleanValue() == true) {
										List<String[]> stationDataArrs = getStationDataArrs(stationData);
										for (String[] stationDataArr : stationDataArrs) {
											writer.writeNext(stationDataArr, false);
										}	
									}							
								} else {
									List<String[]> stationDataArrs = getStationDataArrs(stationData);
									for (String[] stationDataArr : stationDataArrs) {
										writer.writeNext(stationDataArr, false);
									}						
								}
							}
						}
					}
				}   
				
				if (data.getOtherUsersStations() != null) {
					for (OtherUserStations ous : data.getOtherUsersStations()) {
						EAVDAMUser user = ous.getUser();
						List<ActiveStation> otherUsersActiveStations = ous.getStations();
						for (ActiveStation as : otherUsersActiveStations) {
							List<AISFixedStationData> stations = as.getStations();
							for (AISFixedStationData station : stations) {
								if ((station.getStationType() == AISFixedStationType.BASESTATION && showAISBaseStation) ||
										(station.getStationType() == AISFixedStationType.REPEATER && showAISRepeater) ||
										(station.getStationType() == AISFixedStationType.RECEIVER && showAISReceiverStation) ||
										(station.getStationType() == AISFixedStationType.ATON && showAISAtonStation)) {						
									if (station.getStatus().getStatusID() == DerbyDBInterface.STATUS_ACTIVE) {
										if (currentSelections.containsKey("Stations of organization: " + user.getOrganizationName() + " /// " + station.getStationName())) {
											if (currentSelections.get("Stations of organization: " + user.getOrganizationName() + " /// " + station.getStationName()).booleanValue() == true) {
												List<String[]> stationDataArrs = getStationDataArrs(station);
												for (String[] stationDataArr : stationDataArrs) {
													writer.writeNext(stationDataArr, false);
												}
											}							
										} else {
											List<String[]> stationDataArrs = getStationDataArrs(station);
											for (String[] stationDataArr : stationDataArrs) {
												writer.writeNext(stationDataArr, false);
											}					
										}		
									}
								}
							}
						}
					}
				}
			
			}

			writer.close();
			this.dispose();
			JOptionPane.showMessageDialog(parent, "The station data was exported succesfully!", "Operation succesful", JOptionPane.INFORMATION_MESSAGE); 		
		} catch (IOException e) {
			this.dispose();
			JOptionPane.showMessageDialog(parent, "The following error occurred when exporting station data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 					
		}
	}
	
	private List<String[]> getStationDataArrs(AISFixedStationData stationData) {
	
		List<String[]> stationArr = new ArrayList<String[]>();
			
		if (fatdmaParametersCheckBox.isSelected()) {
			
			if (stationData.getFATDMAChannelA() != null) {
				
				String frequency = stationData.getFATDMAChannelA().getChannelName();
				
				if (stationData.getFATDMAChannelA() instanceof AISBaseAndReceiverStationFATDMAChannel) {
				
					AISBaseAndReceiverStationFATDMAChannel fatdmaChannel = (AISBaseAndReceiverStationFATDMAChannel) stationData.getFATDMAChannelA();
					List<FATDMAReservation> fatdmaScheme = fatdmaChannel.getFATDMAScheme();
					
					for (FATDMAReservation fatdmaReservation : fatdmaScheme) {											
					
						List<String> temp = getBaseList(stationData);
						
						temp.add(frequency);
						temp.add(fatdmaReservation.getStartslot().toString());
						temp.add(fatdmaReservation.getBlockSize().toString());
						temp.add(fatdmaReservation.getIncrement().toString());
						temp.add(fatdmaReservation.getUsage());
						
						String[] tempArray = new String[temp.size()];
						temp.toArray(tempArray);
						stationArr.add(tempArray);
						
					}
				
				} else if (stationData.getFATDMAChannelA() instanceof AISAtonStationFATDMAChannel) {
				
					AISAtonStationFATDMAChannel fatdmaChannel = (AISAtonStationFATDMAChannel) stationData.getFATDMAChannelA();
					List<AtonMessageBroadcastRate> atonMessageBroadcastList = fatdmaChannel.getAtonMessageBroadcastList();
					
					for (AtonMessageBroadcastRate atonMessageBroadcastRate : atonMessageBroadcastList) {
					
						List<String> temp = getBaseList(stationData);
						
						temp.add(frequency);
						temp.add(atonMessageBroadcastRate.getStartslot().toString());
						temp.add(atonMessageBroadcastRate.getBlockSize().toString());
						temp.add(atonMessageBroadcastRate.getIncrement().toString());
						temp.add(atonMessageBroadcastRate.getUsage());
						
						String[] tempArray = new String[temp.size()];
						temp.toArray(tempArray);
						stationArr.add(tempArray);
						
					}
					
				}
				
			}
					
			if (stationData.getFATDMAChannelB() != null) {
				
				String frequency = stationData.getFATDMAChannelB().getChannelName();
				
				if (stationData.getFATDMAChannelB() instanceof AISBaseAndReceiverStationFATDMAChannel) {
				
					AISBaseAndReceiverStationFATDMAChannel fatdmaChannel = (AISBaseAndReceiverStationFATDMAChannel) stationData.getFATDMAChannelB();
					List<FATDMAReservation> fatdmaScheme = fatdmaChannel.getFATDMAScheme();
					
					for (FATDMAReservation fatdmaReservation : fatdmaScheme) {											
					
						List<String> temp = getBaseList(stationData);
						
						temp.add(frequency);
						temp.add(fatdmaReservation.getStartslot().toString());
						temp.add(fatdmaReservation.getBlockSize().toString());
						temp.add(fatdmaReservation.getIncrement().toString());
						temp.add(fatdmaReservation.getUsage());
						
						String[] tempArray = new String[temp.size()];
						temp.toArray(tempArray);
						stationArr.add(tempArray);
						
					}
				
				} else if (stationData.getFATDMAChannelB() instanceof AISAtonStationFATDMAChannel) {
				
					AISAtonStationFATDMAChannel fatdmaChannel = (AISAtonStationFATDMAChannel) stationData.getFATDMAChannelB();
					List<AtonMessageBroadcastRate> atonMessageBroadcastList = fatdmaChannel.getAtonMessageBroadcastList();
					
					for (AtonMessageBroadcastRate atonMessageBroadcastRate : atonMessageBroadcastList) {
					
						List<String> temp = getBaseList(stationData);
						
						temp.add(frequency);
						temp.add(atonMessageBroadcastRate.getStartslot().toString());
						temp.add(atonMessageBroadcastRate.getBlockSize().toString());
						temp.add(atonMessageBroadcastRate.getIncrement().toString());
						temp.add(atonMessageBroadcastRate.getUsage());
						
						String[] tempArray = new String[temp.size()];
						temp.toArray(tempArray);
						stationArr.add(tempArray);
						
					}
					
				}
				
			}					
		
		} else {
		
			List<String> temp = getBaseList(stationData);			
			String[] tempArray = new String[temp.size()];
			temp.toArray(tempArray);
			stationArr.add(tempArray);
		
		}
					
		return stationArr;
	}

	private List<String> getBaseList(AISFixedStationData stationData) {
	
		List<String> baseList = new ArrayList<String>();
					
		if (organizationCheckBox.isSelected()) {
			if (stationData.getOperator() != null && stationData.getOperator().getOrganizationName() != null) {
				baseList.add(stationData.getOperator().getOrganizationName());
			} else {
				baseList.add("");
			}
		}

		if (stationNameCheckBox.isSelected()) {
			if (stationData.getStationName() != null) {
				baseList.add(stationData.getStationName());
			} else {
				baseList.add("");
			}		
		}
		
		if (stationTypeCheckBox.isSelected()) {
			if (stationData.getStationType() != null) {
				String stationType = "";
				if (stationData.getStationType() == AISFixedStationType.BASESTATION) {
					stationType = "AIS Base Station";
				} else if (stationData.getStationType() == AISFixedStationType.REPEATER) {
					stationType = "AIS Repeater";
				} else if (stationData.getStationType() == AISFixedStationType.RECEIVER) {
					stationType = "AIS Receiver Station";
				} else if (stationData.getStationType() == AISFixedStationType.ATON) {
					stationType = "AIS Aton Station";
				}
				baseList.add(stationType);
			} else {
				baseList.add("");
			}
		}
		
		if (stationData.getStatus() != null) {
			String stationStatus = "";
			if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_ACTIVE) {
				stationStatus = "Operative";
			} else if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_PLANNED) {
				stationStatus = "Planned";				
			} else if (stationData.getStatus().getStatusID() == DerbyDBInterface.STATUS_SIMULATED) {
				stationStatus = "Simulated";	
			}
			baseList.add(stationStatus);
		} else {
			baseList.add("");
		}
		
		if (positionCheckBox.isSelected()) {
			baseList.add(String.valueOf(stationData.getLat()).replace(".", ","));
			baseList.add(String.valueOf(stationData.getLon()).replace(".", ","));
		}
		
		if (mmsiNumberCheckBox.isSelected()) {
			if (stationData.getMmsi() != null) {
				baseList.add(stationData.getMmsi());
			} else {
				baseList.add("");
			}
		}

		if (transmissionPowerCheckBox.isSelected()) {
			if (stationData.getTransmissionPower() != null) {
				baseList.add(stationData.getTransmissionPower().toString().replace(".", ","));
			} else {
				baseList.add("");
			}
		}
		
		if (antennaTypeCheckBox.isSelected()) {
			if (stationData.getAntenna() != null && stationData.getAntenna().getAntennaType() != null) {
				String antennaType = "";
				if (stationData.getAntenna().getAntennaType() == AntennaType.OMNIDIRECTIONAL) {
					antennaType = "Omnidirectional";
				} else if (stationData.getAntenna().getAntennaType() == AntennaType.DIRECTIONAL) {
					antennaType = "Directional";
				}
				baseList.add(antennaType);
			} else {
				baseList.add("");
			}
		}
			
		if (antennaHeightCheckBox.isSelected()) {
			if (stationData.getAntenna() != null) {
				baseList.add(String.valueOf(stationData.getAntenna().getAntennaHeight()));
			} else {
				baseList.add("");
			}
		}

		if (terrainHeightCheckBox.isSelected()) {
			if (stationData.getAntenna() != null) {
				baseList.add(String.valueOf(stationData.getAntenna().getTerrainHeight()));
			} else {
				baseList.add("");
			}			
		}
		
		if (antennaHeadingCheckBox.isSelected()) {
			if (stationData.getAntenna() != null && stationData.getAntenna().getHeading() != null) {
				baseList.add(stationData.getAntenna().getHeading().toString());
			} else {
				baseList.add("");
			}
		}
		
		if (antennaFieldOfViewAngleCheckBox.isSelected()) {
			if (stationData.getAntenna() != null && stationData.getAntenna().getFieldOfViewAngle() != null) {
				baseList.add(stationData.getAntenna().getFieldOfViewAngle().toString());
			} else {
				baseList.add("");
			}	
		}
			
		if (antennaGainCheckBox.isSelected()) {
			if (stationData.getAntenna() != null && stationData.getAntenna().getGain() != null) {
				baseList.add(stationData.getAntenna().getGain().toString());
			} else {
				baseList.add("");
			}
		}
	
		return baseList;
	}
	
}


class StationsCSVFilter extends FileFilter {

	public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".csv");
    }
	
    public String getDescription() {
        return "*.csv";
    }
	
}