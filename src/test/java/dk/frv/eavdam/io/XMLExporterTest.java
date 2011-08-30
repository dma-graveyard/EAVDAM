package dk.frv.eavdam.io;

import java.io.File;

import dk.frv.eavdam.data.EAVDAMData;

public class XMLExporterTest {

	public static void main(String[] args) {
		try {
			EAVDAMData data = XMLImporter.readXML(new File(
					"C:\\Projects\\Damsa\\generated\\data3.xml"));
			System.out.println(data);

			XMLExporter.writeXML(data, new File(
					"C:\\Projects\\Damsa\\generated\\export.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
