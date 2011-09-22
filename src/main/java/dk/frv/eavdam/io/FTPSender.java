package dk.frv.eavdam.io;

import dk.frv.eavdam.data.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 
public class FTPSender {
    
    public static void sendDataToFTP(FTP ftp, String filename) throws IOException {
    	System.out.println("Sending "+filename+" to FTP server "+ftp.getServer());
        if (ftp == null || ftp.getServer() == null || ftp.getUsername() == null || ftp.getPassword() == null) {
            throw new IOException("FTP object or its contents is null");
        }
        
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftp.getServer());
        ftpClient.login(ftp.getUsername(), ftp.getPassword());
        if (ftp.getDirectory() != null) {
            ftpClient.changeWorkingDirectory(ftp.getDirectory());
        }

        // deletes old files that our organisation has sent
        int i = 0;
        if (filename.indexOf("/") != -1) {
            i = filename.lastIndexOf("/")+1;
        }
        String prefix = filename; //.substring(i, filename.length()-18);  // parses the directory and the date + extension from the filename
        FTPFile[] files = ftpClient.listFiles();
        for (FTPFile file : files) {
            if (file.getName().startsWith(prefix)) {
                ftpClient.deleteFile(file.getName());
            }
        }

        FileInputStream fis = new FileInputStream(filename);
        if (filename.indexOf("/") != -1) {
            ftpClient.storeFile(filename.substring(filename.lastIndexOf("/")+1), fis);
        } else {
            ftpClient.storeFile(filename, fis);            
        }
        ftpClient.logout();

        try {
            if (fis != null) {
                fis.close();
            }    
            ftpClient.disconnect();
        } catch (IOException e) {}    
    }
 
}