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
package dk.frv.eavdam.data;

import dk.frv.eavdam.utils.FATDMAUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	/**
	 * Stores the info about the ownership for each timeslot in both channel A and B.
	 * 
	 * Needed for rules checking.
	 */
	private Map<Integer,String> ownershipA;
	private Map<Integer,String> ownershipB;
	
	/**
	 * For optimizing. No need to get this information several times.
	 */
	private List<Integer> reservedBlocksA = null;
	private List<Integer> reservedBlocksB = null;
	
	/**
	 * Used for optimizing the algorithm.
	 */
	private double[] northCoveragePoint = null; 
	private double[] westCoveragePoint = null;		
	private double[] southCoveragePoint = null; 
	private double[] eastCoveragePoint = null;	
	
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
		
		if(reservedBlocksA != null) return this.reservedBlocksA; 
		
		
		if (fatdmaChannelA != null) {
			if (fatdmaChannelA instanceof AISBaseAndReceiverStationFATDMAChannel) {
				AISBaseAndReceiverStationFATDMAChannel chA = (AISBaseAndReceiverStationFATDMAChannel) fatdmaChannelA;
				List<FATDMAReservation> fatdmaScheme = chA.getFATDMAScheme();
				if (fatdmaScheme != null) {
					for (FATDMAReservation fatdmaReservation : fatdmaScheme) {
						Integer startslot = fatdmaReservation.getStartslot();
				        Integer blockSize = fatdmaReservation.getBlockSize();
				        Integer increment = fatdmaReservation.getIncrement();
						List<Integer> blocks = FATDMAUtils.getBlocks(startslot, blockSize, increment);
						if (blocks != null && !blocks.isEmpty()) {
							if (reservedBlocksA == null) {
								reservedBlocksA = new ArrayList<Integer>();

							}
							if(ownershipA == null){
								ownershipA = new HashMap<Integer, String>();
							}
							for (Integer block : blocks) {
								reservedBlocksA.add(block);
								ownershipA.put(block, fatdmaReservation.getOwnership());
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
						List<Integer> blocks = FATDMAUtils.getBlocks(startslot, blockSize, increment);
						if (blocks != null && !blocks.isEmpty()) {
							if (reservedBlocksA == null) {
								reservedBlocksA = new ArrayList<Integer>();

							}
							if(ownershipA == null){
								ownershipA = new HashMap<Integer, String>();
							}
							for (Integer block : blocks) {
								reservedBlocksA.add(block);
							}
						}						
					}
				}
			}				
		}


		if(this.reservedBlocksA != null){
			//Put them into ascending order. 
			Collections.sort(reservedBlocksA);
		}
		
		return reservedBlocksA;
	}
	
	public List<Integer> getReservedBlocksForChannelB() {
		
		if(this.reservedBlocksB != null) return reservedBlocksB;
		
		if (fatdmaChannelB != null) {
			if (fatdmaChannelB instanceof AISBaseAndReceiverStationFATDMAChannel) {
				AISBaseAndReceiverStationFATDMAChannel chB = (AISBaseAndReceiverStationFATDMAChannel) fatdmaChannelB;
				List<FATDMAReservation> fatdmaScheme = chB.getFATDMAScheme();
				if (fatdmaScheme != null) {
					for (FATDMAReservation fatdmaReservation : fatdmaScheme) {
						Integer startslot = fatdmaReservation.getStartslot();
				        Integer blockSize = fatdmaReservation.getBlockSize();
				        Integer increment = fatdmaReservation.getIncrement();
						List<Integer> blocks = FATDMAUtils.getBlocks(startslot, blockSize, increment);
						if (blocks != null && !blocks.isEmpty()) {
							if (reservedBlocksB == null) {
								reservedBlocksB = new ArrayList<Integer>();

							}
							if(ownershipB == null){
								ownershipB = new HashMap<Integer, String>();
							}
							for (Integer block : blocks) {
								reservedBlocksB.add(block);
								ownershipB.put(block, fatdmaReservation.getOwnership());
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
						List<Integer> blocks = FATDMAUtils.getBlocks(startslot, blockSize, increment);
						if (blocks != null && !blocks.isEmpty()) {
							if (reservedBlocksB == null) {
								reservedBlocksB = new ArrayList<Integer>();

							}
							if(ownershipA == null){
								ownershipA = new HashMap<Integer, String>();
							}
							for (Integer block : blocks) {
								reservedBlocksB.add(block);
							}
						}
					}
				}
			}				
		}


		if(reservedBlocksB != null){
			//Put them into ascending order. 
			Collections.sort(reservedBlocksB);
		}
		
		return reservedBlocksB;
	}
	
	/**
	 * Returns the ownership of the given timeslot in the given channel. Null is return if there is no reservation for the given slot. 
	 * 
	 * @param channel Either A or B.
	 * @param slot Integer of the slot
	 * @return R = remote, L = local, null = no reservation or the station is an aton station.
	 */
	public String getOwnershipInSlot(String channel, Integer slot){
		if(this.stationType.equals(AISFixedStationType.ATON)) return null;
		
		if(channel.equals("A")){
			if(this.ownershipA == null) this.getReservedBlocksForChannelA();
			if(this.ownershipA == null) return null;
			
			return this.ownershipA.get(slot);
		}else if(channel.equals("B")){
			if(this.ownershipB == null) this.getReservedBlocksForChannelB();
			if(this.ownershipB == null) return null;
			
			return this.ownershipB.get(slot);
		}else{
			return null;
		}
	}
	
	/**
	 * Finds the northest point of the coverage. Used to optimize the rule checking.
	 * 
	 * @return
	 */
	public double[] getNorthTransmitCoveragePoints(){
		if(this.northCoveragePoint != null) return this.northCoveragePoint;
		
		if(this.transmissionCoverage == null || this.transmissionCoverage.getCoveragePoints() == null) return null;
		
		double[] maxLat = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLat = {Double.MAX_VALUE,Double.MAX_VALUE};
		double[] maxLon = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLon = {Double.MAX_VALUE,Double.MAX_VALUE};
		
		
		for(double[] p : this.transmissionCoverage.getCoveragePoints()){
			if(p[0] > maxLat[0]) maxLat = p;
			if(p[0] < minLat[0]) minLat = p;
			
			if(p[1] < minLon[1]) minLon = p; 
			if(p[1] > maxLon[1]) maxLon = p;
			
		}
		
		this.northCoveragePoint = maxLat;
		this.westCoveragePoint = minLon;
		this.eastCoveragePoint = maxLon;
		this.southCoveragePoint = minLat;
		
		return maxLat;
	}
	
	public double[] getWestTransmitCoveragePoints(){
		if(this.westCoveragePoint != null) return this.westCoveragePoint;
		
		if(this.transmissionCoverage == null || this.transmissionCoverage.getCoveragePoints() == null) return null;
		
		double[] maxLat = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLat = {Double.MAX_VALUE,Double.MAX_VALUE};
		double[] maxLon = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLon = {Double.MAX_VALUE,Double.MAX_VALUE};
		
		
		for(double[] p : transmissionCoverage.getCoveragePoints()){
			if(p[0] > maxLat[0]) maxLat = p;
			if(p[0] < minLat[0]) minLat = p;
			
			if(p[1] < minLon[1]) minLon = p; 
			if(p[1] > maxLon[1]) maxLon = p;
			
		}
		
		this.northCoveragePoint = maxLat;
		this.westCoveragePoint = minLon;
		this.eastCoveragePoint = maxLon;
		this.southCoveragePoint = minLat;
		
		return minLon;
	}
	
	public double[] getEastTransmitCoveragePoints(){
		if(this.eastCoveragePoint != null) return this.eastCoveragePoint;
		
		if(this.transmissionCoverage == null || this.transmissionCoverage.getCoveragePoints() == null) return null;
		
		double[] maxLat = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLat = {Double.MAX_VALUE,Double.MAX_VALUE};
		double[] maxLon = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLon = {Double.MAX_VALUE,Double.MAX_VALUE};
		
		
		for(double[] p : transmissionCoverage.getCoveragePoints()){
			if(p[0] > maxLat[0]) maxLat = p;
			if(p[0] < minLat[0]) minLat = p;
			
			if(p[1] < minLon[1]) minLon = p; 
			if(p[1] > maxLon[1]) maxLon = p;
			
		}
		
		this.northCoveragePoint = maxLat;
		this.westCoveragePoint = minLon;
		this.eastCoveragePoint = maxLon;
		this.southCoveragePoint = minLat;
		
		return maxLon;
	}
	
	public double[] getSouthTransmitCoveragePoints(){
		if(this.southCoveragePoint != null) return this.southCoveragePoint;
		
		if(this.transmissionCoverage == null || this.transmissionCoverage.getCoveragePoints() == null) return null;
		
		double[] maxLat = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLat = {Double.MAX_VALUE,Double.MAX_VALUE};
		double[] maxLon = {Double.MIN_VALUE,Double.MIN_VALUE};
		double[] minLon = {Double.MAX_VALUE,Double.MAX_VALUE};
		
		
		for(double[] p : transmissionCoverage.getCoveragePoints()){
			if(p[0] > maxLat[0]) maxLat = p;
			if(p[0] < minLat[0]) minLat = p;
			
			if(p[1] < minLon[1]) minLon = p; 
			if(p[1] > maxLon[1]) maxLon = p;
			
		}
		
		this.northCoveragePoint = maxLat;
		this.westCoveragePoint = minLon;
		this.eastCoveragePoint = maxLon;
		this.southCoveragePoint = minLat;
		
		return minLat;
	}
}

