package dk.frv.eavdam.io;

import java.io.File;

import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.EAVDAMData;

public class XMLImporterTest {

	public static void main(String[] args) {
		try {
			EAVDAMData data = XMLImporter.readXML(new File(
					"C:\\Projects\\Damsa\\eavdam\\import\\import.xml"));
			
			for (AISFixedStationData d:data.getStations()) {
				System.out.println(d.getStationName()+" - "+d.getLat()+", "+d.getLon());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
