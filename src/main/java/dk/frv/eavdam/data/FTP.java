package dk.frv.eavdam.data;

public class FTP {
        
    private String server = null;
    private String directory = null;
    private String username = null;
    private String password = null;
   
    public FTP() {}
    
    public FTP(String server, String directory, String username, String password) {
        this.server = server;
        this.directory = directory;
        this.username = username;
        this.password = password;
    }
   
    public String getServer() {
        return server;
    }
    
    public void setServer(String server) {
        this.server = server;
    }
    
    public String getDirectory() {
        return directory;
    }
    
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

}
