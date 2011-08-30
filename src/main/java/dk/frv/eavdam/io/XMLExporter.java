package dk.frv.eavdam.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AntennaType;
import dk.frv.eavdam.io.jaxb.Address;
import dk.frv.eavdam.io.jaxb.AisFixedStationCoverage;
import dk.frv.eavdam.io.jaxb.AisFixedStationData;
import dk.frv.eavdam.io.jaxb.AisFixedStationStatus;
import dk.frv.eavdam.io.jaxb.AisFixedStationType;
import dk.frv.eavdam.io.jaxb.Antenna;
import dk.frv.eavdam.io.jaxb.DirectionalAntenna;
import dk.frv.eavdam.io.jaxb.EavdamData;
import dk.frv.eavdam.io.jaxb.EavdamUser;
import dk.frv.eavdam.io.jaxb.FatdmaSlotAllocation;
import dk.frv.eavdam.io.jaxb.ObjectFactory;
import dk.frv.eavdam.io.jaxb.OmnidirectionalAntenna;
import dk.frv.eavdam.io.jaxb.Person;

/**
 * Class that is responsible for converting AIS base station network data
 * (station and coverage details) to common XML structure from EAVDAM internal
 * Java objects.
 * 
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:25
 */
public class XMLExporter {

	/**
	 * 
	 * @param data
	 * @param file
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public static void writeXML(dk.frv.eavdam.data.EAVDAMData data, File file)
			throws JAXBException, FileNotFoundException {
		ObjectFactory objFactory = new ObjectFactory();
		// Root level
		EavdamData xData = objFactory.createEavdamData();
		// User part
		dk.frv.eavdam.data.EAVDAMUser user = data.getUser();
		EavdamUser xUser = objFactory.createEavdamUser();
		xUser.setOrganizationName(user.getOrganizationName());
		xUser.setCountryID(user.getCountryID());
		xUser.setPhone(user.getPhone());
		xUser.setFax(user.getFax());
		if (user.getWww() != null) {
			xUser.setWww(user.getWww().toString());
		}
		xUser.setDescription(user.getDescription());
		xUser.setContact(convert(user.getContact()));
		xUser.setTechnicalContact(convert(user.getTechnicalContact()));
		xUser.setVisitingAddress(convert(user.getVisitingAddress()));
		xUser.setPostalAddress(convert(user.getPostalAddress()));
		xData.setUser(xUser);
		// Stations
		List<AisFixedStationData> xStations = xData.getStation();
		for (AISFixedStationData station : data.getStations()) {
			xStations.add(convert(station));
		}
		// Create file
		JAXBContext jc = JAXBContext.newInstance("dk.frv.eavdam.io.jaxb");
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(
				true));
		marshaller.marshal(xData, new FileOutputStream("jaxbOutput.xml"));
	}

	private static Person convert(dk.frv.eavdam.data.Person person) {
		Person xPerson = new Person();
		xPerson.setName(person.getName());
		xPerson.setEmail(person.getEmail());
		xPerson.setPhone(person.getPhone());
		xPerson.setFax(person.getFax());
		xPerson.setDescription(person.getDescription());
		xPerson.setVisitingAddress(convert(person.getVisitingAddress()));
		xPerson.setPostalAddress(convert(person.getPostalAddress()));
		return xPerson;
	}

	private static Address convert(dk.frv.eavdam.data.Address address) {
		Address xAddress = new Address();
		xAddress.setAddressline1(address.getAddressline1());
		xAddress.setAddressline2(address.getAddressline2());
		xAddress.setZip(address.getZip());
		xAddress.setCity(address.getCity());
		xAddress.setCountry(address.getCountry());
		return xAddress;
	}

	private static AisFixedStationData convert(AISFixedStationData data) {
		AisFixedStationData xData = new AisFixedStationData();
		xData.setStationName(data.getStationName());
		xData.setLat(data.getLat());
		xData.setLon(data.getLon());
		xData.setMmsi(data.getMmsi());
		xData.setTransmissionPower(data.getTransmissionPower());
		xData.setDescription(data.getDescription());
		if (data.getCoverage() != null) {
			xData.getCoverage().addAll(convert(data.getCoverage()));
		}
		xData.setAntenna(convert(data.getAntenna()));
		xData.setFatdmaAllocation(convert(data.getFatdmaAllocation()));
		if (data.getStationType() != null) {
			xData.setStationType(AisFixedStationType.valueOf(data
					.getStationType().toString()));
		}
		if (data.getStatus() != null) {
			xData.setStatus(AisFixedStationStatus.valueOf(data.getStatus()
					.toString()));
		}

		return xData;
	}

	private static List<AisFixedStationCoverage> convert(
			dk.frv.eavdam.data.AISFixedStationCoverage coverage) {
		if (coverage != null) {
			List<AisFixedStationCoverage> xCoverage = new ArrayList<AisFixedStationCoverage>();
			// TODO: coverage
			return xCoverage;
		}
		return null;
	}

	private static Antenna convert(dk.frv.eavdam.data.Antenna antenna) {
		if (antenna != null) {
			Antenna xAntenna = new Antenna();
			if (antenna.getAntennaType() == AntennaType.OMNIDIRECTIONAL) {
				xAntenna.setOmnidirectionalAntenna(new OmnidirectionalAntenna());
			} else {
				DirectionalAntenna da = new DirectionalAntenna();
				da.setHeading(antenna.getHeading());
				da.setFieldOfViewAngle(antenna.getFieldOfViewAngle());
				da.setGain(antenna.getGain());
				xAntenna.setDirectionalAntenna(da);
			}
			xAntenna.setAntennaHeight(antenna.getAntennaHeight());
			xAntenna.setTerrainHeight(antenna.getTerrainHeight());
			return xAntenna;
		}
		return null;
	}

	private static FatdmaSlotAllocation convert(
			dk.frv.eavdam.data.FATDMASlotAllocation allocation) {
		if (allocation != null) {
			FatdmaSlotAllocation xAllocation = new FatdmaSlotAllocation();
			// TODO: fatdma allocation
			return xAllocation;
		}
		return null;
	}

//	public static void main(String[] args) {
//		try {
//			EAVDAMData data = XMLImporter.readXML(new File(
//					"C:\\Projects\\Damsa\\generated\\data.xml"));
//			System.out.println(data);
//			XMLExporter.writeXML(data, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}