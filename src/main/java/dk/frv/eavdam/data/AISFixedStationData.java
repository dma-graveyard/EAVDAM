package dk.frv.eavdam.data;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A class for holding all properties of an AIS base station.
 * 
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
 */
public class AISFixedStationData {

	/**
	 * Descriptive name for the station (E.g. 'Bornholm AIS Base Station' or
	 * 'W24 AIS AtoN Station').
	 */
	private String stationName;
	/**
	 * Latitude in decimal degrees, datum must be WGS84
	 */
	private double lat;
	/**
	 * Longitude in decimal degrees, datum must be WGS84
	 */
	private double lon;
	/**
	 * MMSI number (optional for receivers)
	 */
	private String mmsi;
	/**
	 * Transmission power in Watts.
	 */
	private Double transmissionPower;
	/**
	 * A free text description of the station.
	 */
	private String description;

	/**
	 * Transmission coverage information for the station.
	 */
	private AISFixedStationCoverage transmissionCoverage;
	
	/**
	 * Receive coverage information for the station.
	 */
	private AISFixedStationCoverage receiveCoverage;
	
	/**
	 * Interference coverage information for the station.
	 */
	private AISFixedStationCoverage interferenceCoverage;
	
	/**
	 * Antenna information for the station.
	 */
	private Antenna antenna;
	/**
	 * FATDMA channels
	 */
	private FATDMAChannel fatdmaChannelA;
	private FATDMAChannel fatdmaChannelB;
	/**
	 * FATDMA allocation information for the station.
	 */
	private FATDMASlotAllocation fatdmaAllocation;
	/**
	 * Type of the fixed AIS station.
	 */
	private AISFixedStationType stationType;
	/**
	 * Responsible operator (user of EAVDAM) of the station.
	 */
	private EAVDAMUser operator;
	/**
	 * 
	 */
	private AISFixedStationStatus status;
	/**
	 * Anything can go in here. The schema allows unknown XML content in the
	 * end.
	 */
	private List<Element> anything;
	
	/**
	 * Organization that has proposed this station.
	 */
	private int proposee;
	
	/**
	 * Id of the station to which the planned station maps to.
	 * 
	 */
	private int refStationID;
	
	/**
	 * ID that is used in the database.
	 * 
	 */
	private int stationDBID;

	public AISFixedStationData() {
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		if (stationName == null || stationName.trim().length() == 0) {
			throw new IllegalArgumentException("Station name must be given");
		}
		this.stationName = stationName.trim();
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		if (lat < -90 || lat > 90) {
			throw new IllegalArgumentException("Latitude not in range [-90 90]");
		}
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		if (lon < -180 || lon > 180) {
			throw new IllegalArgumentException(
					"Longitude not in range [-180 180]");
		}
		this.lon = lon;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
	    // mmsi may be less than 9 digits but should then be prepended with zeros
	    if (mmsi != null && mmsi.length() < 9) {
	        String prefix = "";
	        for (int i=0; i<9-mmsi.length(); i++) {
	            prefix += "0";
	        }	        
	        mmsi = prefix + mmsi;
	    }
		if (mmsi != null && !mmsi.matches("\\d{9}")) {
			throw new IllegalArgumentException(
					"MMSI invalid, must contain 9 digits");
		}
		this.mmsi = mmsi;
	}

	public Double getTransmissionPower() {
		return transmissionPower;
	}

