package dk.frv.eavdam.utils;

import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.io.XMLExporter;
import dk.frv.eavdam.io.XMLImporter;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Calendar;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DBHandler {
        
    private static EAVDAMData data = new EAVDAMData();  // for testing before the database works        
    private static boolean initialized = false;    
    private static boolean updatedXML = false;
    
    public static EAVDAMData getData() {        
    	
    	if(!initialized){
    		DBHandler.importXML("import/import.xml");
    		initialized = true;
    	}
    	
    	EAVDAMData dat = new EAVDAMData(); 
    	
    	try {
    		DerbyDBInterface d = new DerbyDBInterface();
            EAVDAMUser user = d.retrieveDefaultUser();  
            System.out.println("Retrieved default user: "+user.getOrganizationName());
            dat = d.retrieveAllEAVDAMData(user);
                    
                    
                    

    	} catch (Exception e) {
    		e.printStackTrace();
//    		System.out.println(e.getMessage());
    	}

            
            
        return dat;

    }

    public static void saveData(EAVDAMData data) {
        DBHandler.data = data;   // for testing before the database works
        DerbyDBInterface d = new DerbyDBInterface();
        //d.createDatabase(null);        
        
//        System.out.println("Saving data for user "+data.getUser().getOrganizationName()+" ("+data.getActiveStations().get(0).getStations().get(0).getStationName()+")");
        d.insertEAVDAMData(data);

        if(!updatedXML){
			System.out.println("Writing the xml to file...");
			try {

				DerbyDBInterface db = new DerbyDBInterface();
	    		EAVDAMData export = db.retrieveEAVDAMDataForXML();
	
	    		XMLExporter.writeXML(export, new File("generated/export.xml"));
	    		System.out.println("Writing finished!");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			updatedXML = true;
        }
    }
    
    public static void saveUserData(EAVDAMUser user){
    	DerbyDBInterface d = new DerbyDBInterface();
        //d.createDatabase(null);
    	try{
    		int id = d.insertEAVDAMUser(user, false);
//    		System.out.println("Added user under id "+id+".");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public static void exportXML(String filename){
		System.out.println("Writing the xml to file...");
		try {

			DerbyDBInterface db = new DerbyDBInterface();
    		EAVDAMData data = db.retrieveEAVDAMDataForXML();

    		XMLExporter.writeXML(data, new File(filename));
    		System.out.println("Writing finished!");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void importXML(String filename){
		
		try {
			File inFile = new File(filename);
			if(inFile != null && inFile.exists()){
				System.out.println("Reading the xml to file...");	
				EAVDAMData d = XMLImporter.readXML(inFile);
				System.out.println("Read finished. Storing the data to database...");
				
				DerbyDBInterface db = new DerbyDBInterface();
	    		
				
				
				db.insertEAVDAMData(d);
	    		
	    		System.out.println("Process finished!");
			}else{
				System.out.println("Import file "+inFile.getAbsolutePath()+" does not exist!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
        