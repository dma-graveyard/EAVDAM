package dk.frv.eavdam.io;

import dk.frv.eavdam.data.FTP;
import dk.frv.eavdam.utils.DataFileHandler;
import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
 
public class FTPSender {
    
    public static void sendDataToFTP(FTP ftp) throws IOException {

        if (ftp == null || ftp.getServer() == null || ftp.getUsername() == null || ftp.getPassword() == null) {
            throw new IOException("FTP object or its contents is null");
        }
        
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftp.getServer());
        ftpClient.login(ftp.getUsername(), ftp.getPassword());
        if (ftp.getDirectory() != null) {
            ftpClient.changeWorkingDirectory(ftp.getDirectory());
        }

        String datafile = DataFileHandler.getLatestDataFileName();
        
        FileInputStream fis = new FileInputStream("data/" + datafile);
        ftpClient.storeFile(datafile, fis);
        ftpClient.logout();

        try {
            if (fis != null) {
                fis.close();
            }    
            ftpClient.disconnect();
        } catch (IOException e) {}    
    }
    
}