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
package dk.frv.eavdam.data;

import java.util.List;

/**
 * Class for application's options.
 */
public class Options {
    
	public static final int LARGE_ICONS = 1;
    public static final int SMALL_ICONS = 2;	
	
    private int iconsSize = LARGE_ICONS;  // default	
	
    private String emailTo = null;	
    private String emailFrom = null;
    private String emailSubject = null;
    private String emailHost = null;
    private boolean emailAuth = false;    
    private String emailUsername = null;
    private String emailPassword = null;    
    
    private List<FTP> ftps = null;
    
    public String getEmailTo() {
        return emailTo;
    }
    
	/**
	 * Sets the e-mail recipients for whom the station data is sent.
	 *
	 * param emailTo  E-mail recipients. Can be a comma separated list of multiple e-mail addresses.
	 */
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