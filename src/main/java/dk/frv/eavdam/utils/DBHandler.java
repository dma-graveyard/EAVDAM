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
package dk.frv.eavdam.utils;

import com.bbn.openmap.gui.OpenMapFrame;
import dk.frv.eavdam.data.AISBaseAndReceiverStationFATDMAChannel;
import dk.frv.eavdam.data.AISDatalinkCheckIssue;
import dk.frv.eavdam.data.AISDatalinkCheckResult;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISSlotMap;
import dk.frv.eavdam.data.ActiveStation;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.FATDMAReservation;
import dk.frv.eavdam.data.FTP;
import dk.frv.eavdam.data.Options;
import dk.frv.eavdam.data.Simulation;
import dk.frv.eavdam.io.FTPHandler;
import dk.frv.eavdam.io.XMLExporter;
import dk.frv.eavdam.io.XMLImporter;
import dk.frv.eavdam.io.derby.DerbyDBInterface;
import dk.frv.eavdam.layers.StationLayer;
import dk.frv.eavdam.menus.IssuesMenuItem;
import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;

/**
 * Class for getting and setting data in the database.
 */
public class DBHandler {
     
	/**
	 * Whether this is the first time calling this class's methods.
	 */
    private static boolean initialized = false;    
  
	/**
	 * Whether data files have been imported from FTP sites in the background and need to be read to the database
	 */
	public static boolean importDataUpdated = false;
	
	/**
	 * Interface for connecting to the database.
	 */
    public static DerbyDBInterface derby = null;
	
	/**
	 * Gets all application data from the database.
	 *
	 * @return  All application data from the database
	 */
    public static EAVDAMData getData() {    
		return getData(null);
    }
	
