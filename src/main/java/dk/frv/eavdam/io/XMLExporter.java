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
package dk.frv.eavdam.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import dk.frv.eavdam.data.AISAtonStationFATDMAChannel;
import dk.frv.eavdam.data.AISBaseAndReceiverStationFATDMAChannel;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationStatus;
import dk.frv.eavdam.data.AntennaType;
import dk.frv.eavdam.data.AtonMessageBroadcastRate;
import dk.frv.eavdam.data.FATDMAChannel;
import dk.frv.eavdam.data.FATDMAReservation;
import dk.frv.eavdam.data.FATDMASlotAllocation;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import dk.frv.eavdam.io.jaxb.AccessScheme;
import dk.frv.eavdam.io.jaxb.Address;
import dk.frv.eavdam.io.jaxb.AisFixedStationCoverage;
import dk.frv.eavdam.io.jaxb.AisFixedStationCoverageType;
import dk.frv.eavdam.io.jaxb.AisFixedStationData;
import dk.frv.eavdam.io.jaxb.AisFixedStationStatus;
import dk.frv.eavdam.io.jaxb.AisFixedStationType;
import dk.frv.eavdam.io.jaxb.Antenna;
import dk.frv.eavdam.io.jaxb.ChannelBroadcast;
import dk.frv.eavdam.io.jaxb.ChannelType;
import dk.frv.eavdam.io.jaxb.CoveragePoint;
import dk.frv.eavdam.io.jaxb.DirectionalAntenna;
import dk.frv.eavdam.io.jaxb.EavdamData;
import dk.frv.eavdam.io.jaxb.EavdamUser;
import dk.frv.eavdam.io.jaxb.FatdmaChannel;
import dk.frv.eavdam.io.jaxb.FatdmaSlotAllocation;
import dk.frv.eavdam.io.jaxb.ObjectFactory;
import dk.frv.eavdam.io.jaxb.OmnidirectionalAntenna;
import dk.frv.eavdam.io.jaxb.Ownership;
import dk.frv.eavdam.io.jaxb.Person;
import dk.frv.eavdam.io.jaxb.Status;
import dk.frv.eavdam.menus.AboutEAVDAMMenuItem;

/**
 * Class for converting AIS base station network data (station and coverage details)
 * to common XML structure from EAVDAM internal Java objects.
 */
public class XMLExporter {

