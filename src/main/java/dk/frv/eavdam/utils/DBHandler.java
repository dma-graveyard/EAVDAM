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
        if (data != null) { // for testing before the database works 
            return data;
        }
        try {
            DerbyDBInterface d = new DerbyDBInterface();
            //d.createDatabase(null);
            ArrayList<EAVDAMData> data = d.retrieveAllEAVDAMData();
            if (data != null && !data.isEmpty()) {
                return data.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void saveData(EAVDAMData data) {
        DBHandler.data = data;   // for testing before the database works 
        DerbyDBInterface d = new DerbyDBInterface();
        //d.createDatabase(null);
        
        d.insertEAVDAMData(data);

    }
    
    public static void saveUserData(EAVDAMUser user){
    	DerbyDBInterface d = new DerbyDBInterface();
        //d.createDatabase(null);
    	try{
    		int id = d.insertEAVDAMUser(user, false);
    		System.out.println("Added user under id "+id+".");
    		EAVDAMUser test = d.retrieveDefaultUser();
    		System.out.println("Default user = "+test.getOrganizationName());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
}
        