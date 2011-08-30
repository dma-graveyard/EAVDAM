package dk.frv.eavdam.data;

import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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
	 * Coverage information for the station.
	 */
	private AISFixedStationCoverage coverage;
	/**
	 * Antenna information for the station.
	 */
	private Antenna antenna;
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

	public AISFixedStationData() {
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public Double getTransmissionPower() {
		return transmissionPower;
	}

	public void setTransmissionPower(Double transmissionPower) {
		this.transmissionPower = transmissionPower;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AISFixedStationCoverage getCoverage() {
		return coverage;
	}

	public void setCoverage(AISFixedStationCoverage coverage) {
		this.coverage = coverage;
	}

	public Antenna getAntenna() {
		return antenna;
	}

	public void setAntenna(Antenna antenna) {
		this.antenna = antenna;
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
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

			StringBuilder sb = new StringBuilder();
			for (Element e:this.anything) {
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
				+ description + ", coverage=" + coverage + ", antenna="
				+ antenna + ", fatdmaAllocation=" + fatdmaAllocation
				+ ", stationType=" + stationType + ", operator=" + operator
				+ ", status=" + status + ", anything=" + anything + "]";
	}
}