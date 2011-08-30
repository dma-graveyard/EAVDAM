package dk.frv.eavdam.io;

import java.io.File;

import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.EAVDAMData;

public class XMLImporterTest {

	public static void main(String[] args) {
		try {
			EAVDAMData data = XMLImporter.readXML(new File(
					"C:\\Projects\\Damsa\\generated\\export.xml"));
			System.out.println(data);
			for (AISFixedStationData d:data.getStations()) {
				System.out.println(d.getAnythingString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
