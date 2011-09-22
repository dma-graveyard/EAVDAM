package dk.frv.eavdam.io;

import java.io.File;

import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.io.derby.DerbyDBInterface;

public class XMLExporterTest {

	public static void main(String[] args) {

		System.out.println("Writing the xml to file...");
		try {
//			EAVDAMData data = XMLImporter.readXML(new File(
//					"C:\\Projects\\Damsa\\generated\\data3.xml"));
//			System.out.println(data);

			DerbyDBInterface db = new DerbyDBInterface();
    		EAVDAMData data = db.retrieveEAVDAMDataForXML();

    		XMLExporter.writeXML(data, new File("C:\\Projects\\Damsa\\eavdam\\generated\\export.xml"));
    		System.out.println("Writing finished!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
