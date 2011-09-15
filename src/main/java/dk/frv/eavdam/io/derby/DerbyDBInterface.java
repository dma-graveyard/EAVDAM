package dk.frv.eavdam.io.derby;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Properties;

import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.AISFixedStationStatus;
import dk.frv.eavdam.data.AISFixedStationType;
import dk.frv.eavdam.data.ActiveStation;
import dk.frv.eavdam.data.Address;
import dk.frv.eavdam.data.Antenna;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.FATDMASlotAllocation;
import dk.frv.eavdam.data.OtherUserStations;
import dk.frv.eavdam.data.Person;
import dk.frv.eavdam.data.Simulation;


	/**
	 */
	public class DerbyDBInterface {
	    /* the default framework is embedded*/
	    private String framework = "embedded";
	    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	    private String protocol = "jdbc:derby:";
	    private String defaultDB = "eavdam";
	    
	    public static final int STATUS_ACTIVE = 1;
	    public static final int STATUS_OLD = 2;
	    public static final int STATUS_SIMULATED = 3;
	    public static final int STATUS_PROPOSED = 4;
	    public static final int STATUS_PLANNED = 5;
	    
	    public static final int ANTENNA_DIRECTIONAL = 1;
	    public static final int ANTENNA_OMNIDIR = 2;
	    
	    public static final int STATION_BASE = 1;
	    public static final int STATION_REPEATER = 2;
	    public static final int STATION_RECEIVER = 3;
	    public static final int STATION_ATON = 4;
	    
	    Connection conn = null;
	    
	    public DerbyDBInterface(String driver, String protocol){
	    	this.driver = driver;
	    	this.protocol = protocol;
	    }
	    
	    public DerbyDBInterface(){
	    	
	    }
	    
	    public static void main(String[] args)
	    {
	       DerbyDBInterface dba =  new DerbyDBInterface();
	       dba.createDatabase(null);
	       
	        System.out.println("SimpleApp finished");
	    }

	    /**
	     * Creates the connection to the database.
	     * 
	     * @param dbName Name of the database. If null, default(eavdam) will be used.
	     * @return
	     * @throws SQLException
	     */
	    public Connection getDBConnection(String dbName) throws SQLException{
	        /* load the desired JDBC driver */
	        loadDriver();
	        
	        if(dbName == null) dbName = defaultDB;
	        
            Properties props = new Properties(); // connection properties
            // providing a user name and password is optional in the embedded
            // and derbyclient frameworks
            props.put("user", "eavdam");
            props.put("password", "DaMSA");
	        
            this.conn = DriverManager.getConnection(protocol + dbName+ ";create=true", props);
            
            //Test if the database exists:
            try{
            	PreparedStatement ps = conn.prepareStatement("SELECT id, name FROM STATIONTYPE WHERE id=1");
            	ResultSet rs = ps.executeQuery();
            	
            	rs.close();
            	ps.close();
            	
            }catch(SQLException e){
            	System.out.println("Database does not exist, creating it...");
            	this.createDatabase(dbName);
            }
            
            
            return this.conn;
	    }
	
	    /**
	     * Inserts the data to the database. Depending on the data 
	     * 
	     * @param data 
	     * @return Boolean value that indicates if the insert was successful
	     */
	    public boolean insertEAVDAMData(ArrayList<EAVDAMData> data){
	    	boolean success = true;
	    	for(EAVDAMData d : data){
	    		if(!this.insertEAVDAMData(d)){
	    			success = false;
	    		}
	    	}
	    	
	    	return success;
	    }
	    
	    public boolean insertEAVDAMData(EAVDAMData data){
	    	if(true) return true;
	        
	        boolean success = true;
	        
	        if(data.getUser() == null){
	        	//throw new Exception("No user data given in EAVDAMData!");
	        	return false;
	        }
	        
	        try{
	        	if(conn == null) conn = getDBConnection(defaultDB);
	    	

	        	int orgID = this.getOrganizationID(data.getUser());
	        	
	        	
	        	if(data.getActiveStations() != null && data.getActiveStations().size() > 0){
	        		for(ActiveStation a : data.getActiveStations()){
	        			if(a.getStations() != null && a.getStations().size() > 0){
		        			for(AISFixedStationData ais :a.getStations()){
		        				this.insertStation(ais, STATUS_ACTIVE, orgID, -1);
		        			}
	        			}
	        			
	        			if(a.getProposals() != null && a.getProposals().size() > 0){
		        			for(EAVDAMUser user : a.getProposals().keySet()){
		        				int proposeeID = this.getOrganizationID(user);
		        				/*
		        				for(AISFixedStationData ais : a.getProposals().get(user)){
		        					this.insertStation(ais, STATUS_PROPOSED, orgID, proposeeID);
		        				}
		        				*/
		        				AISFixedStationData ais = a.getProposals().get(user);
		        				this.insertStation(ais, STATUS_PROPOSED, orgID, proposeeID);
		        			}
	        			}
	        		}
	        	}
	        	
	        	if(data.getOldStations() != null && data.getOldStations().size() > 0){
	        		for(AISFixedStationData ais : data.getOldStations()){
	        			this.insertStation(ais, STATUS_OLD, orgID, -1);
	        		}
	        	}
	        	
	        	if(data.getSimulatedStations() != null && data.getSimulatedStations().size() > 0){
	        		for(Simulation sim : data.getSimulatedStations()){
	        			for(AISFixedStationData ais : sim.getStations()){
	        				this.insertSimulatedStation(ais, STATUS_SIMULATED, orgID, -1);
	        			}
	        		}
	        	}
	        	
	        	if(data.getOtherUsersStations() != null && data.getOtherUsersStations().size() > 0){
	        		for(OtherUserStations other : data.getOtherUsersStations()){
	        			int otherID = this.getOrganizationID(other.getUser());
	        			for(ActiveStation active : other.getStations()){
		        			for(AISFixedStationData ais : active.getStations()){
		        				this.insertStation(ais, STATUS_ACTIVE, otherID, -1);
		        			}
	        			}
	        		}
	        	}
	        	
            	
	        }catch(Exception e){
	        	success = false;
	        }
	        
	        return success;
	    }
	    
	    private void insertSimulatedStation(AISFixedStationData ais,
				int statusSimulated, int orgID, int i) {
			// TODO Auto-generated method stub
			
		}

		/**
	     * Finds the id for the address. Returns -x if the address is new.
	     * 
	     * -x is the negative value for the new id. So if the result is -28, the first available id is 28.
	     * 
	     * 
	     * @param address 
	     * @return
	     * @throws Exception
	     */
	    private int getAddressID(Address address) throws Exception{
	    	PreparedStatement ps = conn.prepareStatement("select id from ADDRESS where addressline1 = ?");
    		ps.setString(1, address.getAddressline1());
    		ResultSet res = ps.executeQuery();
    		int addressID = -1;
    		if(!res.next()){
    			//No address found. Add new one. First, get the highest id...
    			res.close();
        		ps = conn.prepareStatement("select count(id) from ADDRESS");
        		res = ps.executeQuery();
    			if(!res.next()){
    				addressID = 0+1;
    			}else{
    				addressID = res.getInt(1)+1;
    			}
    			
    			this.insertAddress(address, addressID);
    		}else{
    			return res.getInt(1);
    		}
    		
    		ps.close();
    		res.close();
    		return addressID;
	    }
	    
	    private int insertAddress(Address address, int id) throws Exception{
			
    		PreparedStatement ps = conn.prepareStatement("insert into ADDRESS values (?,?,?,?,?)");
    		ps.setInt(1, id);
    		ps.setString(2, address.getAddressline1());
    		ps.setString(3, address.getAddressline2());
    		ps.setString(4, address.getZip());
    		ps.setString(5, address.getCity());
    		ps.setString(6, address.getCountry());
    		
    		ps.executeUpdate();
    		ps.close();
    		
    		return id;
	    }
	    
	    private int getPersonID(Person person) throws Exception{
    		PreparedStatement ps = conn.prepareStatement("select id from PERSON where email = ?");
    		
    		ps.setString(1, person.getEmail());
    		ResultSet res = ps.executeQuery();
    		int contactID = -1;
    		if(!res.next()){ //No such person, add new one...
    			res.close();
        		ps = conn.prepareStatement("select count(id) from PERSON");
        		res = ps.executeQuery();
    			if(!res.next()){
    				contactID = 0+1;
    			}else{
    				contactID = res.getInt(1)+1;
    			}
    			
    			this.insertPerson(person, contactID);
    			
    		}else{
    			contactID = res.getInt(1);
    			return contactID;
    		}
    		
    		ps.close();
    		res.close();
    		
    		return contactID;
	    }
	    
	    private void insertPerson(Person person, int id) throws Exception{
	    	//Find if the addresses exists.
			int personPostalAddress = this.getAddressID(person.getPostalAddress());
			if(personPostalAddress < 0){
				personPostalAddress *= -1;
				this.insertAddress(person.getPostalAddress(), personPostalAddress);
			}
			
			int personVisitingAddress = this.getAddressID(person.getVisitingAddress());
			if(personVisitingAddress < 0){
				personVisitingAddress *= -1;
				this.insertAddress(person.getVisitingAddress(), personVisitingAddress);
			}

						
    		PreparedStatement ps = conn.prepareStatement("insert into PERSON values (?,?,?,?,?)");
    		ps.setInt(1, id);
    		ps.setString(2, person.getName());
    		ps.setString(3, person.getEmail());
    		ps.setString(4, person.getPhone());
    		ps.setString(5, person.getFax());
    		ps.setString(6, person.getDescription());
    		ps.setInt(7, personVisitingAddress);
    		ps.setInt(8, personPostalAddress);
    		
    		
    		ps.executeUpdate();
    		ps.close();

	    }
	    
	    private int getOrganizationID(EAVDAMUser user) throws Exception{
	        
        	PreparedStatement ps = conn.prepareStatement("select id from organization where ORGANIZATION name = ?");
        	ps.setString(1, user.getOrganizationName());
        	ResultSet res = ps.executeQuery();
        	
        	int orgID = -1;
        	//Find out if the organization exists in the database.
        	if(!res.next()){
        		System.out.println("Organization "+user.getOrganizationName()+" does not exist! Inserting new organization...");
        		
        		//Add the organization.
        		ps = conn.prepareStatement("select count(id) from ORGANIZATION");
        		if(!res.next()){
        			orgID = 1;
        		}else{
        			orgID = res.getInt(1)+1;
        		}
        		
        		this.insertOrganization(user, orgID);
        	}else{
        		orgID = res.getInt(1);
        	}
        	
        	ps.close();
        	res.close();
        	
        	return orgID;
	    }
	    
	    private void insertOrganization(EAVDAMUser user, int id) throws Exception{
    		//Start with the POSTAL address
    		int postalID = this.getAddressID(user.getPostalAddress()); //Insert a new entry if the address is not found.
    		int visitingID = this.getAddressID(user.getVisitingAddress());
    		
    		//Check the CONTACT person.
    		int contactPersonID = this.getPersonID(user.getContact());
    	
    		//Check the TECHNICAL person.
    		int technicalPersonID = this.getPersonID(user.getTechnicalContact());
    		
    		PreparedStatement ps = conn.prepareStatement("insert into ORGANIZATION values (?,?,?,?,?,?,?,?,?,?,?)");
    		ps.setInt(1,id);
    		ps.setString(2, user.getOrganizationName());
    		ps.setString(3, user.getCountryID());
    		ps.setString(4, user.getPhone());
    		ps.setString(5, user.getFax());
    		ps.setString(6, user.getWww().toString());
    		ps.setString(7, user.getDescription());
    		ps.setInt(8, contactPersonID);
    		ps.setInt(9, technicalPersonID);
    		ps.setInt(10, visitingID);
    		ps.setInt(11, postalID);
    		
    		ps.executeUpdate();
    		ps.close();
    		
	    }
	    
	    public int insertStation(AISFixedStationData as, int status, int organizationID, int proposee) throws Exception{
	    	
	    	
	    	int stationType;
	    	//Get the station type.
	    	switch(as.getStationType()){
	    		case BASESTATION: stationType = STATION_BASE; break;
	    		case ATON: stationType = STATION_ATON; break;
	    		case RECEIVER: stationType = STATION_RECEIVER; break;
	    		case REPEATER: stationType = STATION_REPEATER; break;
	    	
	    		default: stationType = STATION_BASE;
	    	}
	    	
	    	PreparedStatement psc = conn.prepareStatement("select count(id) from FIXEDSTATION");
	    	ResultSet rs = psc.executeQuery();
	    	int stationID = 0;
	    	if(!rs.next()){
	    		stationID = 1;
	    	}else{
	    		stationID = rs.getInt(1)+1;
	    	}
	    	
	    	rs.close();
	    	psc.close();
	    	
	    	//Insert station
	    	PreparedStatement ps = conn.prepareStatement("insert into FIXEDSTATION values (?,?,?,?,?,?,?,?,?,?,?)");
	    	ps.setInt(1, stationID);
	    	ps.setString(2, as.getStationName());
	    	ps.setInt(3,organizationID);
	    	ps.setString(4, as.getMmsi());
	    	ps.setDouble(5, as.getLat());
	    	ps.setDouble(6, as.getLon());
	    	ps.setDouble(7, as.getTransmissionPower());
	    	ps.setString(8, as.getDescription());
	    	ps.setInt(9, stationType);
	    	ps.setString(10, as.getAnythingString());
	    	ps.setInt(11, proposee);
	    	
	    	ps.executeUpdate();
	    	ps.close();
	    	
	    	//Insert Antenna
	    	int antennaType;
	    	//Get the antenna type.
	    	switch(as.getAntenna().getAntennaType()){
	    		case DIRECTIONAL: antennaType = ANTENNA_DIRECTIONAL; break;
	    		case OMNIDIRECTIONAL: antennaType = ANTENNA_OMNIDIR; break;
	    	
	    		default: antennaType = ANTENNA_DIRECTIONAL;
	    	}
	    	
	    	this.insertAntenna(as.getAntenna(), stationID, antennaType);
	    	
	    	//Insert Status
	    	this.insertStatus(as.getStatus(), status, stationID);
	    	
	    	
	    	//Insert FATDMA Allocations
	    	this.insertFATDMAAllocations(as.getFatdmaAllocation());
	    	
	    	
	    	
	    	return stationID;
	    }
	    
	    public void insertAntenna(Antenna antenna, int stationID, int antennaID) throws Exception{
	    	PreparedStatement psc = conn.prepareStatement("insert into ANTENNA values (?,?,?,?,?,?,?)");
	    	
	    	psc.setInt(1, stationID);
	    	psc.setDouble(2, antenna.getAntennaHeight());
	    	psc.setDouble(3, antenna.getTerrainHeight());
	    	psc.setInt(4, antenna.getHeading());
	    	psc.setDouble(5, antenna.getFieldOfViewAngle());
	    	psc.setDouble(6, antenna.getGain());
	    	psc.setInt(7, antennaID);
	    	psc.executeUpdate();
	    	
	    	psc.close();
	    	
	    }
	    
	    public void insertStatus(AISFixedStationStatus status, int statusID, int stationID) throws Exception{
	    	PreparedStatement psc = conn.prepareStatement("insert into STATUS values (?,?,?,?)");
	    	
	    	psc.setInt(1, stationID);
	    	psc.setInt(2, statusID);
	    	psc.setDate(3, status.getStartDate());
	    	psc.setDate(4, status.getEndDate());
	    	psc.executeUpdate();
	    	
	    	psc.close();
	    }
	    
	    public void insertFATDMAAllocations(FATDMASlotAllocation fatdma){
	    	
	    }
	    
	    /**
	     * Retrieves all the data from the database.
	     * 
	     * This includes ...
	     * 
	     * @return
	     */
	    public ArrayList<EAVDAMData> retrieveAllEAVDAMData() throws Exception{
	    	ArrayList<EAVDAMData> data = new ArrayList<EAVDAMData>();
	    	
	    	if(this.conn == null) this.conn = this.getDBConnection(null);
	    	
	    	//Get user information
	    	////Includes Address and Person 
	    	PreparedStatement ps = conn.prepareStatement("");
	    	
	    	
	    	//Get station information
	    	
	    	//Get antenna information
	    	
	    	//Get status information
	    	
	    	//
	    	
	    	
	    	return null;
	    }
	    
	    
	    /**
	     * Retrieves the data only for the given id.
	     * 
	     * @param id
	     * @return
	     */
	    public EAVDAMData retrieveEAVDAMData(String id) throws Exception{
	    	
	    	
	    	return null;
	    }
	    
	 
//	    public int getStatusID(String status) throws Exception{
//	    	PreparedStatement ps = conn.prepareStatement("select id from STATUSTYPE where name = '"+status+"'");
//	    	ResultSet res = ps.executeQuery();
//	    	if(!res.next()){
//	    
//			}
	    
//	    }
	    
	    /**
	     * Retrieves the data that will be sent to the FTP-server in an xml-file. 
	     * 
	     * This includes the active stations and ...
	     * 
	     * @return
	     */
	    public ArrayList<EAVDAMData> retrieveSendData(){
	    	return null;
	    }
	    
	    /**
	     * Loads the appropriate JDBC driver for this environment/framework. For
	     * example, if we are in an embedded environment, we load Derby's
	     * embedded Driver, <code>org.apache.derby.jdbc.EmbeddedDriver</code>.
	     */
	    private void loadDriver() {
	        /*
	         *  The JDBC driver is loaded by loading its class.
	         *  If you are using JDBC 4.0 (Java SE 6) or newer, JDBC drivers may
	         *  be automatically loaded, making this code optional.
	         *
	         *  In an embedded environment, this will also start up the Derby
	         *  engine (though not any databases), since it is not already
	         *  running. In a client environment, the Derby engine is being run
	         *  by the network server framework.
	         *
	         *  In an embedded environment, any static Derby system properties
	         *  must be set before loading the driver to take effect.
	         */
	        try {
	            Class.forName(driver).newInstance();
	            System.out.println("Loaded the appropriate driver");
	        } catch (ClassNotFoundException cnfe) {
	            System.err.println("\nUnable to load the JDBC driver " + driver);
	            System.err.println("Please check your CLASSPATH.");
	            cnfe.printStackTrace(System.err);
	        } catch (InstantiationException ie) {
	            System.err.println(
	                        "\nUnable to instantiate the JDBC driver " + driver);
	            ie.printStackTrace(System.err);
	        } catch (IllegalAccessException iae) {
	            System.err.println(
	                        "\nNot allowed to access the JDBC driver " + driver);
	            iae.printStackTrace(System.err);
	        }
	    }

	    /**
	     * Reports a data verification failure to System.err with the given message.
	     *
	     * @param message A message describing what failed.
	     */
	    private void reportFailure(String message) {
	        System.err.println("\nData verification failed:");
	        System.err.println('\t' + message);
	    }

	    /**
	     * Prints details of an SQLException chain to <code>System.err</code>.
	     * Details included are SQL State, Error code, Exception message.
	     *
	     * @param e the SQLException from which to print details.
	     */
	    public static void printSQLException(SQLException e)
	    {
	        // Unwraps the entire exception chain to unveil the real cause of the
	        // Exception.
	        while (e != null)
	        {
	            System.err.println("\n----- SQLException -----");
	            System.err.println("  SQL State:  " + e.getSQLState());
	            System.err.println("  Error Code: " + e.getErrorCode());
	            System.err.println("  Message:    " + e.getMessage());
	            // for stack traces, refer to derby.log or uncomment this:
	            //e.printStackTrace(System.err);
	            e = e.getNextException();
	        }
	    }

	    /**
	     * Parses the arguments given and sets the values of this class' instance
	     * variables accordingly - that is which framework to use, the name of the
	     * JDBC driver class, and which connection protocol protocol to use. The
	     * protocol should be used as part of the JDBC URL when connecting to Derby.
	     * <p>
	     * If the argument is "embedded" or invalid, this method will not change
	     * anything, meaning that the default values will be used.</p>
	     * <p>
	     * @param args JDBC connection framework, either "embedded", "derbyclient".
	     * Only the first argument will be considered, the rest will be ignored.
	     */
	    private void parseArguments(String[] args)
	    {
	        if (args.length > 0) {
	            if (args[0].equalsIgnoreCase("derbyclient"))
	            {
	                framework = "derbyclient";
	                driver = "org.apache.derby.jdbc.ClientDriver";
	                protocol = "jdbc:derby://localhost:1527/";
	            }
	        }
	    }
	    
	    public void createDatabase(String dbName){
	    	boolean log = true;
	    	
	    	try{
	    		if(this.conn == null) this.getDBConnection(dbName);
	    	
	    		Statement s = conn.createStatement();

	    		
	    		//First, drop the tables (if they exist)
				
				
				try {
					s.execute("DROP TABLE ANTENNA");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
	    		
				try {
					s.execute("DROP TABLE FATDMA");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				
	    		try {
					s.execute("DROP TABLE COVERAGEPOINTS");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
								
				try {
					s.execute("DROP TABLE STATUS");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
	    		
				try {
					s.execute("DROP TABLE FIXEDSTATION");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
	    		try{
	    			s.execute("DROP TABLE ORGANIZATION");
	    		}catch(Exception e){
	    			if(log)
						e.printStackTrace();
	    		}
								
				try {
					s.execute("DROP TABLE PERSON");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				
	    		try {
					s.execute("DROP TABLE ADDRESS");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}


				
	    		try {
					s.execute("DROP TABLE ANTENNATYPE");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				try {
					s.execute("DROP TABLE STATIONTYPE");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				try {
					s.execute("DROP TABLE STATUSTYPE");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				//Then create the tables.
				try {
					s.execute("CREATE TABLE ADDRESS" + "(ID INT PRIMARY KEY,"
							+ "ADDRESSLINE1 VARCHAR(200),"
							+ "ADDRESSLINE2 VARCHAR(200),"
							+ "ZIPCODE VARCHAR(10)," + "CITY VARCHAR(50),"
							+ "COUNTRY VARCHAR(50))");
				} catch (Exception e) {
					// TODO: handle exception
					if(log)
						e.printStackTrace();
				}
				
			
				try {
					s.execute("CREATE TABLE PERSON"
							+ "(ID INT PRIMARY KEY,"
							+ "NAME VARCHAR(150) NOT NULL,"
							+ "EMAIL VARCHAR(150) NOT NULL,"
							+ "PHONE VARCHAR(15),"
							+ "FAX VARCHAR(15),"
							+ "DESCRIPTION VARCHAR(1000),"
							+ "VISITINGADDRESS INT,"
							+ "POSTALADDRESS INT,"
							+ "CONSTRAINT fk_va_p FOREIGN KEY (VISITINGADDRESS) references ADDRESS(ID),"
							+ "CONSTRAINT fk_pa_p FOREIGN KEY (POSTALADDRESS) references ADDRESS(ID))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
	    		
	    		try {
					s.execute("CREATE TABLE ORGANIZATION"
							+ "(ID INT PRIMARY KEY,"
							+ "ORGANIZATIONNAME VARCHAR(100) NOT NULL,"
							+ "COUNTRYID VARCHAR(3) NOT NULL,"
							+ "PHONE VARCHAR(15),"
							+ "FAX VARCHAR(15),"
							+ "WWW VARCHAR(50),"
							+ "DESCRIPTION VARCHAR(1000),"
							+ "CONTACTPERSON INT,"
							+ "TECHNICALPERSON INT,"
							+ "VISITINGADDRESS INT,"
							+ "POSTALADDRESS INT,"
							+ "CONSTRAINT fk_cp_o FOREIGN KEY (CONTACTPERSON) references PERSON(ID),"
							+ "CONSTRAINT fk_tp_o FOREIGN KEY (TECHNICALPERSON) references PERSON(ID),"
							+ "CONSTRAINT fk_va_o FOREIGN KEY (VISITINGADDRESS) references ADDRESS(ID),"
							+ "CONSTRAINT fk_pa_o FOREIGN KEY (POSTALADDRESS) references ADDRESS(ID))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				
				try {
					s.execute("CREATE TABLE ANTENNATYPE"
							+ "(ID INT PRIMARY KEY," + "NAME VARCHAR(50))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				

				
				
				try {
					s.execute("CREATE TABLE STATIONTYPE"
							+ "(ID INT PRIMARY KEY,"
							+ "NAME VARCHAR(50) NOT NULL)");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				

				
				try {
					s.execute("CREATE TABLE STATUSTYPE" 
							+ "(ID INT PRIMARY KEY,"
							+ "NAME VARCHAR(50))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}

				
				try {
					s.execute("CREATE TABLE FIXEDSTATION"
							+ "(ID INT PRIMARY KEY,"
							+ "NAME VARCHAR(120),"
							+ "OWNER INT,"
							+ "MMSI VARCHAR(15),"
							+ "LAT DECIMAL,"
							+ "LON DECIMAL,"
							+ "TRANSIMISIONPOWER DECIMAL,"
							+ "DESCRIPTION VARCHAR(1000),"
							+ "STATIONTYPE INT,"
							+ "ANYVALUE VARCHAR(2000),"
							+ "PROPOSEE INT,"
							+ "CONSTRAINT fk_prop_fs FOREIGN KEY (PROPOSEE) references ORGANIZATION(ID),"
							+ "CONSTRAINT fk_o_fs FOREIGN KEY (OWNER) references ORGANIZATION(ID),"
							+ "CONSTRAINT fk_st_fs FOREIGN KEY (STATIONTYPE) references STATIONTYPE(ID))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
					
				}
				
				try {
					s.execute("CREATE TABLE STATUS"
							+ "(STATION INT,"
							+ "STATUSTYPE INT,"
							+ "STARTDATE DATE,"
							+ "ENDDATE DATE,"
							+ "CONSTRAINT fk_fs_s FOREIGN KEY (STATION) references FIXEDSTATION(ID),"
							+ "CONSTRAINT fk_st_s FOREIGN KEY (STATUSTYPE) references STATUSTYPE(ID))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				try {
					s.execute("CREATE TABLE ANTENNA"
							+ "(STATION INT,"
							+ "ANTENNAHEIGHT DECIMAL,"
							+ "TERRAINHEIGHT DECIMAL,"
							+ "ANTENNAHEADING INT,"
							+ "FIELDOFVIEWANGLE DECIMAL,"
							+ "GAIN DECIMAL,"
							+ "ANTENNATYPE INT,"
							+ "CONSTRAINT fk_s_a FOREIGN KEY (STATION) references FIXEDSTATION(ID))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				try {
					s.execute("CREATE TABLE COVERAGEPOINTS"
							+ "(ID INT PRIMARY KEY,"
							+ "STATION INT,"
							+ "LAT DECIMAL,"
							+ "LON DECIMAL,"
							+ "CONSTRAINT fk_s_cp FOREIGN KEY (STATION) references FIXEDSTATION(ID))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
		
				try {
					s.execute("CREATE TABLE FATDMA"
							+ "(ID INT PRIMARY KEY,"
							+ "STATION INT,"
							+ "ALLOCATIONINDEX INT,"
							+ "ALLOCATIONVALUE INT,"
							+ "CONSTRAINT fk_s_fatdma FOREIGN KEY (STATION) references FIXEDSTATION(ID))");
				} catch (Exception e) {
					if(log)
						e.printStackTrace();
				}
				
				
				//Insert the predefined data
	    		s.execute("INSERT INTO ANTENNATYPE VALUES("+ANTENNA_DIRECTIONAL+", 'Directional Antenna')");
	    		s.execute("INSERT INTO ANTENNATYPE VALUES("+ANTENNA_OMNIDIR+", 'Omnidirectional Antenna')");
	    		
	    		s.execute("INSERT INTO STATIONTYPE VALUES("+STATION_BASE+", 'Base station')");
	    		s.execute("INSERT INTO STATIONTYPE VALUES("+STATION_REPEATER+", 'Repeater')");
	    		s.execute("INSERT INTO STATIONTYPE VALUES("+STATION_RECEIVER+", 'Receiver')");
	    		s.execute("INSERT INTO STATIONTYPE VALUES("+STATION_ATON+", 'ATON')");
	    		
	    		s.execute("INSERT INTO STATUSTYPE VALUES("+STATUS_ACTIVE+", 'Active')");
	    		s.execute("INSERT INTO STATUSTYPE VALUES("+STATUS_OLD+", 'Old')");
	    		s.execute("INSERT INTO STATUSTYPE VALUES("+STATUS_SIMULATED+", 'Simulated')");
	    		s.execute("INSERT INTO STATUSTYPE VALUES("+STATUS_PROPOSED+", 'Proposed')");
	    		s.execute("INSERT INTO STATUSTYPE VALUES("+STATUS_PLANNED+", 'Planned')");
	    		conn.commit();
	    		
	    	 	 //test
	    		 ResultSet rs = s.executeQuery("SELECT id, name FROM STATIONTYPE WHERE id=1");
	    		 boolean failure = false;
	             if (!rs.next())
	             {
	                 failure = true;
	                 reportFailure("No rows in ResultSet");
	             }

	             int id;
	             if ((id = rs.getInt(1)) != 1){
	                 failure = true;
	                 System.out.println("FAILURE IN DATABASE CREATION. ID TEST FAILED! Should be "+1+", is "+id);
	             }

	             String type;
	             if(!(type = rs.getString(2)).equals("Base station")){
	            	 failure = true;
	                 System.out.println("FAILURE IN DATABASE CREATION. ID TEST FAILED! Should be 'Base station', is "+type);
	             }
	    		
	    		if(!failure){
	    			System.out.println("Database created successfully! Test result: id="+id+", type="+type+" (should be: id=1, type=Base station)");
	    		}
				
	    		rs.close();
	    		conn.close();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	    
}