	/**
	 * Writes XML file.
	 *	 
	 * @param data  Data to be written
	 * @param file  File to which write the data
	 */
	public static void writeXML(dk.frv.eavdam.data.EAVDAMData data, File file)
			throws JAXBException, FileNotFoundException {
		ObjectFactory objFactory = new ObjectFactory();
		// Root level
		EavdamData xData = objFactory.createEavdamData();
		xData.setVersion(AboutEAVDAMMenuItem.version);
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
		if (user.getAnything() != null) {
			xUser.getAny().addAll(user.getAnything());
		}
		xData.setUser(xUser);
		
		try{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(new Date(System.currentTimeMillis()));
			XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			xData.setTimestamp(date2);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//TODO convert active etc. stations?!?!?
		
		// Stations
		List<AisFixedStationData> xStations = xData.getStation();
		for (AISFixedStationData station : data.getStations()) {
			xStations.add(convert(station));
		}
		// Create file
		JAXBContext jc = JAXBContext.newInstance("dk.frv.eavdam.io.jaxb");
		Marshaller marshaller = jc.createMarshaller();
		//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(
		//		true));
		
		ObjectFactory factory = new ObjectFactory();
		JAXBElement<EavdamData> element = factory.createEavdamData(xData);
 		
		marshaller.marshal(element, new FileOutputStream(file));
		

	}

	private static Person convert(dk.frv.eavdam.data.Person person) {
		if (person != null) {
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
		return null;
	}

	private static Address convert(dk.frv.eavdam.data.Address address) {
		if (address != null) {
			Address xAddress = new Address();
			xAddress.setAddressline1(address.getAddressline1());
			xAddress.setAddressline2(address.getAddressline2());
			xAddress.setZip(address.getZip());
			xAddress.setCity(address.getCity());
			xAddress.setCountry(address.getCountry());
			return xAddress;
		}
		return null;
	}

	private static AisFixedStationData convert(AISFixedStationData data) {
		if (data != null) {
			AisFixedStationData xData = new AisFixedStationData();
			xData.setStationName(data.getStationName());
			xData.setLat(data.getLat());
			xData.setLon(data.getLon());
			xData.setMmsi(data.getMmsi());
			xData.setTransmissionPower(data.getTransmissionPower());
			xData.setDescription(data.getDescription());
			if (data.getTransmissionCoverage() != null) {
				List<AisFixedStationCoverage> c = convert(data.getTransmissionCoverage(), DerbyDBInterface.COVERAGE_TRANSMIT);
				if(c != null)
					xData.getCoverage().addAll(c);
			}

			if (data.getReceiveCoverage() != null) {
				List<AisFixedStationCoverage> c = convert(data.getReceiveCoverage(), DerbyDBInterface.COVERAGE_RECEIVE);
				if(c != null)
					xData.getCoverage().addAll(c);
			}
			
			if (data.getInterferenceCoverage() != null) {
				List<AisFixedStationCoverage> c = convert(data.getInterferenceCoverage(), DerbyDBInterface.COVERAGE_INTERFERENCE);
				if(c != null)
					xData.getCoverage().addAll(c);
			}
			
			xData.setAntenna(convert(data.getAntenna()));
			
			xData.setFatdmaAllocation(convert(data.getFATDMAChannelA(), data.getFATDMAChannelB()));
			
			if (data.getStationType() != null) {
				xData.setStationType(AisFixedStationType.valueOf(data
						.getStationType().toString()));
			}
			if (data.getStatus() != null) {
				xData.setStatus(convert(data.getStatus()));
			}
			if (data.getAnything() != null) {
				xData.getAny().addAll(data.getAnything());
			}

			return xData;
		}
		return null;
	}

	private static List<AisFixedStationCoverage> convert(
			dk.frv.eavdam.data.AISFixedStationCoverage coverage, int coverageType) {
		if (coverage != null && coverage.getCoveragePoints() != null) {
			List<AisFixedStationCoverage> xCoverage = new ArrayList<AisFixedStationCoverage>();

			
			
			AisFixedStationCoverage c = new AisFixedStationCoverage();
			switch(coverageType){
				case DerbyDBInterface.COVERAGE_INTERFERENCE: c.setCoverageType(AisFixedStationCoverageType.INTERFERENCE); break;
				case DerbyDBInterface.COVERAGE_TRANSMIT: c.setCoverageType(AisFixedStationCoverageType.TRANSMISSION); break;
				case DerbyDBInterface.COVERAGE_RECEIVE: c.setCoverageType(AisFixedStationCoverageType.RECEIVE); break;
			}
			
			for(double[] p : coverage.getCoveragePoints()){
				CoveragePoint cp = new CoveragePoint();
				cp.setLat(p[0]);
				cp.setLon(p[1]);
				c.getPoint().add(cp);
			}
			
			xCoverage.add(c);
			// TODO: coverage
			return xCoverage;
		}
		return null;
	}

	private static Status convert(dk.frv.eavdam.data.AISFixedStationStatus status){
		if(status != null){
			Status xStatus = new Status();
			if(status.getStatusID() == DerbyDBInterface.STATUS_ACTIVE){
				xStatus.setStatus(AisFixedStationStatus.OPERATIVE);
			}else if(status.getStatusID() == DerbyDBInterface.STATUS_OLD){
				xStatus.setStatus(AisFixedStationStatus.OLD);
			} else if(status.getStatusID() == DerbyDBInterface.STATUS_SIMULATED){
				xStatus.setStatus(AisFixedStationStatus.SIMULATED);
			} else if(status.getStatusID() == DerbyDBInterface.STATUS_PLANNED){
				xStatus.setStatus(AisFixedStationStatus.PLANNED);
			} else if(status.getStatusID() == DerbyDBInterface.STATUS_PROPOSED){
				xStatus.setStatus(AisFixedStationStatus.PROPOSED);
			}  
			
			if(status.getStartDate() != null){
				try{
					GregorianCalendar c = new GregorianCalendar();
					c.setTime(status.getStartDate());
					XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
					xStatus.setStartDate(date2);
				}catch(Exception e){
					e.printStackTrace();
				}
			}	
			
			if(status.getEndDate() != null){
				try{
					GregorianCalendar c = new GregorianCalendar();
					
					c.setTime(status.getEndDate());
					XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
					xStatus.setEndDate(date2);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			return xStatus;
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

	private static FatdmaSlotAllocation convert(dk.frv.eavdam.data.FATDMAChannel channelA, dk.frv.eavdam.data.FATDMAChannel channelB) {
		FatdmaSlotAllocation xAllocation = new FatdmaSlotAllocation();
		if (channelA != null) {
			if(channelA instanceof AISAtonStationFATDMAChannel){
				
				AISAtonStationFATDMAChannel c = (AISAtonStationFATDMAChannel)channelA;
				
				FatdmaChannel channel = new FatdmaChannel();
				channel.setChannelName(c.getChannelName());
				channel.setChannelType(ChannelType.ATON);
				
				for(AtonMessageBroadcastRate r : c.getAtonMessageBroadcastList()){
					ChannelBroadcast b = new ChannelBroadcast();
					
					if(r.getAccessScheme().equals(AtonMessageBroadcastRate.CSTDMA_ACCESS_SCHEME))
						b.setAccessScheme(AccessScheme.CSTDMA);
					else if(r.getAccessScheme().equals(AtonMessageBroadcastRate.FATDMA_ACCESS_SCHEME))
						b.setAccessScheme(AccessScheme.FATDMA);
					else if(r.getAccessScheme().equals(AtonMessageBroadcastRate.RATDMA_ACCESS_SCHEME))
						b.setAccessScheme(AccessScheme.RATDMA);
					
					b.setBlockSize(r.getBlockSize());
					b.setIncrement(r.getIncrement());
					b.setMessageID(r.getMessageID());
					b.setStartSlot(r.getStartslot());
					b.setUtcHour(r.getUTCHour());
					b.setUtcMinute(r.getUTCMinute());
					b.setUsage(r.getUsage());
					
					channel.getBroadcast().add(b);
				}
				
				xAllocation.setFatdmaChannelA(channel);
				
			}else{
				AISBaseAndReceiverStationFATDMAChannel c = (AISBaseAndReceiverStationFATDMAChannel)channelA;
				
				FatdmaChannel channel = new FatdmaChannel();
				channel.setChannelName(c.getChannelName());
				channel.setChannelType(ChannelType.BASE);
				
				for(FATDMAReservation r : c.getFATDMAScheme()){
					ChannelBroadcast b = new ChannelBroadcast();
					
					b.setBlockSize(r.getBlockSize());
					b.setIncrement(r.getIncrement());
					b.setStartSlot(r.getStartslot());
					b.setUsage(r.getUsage());
					
					if(r.getOwnership().equals(FATDMAReservation.STATION_OWNERSHIP_LOCAL))
						b.setOwnership(Ownership.L);
					else
						b.setOwnership(Ownership.R);
					
					channel.getBroadcast().add(b);
				}
				
				xAllocation.setFatdmaChannelA(channel);
			}
		}
		
		if (channelB != null) {
				if(channelB instanceof AISAtonStationFATDMAChannel){
					
					AISAtonStationFATDMAChannel c = (AISAtonStationFATDMAChannel)channelB;
					
					FatdmaChannel channel = new FatdmaChannel();
					channel.setChannelName(c.getChannelName());
					channel.setChannelType(ChannelType.ATON);
					
					for(AtonMessageBroadcastRate r : c.getAtonMessageBroadcastList()){
						ChannelBroadcast b = new ChannelBroadcast();
						
						if(r.getAccessScheme().equals(AtonMessageBroadcastRate.CSTDMA_ACCESS_SCHEME))
							b.setAccessScheme(AccessScheme.CSTDMA);
						else if(r.getAccessScheme().equals(AtonMessageBroadcastRate.FATDMA_ACCESS_SCHEME))
							b.setAccessScheme(AccessScheme.FATDMA);
						else if(r.getAccessScheme().equals(AtonMessageBroadcastRate.RATDMA_ACCESS_SCHEME))
							b.setAccessScheme(AccessScheme.RATDMA);
						
						b.setBlockSize(r.getBlockSize());
						b.setIncrement(r.getIncrement());
						b.setMessageID(r.getMessageID());
						b.setStartSlot(r.getStartslot());
						b.setUtcHour(r.getUTCHour());
						b.setUtcMinute(r.getUTCMinute());
						
						channel.getBroadcast().add(b);
					}
					
					xAllocation.setFatdmaChannelB(channel);
					
				}else{
					AISBaseAndReceiverStationFATDMAChannel c = (AISBaseAndReceiverStationFATDMAChannel)channelB;
					
					FatdmaChannel channel = new FatdmaChannel();
					channel.setChannelName(c.getChannelName());
					channel.setChannelType(ChannelType.BASE);
					
					for(FATDMAReservation r : c.getFATDMAScheme()){
						ChannelBroadcast b = new ChannelBroadcast();
						
						b.setBlockSize(r.getBlockSize());
						b.setIncrement(r.getIncrement());
						b.setStartSlot(r.getStartslot());
						
						if(r.getOwnership().equals(FATDMAReservation.STATION_OWNERSHIP_LOCAL))
							b.setOwnership(Ownership.L);
						else
							b.setOwnership(Ownership.R);
						
						channel.getBroadcast().add(b);
					}
					
					xAllocation.setFatdmaChannelB(channel);
				}
		
		}
		
		if(xAllocation.getFatdmaChannelA() == null && xAllocation.getFatdmaChannelB() == null)
			return null;
		else 
			return xAllocation;
	}
}