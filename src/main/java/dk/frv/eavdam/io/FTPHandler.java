package dk.frv.eavdam.io;

import dk.frv.eavdam.data.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
public class FTPHandler {
    
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
 
	public static void importDataFromFTP(FTP ftp, String importDirectory, String ownFileName) throws IOException {
	
        if (ftp == null || ftp.getServer() == null || ftp.getUsername() == null || ftp.getPassword() == null) {
            throw new IOException("FTP object or its contents is null");
        }
        
        if (importDirectory == null) {
            throw new IOException("importDirectory is null");
        }		

        if (ownFileName == null) {
            throw new IOException("ownFileName is null");
        }		
		
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftp.getServer());
        ftpClient.login(ftp.getUsername(), ftp.getPassword());
        if (ftp.getDirectory() != null) {
            ftpClient.changeWorkingDirectory(ftp.getDirectory());
        }
		
		FTPFile[] files = ftpClient.listFiles();
        for (FTPFile file : files) {		
			if (!file.isDirectory() && !file.getName().equals(ownFileName) && (file.getName().substring(file.getName().length()-3).equalsIgnoreCase("xml"))) {
				File importedFile = new File(importDirectory + File.separator + file.getName());
				if (importedFile.exists()) {
					importedFile.delete();
				}
				FileOutputStream fos = new FileOutputStream(importedFile);
				ftpClient.retrieveFile(file.getName(), fos);
				fos.close();
			}
		}

		ftpClient.logout();
		
        try {
            ftpClient.disconnect();
        } catch (IOException e) {}
    
    } 
 
}