	public void setTransmissionPower(Double transmissionPower) {
		if (transmissionPower != null && transmissionPower < 0) {
			throw new IllegalArgumentException(
					"Transmission power must be non-negative");
		}
		this.transmissionPower = transmissionPower;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null) {
			this.description = description.trim();
		} else {
			this.description = null;
		}
	}

	public AISFixedStationCoverage getTransmissionCoverage() {
		return transmissionCoverage;
	}

	public void setTransmissionCoverage(AISFixedStationCoverage coverage) {
		this.transmissionCoverage = coverage;
	}

	public Antenna getAntenna() {
		return antenna;
	}

	public void setAntenna(Antenna antenna) {
		this.antenna = antenna;
	}

	public FATDMAChannel getFATDMAChannelA() {
		return fatdmaChannelA;
	}
	
	public void setFATDMAChannelA(FATDMAChannel fatdmaChannelA) {
		this.fatdmaChannelA = fatdmaChannelA;
	}
	
	public FATDMAChannel getFATDMAChannelB() {
		return fatdmaChannelB;
	}	
	
	public void setFATDMAChannelB(FATDMAChannel fatdmaChannelB) {
		this.fatdmaChannelB = fatdmaChannelB;
	}	
	
	public FATDMASlotAllocation getFatdmaAllocation() {
		return fatdmaAllocation;
	}

	public void setFatdmaAllocation(FATDMASlotAllocation fatdmaAllocation) {
		this.fatdmaAllocation = fatdmaAllocation;
	}

	public AISFixedStationType getStationType() {
		return stationType;
	}

	public void setStationType(AISFixedStationType stationType) {
		this.stationType = stationType;
	}

	public EAVDAMUser getOperator() {
		return operator;
	}

	public void setOperator(EAVDAMUser operator) {
		this.operator = operator;
	}

	public AISFixedStationStatus getStatus() {
		return status;
	}

	public void setStatus(AISFixedStationStatus status) {
		this.status = status;
	}

	public List<Element> getAnything() {
		return anything;
	}

	public void setAnything(List<Element> anything) {
		this.anything = anything;
	}

	public String getAnythingString() throws TransformerException {
		if (this.anything != null) {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");

			StringBuilder sb = new StringBuilder();
			for (Element e : this.anything) {
				sb.append(e.getLocalName());
				sb.append(" = ");
				NodeList children = e.getChildNodes();
				StringWriter buffer = new StringWriter();
				for (int i = 0; i < children.getLength(); i++) {
					transformer.transform(new DOMSource(children.item(i)),
							new StreamResult(buffer));
				}
				sb.append(buffer.toString());
				sb.append("\n");
			}
			return sb.toString();
		}
		return null;
	}

	@Override
	public String toString() {
		return "AISFixedStationData [stationName=" + stationName + ", lat="
				+ lat + ", lon=" + lon + ", mmsi=" + mmsi
				+ ", transmissionPower=" + transmissionPower + ", description="
				+ description + ", transmitCoverage=" + transmissionCoverage + ", antenna="
				+ antenna + ", fatdmaChannelA=" + fatdmaChannelA.toString()
				+ ", fatdmaChannelB=" + fatdmaChannelB.toString()
				+ ", stationType=" + stationType + ", operator=" + operator
				+ ", status=" + status + ", anything=" + anything + "]";
	}

	public int getProposee() {
		return proposee;
	}

	public void setProposee(int proposee) {
		this.proposee = proposee;
	}

	public int getStationDBID() {
		return stationDBID;
	}

	public void setStationDBID(int stationDBID) {
		this.stationDBID = stationDBID;
	}

	public int getRefStationID() {
		return refStationID;
	}

	public void setRefStationID(int refStationID) {
		this.refStationID = refStationID;
	}

	public AISFixedStationCoverage getReceiveCoverage() {
		return receiveCoverage;
	}

	public void setReceiveCoverage(AISFixedStationCoverage receiveCoverage) {
		this.receiveCoverage = receiveCoverage;
	}

	public AISFixedStationCoverage getInterferenceCoverage() {
		return interferenceCoverage;
	}

	public void setInterferenceCoverage(AISFixedStationCoverage interferenceCoverage) {
		this.interferenceCoverage = interferenceCoverage;
	}
	
	public List<Integer> getReservedBlocksForChannelA() {
		
		List<Integer> reservedBlocks = null;
		
		if (fatdmaChannelA != null) {
			if (fatdmaChannelA instanceof AISBaseAndReceiverStationFATDMAChannel) {
				AISBaseAndReceiverStationFATDMAChannel chA = (AISBaseAndReceiverStationFATDMAChannel) fatdmaChannelA;
				List<FATDMAReservation> fatdmaScheme = chA.getFATDMAScheme();
				if (fatdmaScheme != null) {
					for (FATDMAReservation fatdmaReservation : fatdmaScheme) {
						Integer startslot = fatdmaReservation.getStartslot();
				        Integer blockSize = fatdmaReservation.getBlockSize();
				        Integer increment = fatdmaReservation.getIncrement();
						if (startslot != null && blockSize != null && increment != null) {
							int startslotInt = startslot.intValue();
							int blockSizeInt = blockSize.intValue();
							int incrementInt = increment.intValue();
							if (reservedBlocks == null) {
								reservedBlocks = new ArrayList<Integer>();
							}
							if (incrementInt == 0) {
								for (int i=0; i<blockSizeInt; i++) {
									reservedBlocks.add(new Integer(startslotInt+i));
								}								
							} else if (incrementInt > 0) {
								int i = 0;
								while (i*incrementInt <= 2249) {							
									for (int j=0; j<blockSizeInt; j++) {
										reservedBlocks.add(new Integer(startslotInt+j+(i*incrementInt)));
									}
									i++;
								}
							}
						}
					}
				}
			} else if (fatdmaChannelA instanceof AISAtonStationFATDMAChannel) {
				AISAtonStationFATDMAChannel chA = (AISAtonStationFATDMAChannel) fatdmaChannelA;
				List<AtonMessageBroadcastRate> atonMessageBroadcastList = chA.getAtonMessageBroadcastList();
				if (atonMessageBroadcastList != null) {
					for (AtonMessageBroadcastRate atonMessageBroadcastRate : atonMessageBroadcastList) {
						Integer startslot = atonMessageBroadcastRate.getStartslot();
				        Integer blockSize = atonMessageBroadcastRate.getBlockSize();
				        Integer increment = atonMessageBroadcastRate.getIncrement();
						if (startslot != null && blockSize != null && increment != null) {
							int startslotInt = startslot.intValue();
							int blockSizeInt = blockSize.intValue();
							int incrementInt = increment.intValue();
							if (reservedBlocks == null) {
								reservedBlocks = new ArrayList<Integer>();
							}							
							if (incrementInt == 0) {
								for (int i=0; i<blockSizeInt; i++) {
									reservedBlocks.add(new Integer(startslotInt+i));
								}								
							} else if (incrementInt > 0) {
								int i = 0;
								while (i*incrementInt <= 2249) {							
									for (int j=0; j<blockSizeInt; j++) {
										reservedBlocks.add(new Integer(startslotInt+j+(i*incrementInt)));
									}
									i++;
								}
							}
						}
					}
				}
			}				
		}

		return reservedBlocks;
	}
	
	public List<Integer> getReservedBlocksForChannelB() {
		
		List<Integer> reservedBlocks = null;
		
		if (fatdmaChannelB != null) {
			if (fatdmaChannelB instanceof AISBaseAndReceiverStationFATDMAChannel) {
				AISBaseAndReceiverStationFATDMAChannel chB = (AISBaseAndReceiverStationFATDMAChannel) fatdmaChannelB;
				List<FATDMAReservation> fatdmaScheme = chB.getFATDMAScheme();
				if (fatdmaScheme != null) {
					for (FATDMAReservation fatdmaReservation : fatdmaScheme) {
						Integer startslot = fatdmaReservation.getStartslot();
				        Integer blockSize = fatdmaReservation.getBlockSize();
				        Integer increment = fatdmaReservation.getIncrement();
						if (startslot != null && blockSize != null && increment != null) {
							int startslotInt = startslot.intValue();
							int blockSizeInt = blockSize.intValue();
							int incrementInt = increment.intValue();
							if (reservedBlocks == null) {
								reservedBlocks = new ArrayList<Integer>();
							}	
							if (incrementInt == 0) {
								for (int i=0; i<blockSizeInt; i++) {
									reservedBlocks.add(new Integer(startslotInt+i));
								}								
							} else if (incrementInt > 0) {
								int i = 0;
								while (i*incrementInt <= 2249) {							
									for (int j=0; j<blockSizeInt; j++) {
										reservedBlocks.add(new Integer(startslotInt+j+(i*incrementInt)));
									}
									i++;
								}
							}
						}
					}
				}
			} else if (fatdmaChannelB instanceof AISAtonStationFATDMAChannel) {
				AISAtonStationFATDMAChannel chB = (AISAtonStationFATDMAChannel) fatdmaChannelB;
				List<AtonMessageBroadcastRate> atonMessageBroadcastList = chB.getAtonMessageBroadcastList();
				if (atonMessageBroadcastList != null) {
					for (AtonMessageBroadcastRate atonMessageBroadcastRate : atonMessageBroadcastList) {
						Integer startslot = atonMessageBroadcastRate.getStartslot();
				        Integer blockSize = atonMessageBroadcastRate.getBlockSize();
				        Integer increment = atonMessageBroadcastRate.getIncrement();
						if (startslot != null && blockSize != null && increment != null) {
							int startslotInt = startslot.intValue();
							int blockSizeInt = blockSize.intValue();
							int incrementInt = increment.intValue();
							if (reservedBlocks == null) {
								reservedBlocks = new ArrayList<Integer>();
							}							
							if (incrementInt == 0) {
								for (int i=0; i<blockSizeInt; i++) {
									reservedBlocks.add(new Integer(startslotInt+i));
								}								
							} else if (incrementInt > 0) {
								int i = 0;
								while (i*incrementInt <= 2249) {							
									for (int j=0; j<blockSizeInt; j++) {
										reservedBlocks.add(new Integer(startslotInt+j+(i*incrementInt)));
									}
									i++;
								}
							}
						}
					}
				}
			}				
		}

		return reservedBlocks;
	}
	
}
