	/**
	 * Gets all application data from the database.
	 *
	 * @param openMapFrame  OpenMapFrame of the application
	 * @return              All application data from the database
	 */	
    public static EAVDAMData getData(OpenMapFrame openMapFrame) {
	
		if (openMapFrame != null) {
			openMapFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));	
		}
	
		if (derby == null) {
			derby = new DerbyDBInterface();
		}
		
    	EAVDAMData dat = new EAVDAMData();
    	
		if (!initialized){

			// inserts a default read only user if no user is defined		
			try {
				EAVDAMUser user = derby.retrieveDefaultUser();
				if (user == null || user.getOrganizationName() == null || user.getOrganizationName().isEmpty()) {
					user = new EAVDAMUser();
					user.setOrganizationName("read_only_user_" + String.valueOf(new Date().getTime()));
					user.setCountryID("FI");
					saveUserData(user, true);
				}
			} catch (Exception ex) {}
		
			if (openMapFrame != null) {
				new LoadXMLsFromFTPsThread(openMapFrame).start();
			}
	
    		dat = XMLHandler.importData();  // data is read from xml files in the beginning of application


			
    		initialized = true;
			
    	} else {
    	
	    	try {
				if (importDataUpdated && StationLayer.windowReady) {
				    dat = XMLHandler.importData();
					importDataUpdated = false;
					
				} else {
					EAVDAMUser user = derby.retrieveDefaultUser();  					
					dat = derby.retrieveAllEAVDAMData(user);
				}
	    	} catch (Exception e) {
	    		System.out.println(e.getMessage());
				e.printStackTrace();
	    	}

    	}
 
 		if (openMapFrame != null) {
			openMapFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));	 
		}
 
        return dat;
    }

	/**
	 * Gets all application data within a certain area from the database.
	 *
	 * @param topLeftLat  Top left latitude of the area
	 * @param topLeftLon  Top left longitude of the area
	 * @param lowRightLat  Lower right latitude of the area
	 * @param lowRightLon  Lower right longitude of the area	 
	 * @return             All application data within the defined area from the database
	 */		
    public static EAVDAMData getData(double topLeftLat, double topLeftLon, double lowRightLat, double lowRightLon){
    	EAVDAMData dat = null;
    	
    	try {
    		if(derby == null) derby = new DerbyDBInterface();
    		
            EAVDAMUser user = derby.retrieveDefaultUser();  
            if(user != null){
            	System.out.println("Retrieved default user: "+user.getOrganizationName());
            
            }
            
        	dat = derby.retrieveAllEAVDAMData(user, topLeftLat, topLeftLon, lowRightLat, lowRightLon);
        	
                  
                

    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	
    	
    	return dat;
    }
    
	/**
	 * Saves application data.
	 *
	 * @param data  Application data
	 */
    public static void saveData(EAVDAMData data) {
		if (derby == null) {
			derby = new DerbyDBInterface();
		}
		derby.insertEAVDAMData(data);
		IssuesMenuItem.healthCheckMayBeOutdated = true;
    }
    
	/**
	 * Saves user data.
	 *
	 * @param user         User data
	 * @param defaultUser  Whether the user is the user of this application or other user
	 */
    public static void saveUserData(EAVDAMUser user, boolean defaultUser){
		if (derby == null) {
			derby = new DerbyDBInterface();
		}
    	try {
    		int id = derby.insertEAVDAMUser(user, defaultUser);
			IssuesMenuItem.healthCheckMayBeOutdated = true;
    	} catch(Exception e) {
    		e.printStackTrace();
    	}	
    }
    
    /**
     * Retrieves the options from the database.
     * 
     * @return  Options from the database
     */
    public static Options getOptions() {

		Options op = null;

    	try {
    		op = new Options();
			if (derby == null) {
				derby = new DerbyDBInterface();
			}
    		op = derby.getOptions();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		
		return op;
	}
    		
	/**
	 * Saves options to database.
	 *
	 * @param options  Options to save
	 */
    public static void saveOptions(Options options) {
		if (derby == null) {
			derby = new DerbyDBInterface();
		}
    	derby.insertOptions(options);
    }
    
	/**
	 * Deletes a simulation from the database.
	 *
	 * @param simulationName  Name of the simulation to delete
	 */
    public static void deleteSimulation(String simulationName){
    	try {
    		if (derby == null) {
				derby = new DerbyDBInterface();
			}
    		derby.deleteSimulation(simulationName);
			IssuesMenuItem.healthCheckMayBeOutdated = true;			
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * Deletes a station from the database.
	 *
	 * @param stationID  ID of the station to delete
	 */
    public static void deleteStation(int stationID){
    	try {
    		if (derby == null) {
				derby = new DerbyDBInterface();
			}
    		derby.deleteStation(stationID);
			IssuesMenuItem.healthCheckMayBeOutdated = true;			
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * Acknowledges health check issues in the database.
	 *
	 * @param issues  List of issues to acknowledge
	 */
    public static void acknowledgeIssues(List<AISDatalinkCheckIssue> issues){    	
    	try {
    		if (derby == null) {
				derby = new DerbyDBInterface();
			}
    		derby.acknowledgeIssues(issues);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}


/**
 * Class for loading other users xmls from ftp sites.
 */
class LoadXMLsFromFTPsThread extends Thread {
		
	private OpenMapFrame openMapFrame;
		
	LoadXMLsFromFTPsThread(OpenMapFrame openMapFrame) {
		this.openMapFrame = openMapFrame;
	}
	
	public void run() {
		
		if (openMapFrame == null) {
			return;
		}
		
		boolean updated = false;
		
		EAVDAMData exportData = XMLHandler.exportData();		
		
		Options options = DBHandler.getOptions();
		String ownFileName = XMLHandler.getLatestDataFileName();
		if (ownFileName != null && ownFileName.indexOf("/") != -1) {
			ownFileName = ownFileName.substring(ownFileName.lastIndexOf("/")+1);
		}
		List<FTP> ftps = options.getFTPs();
		String errors = "";
		if (ftps != null && !ftps.isEmpty()) {
			for (FTP ftp : ftps) {
				try {
					FTPClient ftpClient = FTPHandler.connect(ftp);
					if (exportData.getStations() == null || exportData.getStations().length == 0) {
						FTPHandler.deleteDataFromFTP(ftpClient, ownFileName);
					} else {
						FTPHandler.sendDataToFTP(ftpClient, XMLHandler.getLatestDataFileName());
					}
					if (FTPHandler.importDataFromFTP(ftpClient, XMLHandler.importDataFolder, ownFileName)) {
						updated = true;
					}					
					FTPHandler.disconnect(ftpClient);
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
					 errors += "- " + ex.getMessage() + "\n";
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
					errors += "- " + ex.getMessage() + "\n";
				}
			}
		}
		if (!errors.isEmpty()) {
			JOptionPane.showMessageDialog(openMapFrame, "The following ftp sites had problems when exchanging data:\n" + errors, "Error", JOptionPane.ERROR_MESSAGE); 
		}	

		DBHandler.importDataUpdated = updated;			
		IssuesMenuItem.healthCheckMayBeOutdated = updated;
	}

}
        