package dk.frv.eavdam.data;

import java.util.List;

public class Options {
    
    public static final int LARGE_ICONS = 1;
    public static final int SMALL_ICONS = 2;
        
    private String emailTo = null;  // can be a comma separated list of multiple e-mail addresses
    private String emailFrom = null;
    private String emailSubject = null;
    private String emailHost = null;
    private boolean emailAuth = false;    
    private String emailUsername = null;
    private String emailPassword = null;    
    
    private List<FTP> ftps = null;
    
    private int iconsSize = LARGE_ICONS;  // default
    
    public String getEmailTo() {
        return emailTo;
    }
    
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
    
    public String getEmailFrom() {
        return emailFrom;
    }
    
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }
    
    public String getEmailSubject() {
        return emailSubject;
    }
    
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }
    
    public String getEmailHost() {
        return emailHost;
    }
    
    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }
    
    public boolean isEmailAuth() {
        return emailAuth;
    }
    
    public void setEmailAuth(boolean emailAuth) {
        this.emailAuth = emailAuth;
    }
    
    public String getEmailUsername() {
        return emailUsername;
    }
    
    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }
    
    public String getEmailPassword() {
        return emailPassword;
    }
    
    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }                    
    
    public List<FTP> getFTPs() {
        return ftps;
    }
    
    public void setFTPs(List<FTP> ftps) {
        this.ftps = ftps;
    }    
    
    public int getIconsSize() {
        return iconsSize;
    }
    
    public void setIconsSize(int iconsSize) {
        if (iconsSize == LARGE_ICONS || iconsSize == SMALL_ICONS) {
            this.iconsSize = iconsSize;
        }
    }

}
