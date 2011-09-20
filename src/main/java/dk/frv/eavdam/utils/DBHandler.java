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
        
    public static EAVDAMData getData() {        
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
    
}
        