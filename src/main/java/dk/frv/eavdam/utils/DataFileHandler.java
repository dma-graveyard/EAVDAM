package dk.frv.eavdam.utils;

import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.io.XMLImporter;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBException;

public class DataFileHandler {
    
    public static EAVDAMData currentEAVDAMData;
    
    public static String getLatestDataFileName() {
        
        String datafile = "";
        
        File dir = new File("data");
        String[] children = dir.list();
        if (children != null) {
            for (int i=0; i<children.length; i++) {
                String filename = children[i];
                if (filename.endsWith("xml")) {
                    if (filename.compareTo(datafile) > 0) {
                        datafile = filename;
                    }
                }
            }
        }
        
        if (datafile.isEmpty()) {
            return null;
        } else {
            return datafile;
        }        
    }
    
    public static String getNewDataFileName(String organisationName) {
        
        if (organisationName == null || organisationName.isEmpty()) {
        
            organisationName = "undefined_organisation";
        
            try {
                String datafile = getLatestDataFileName();
                if (datafile != null) {        
                    EAVDAMData data = XMLImporter.readXML(new File("data/" + datafile));
                    if (data != null) {
                        EAVDAMUser user = data.getUser();
                        if (user != null) {
                            String temp = user.getOrganizationName();
                            if (temp != null && !temp.isEmpty()) {
                                organisationName = temp;
                            }
                        }
                    }
                }
            } catch (JAXBException e) {
            } catch (MalformedURLException e) {}
        
        }
        
        GregorianCalendar gc = new GregorianCalendar();
        String year = String.valueOf(gc.get(Calendar.YEAR));
        String month = String.valueOf(gc.get(Calendar.MONTH)+1);
        if (month.length() == 1) {
            month = "0" + month;
        }
        String day = String.valueOf(gc.get(Calendar.DAY_OF_MONTH));
        if (day.length() == 1) {
            day = "0" + day;
        }
        String hours = String.valueOf(gc.get(Calendar.HOUR_OF_DAY));
        if (hours.length() == 1) {
            hours = "0" + hours;
        }
        String minutes = String.valueOf(gc.get(Calendar.MINUTE));
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }
        String seconds = String.valueOf(gc.get(Calendar.SECOND));
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }                
                
        return organisationName.replaceAll(" ", "_") + "_" + year + month + day + hours + minutes + seconds + ".xml"; 
    }
    
    public static void deleteOldDataFiles() {
        
        String latestDataFile = getLatestDataFileName();
        
        if (latestDataFile != null) {
            File dir = new File("data");
            String[] children = dir.list();
            if (children != null) {
                for (int i=0; i<children.length; i++) {
                    String filename = children[i];
                    if (filename.endsWith("xml")) {
                        if (!filename.equals(latestDataFile)) {
                            new File("data/" + filename).delete();
                        }
                    }
                }
            }
        }
    }
    
}
        