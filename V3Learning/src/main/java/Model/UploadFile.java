package Model;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


public class UploadFile {
	 public static String upload(InputStream inputStream,String secondRemoteFile) {
	        String server = "v3l.host22.com";
	        int port = 21;
	        String user = "a1423157";
	        String pass = "echipab6";
	 
	        FTPClient ftpClient = new FTPClient();
	        try {
	 
	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalPassiveMode();
	 
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	            /*
	            // APPROACH #1: uploads first file using an InputStream
	            File firstLocalFile = new File("D:/Test/Projects.zip");
	 
	            String firstRemoteFile = "Projects.zip";
	            InputStream inputStream = new FileInputStream(firstLocalFile);
	            
	            System.out.println("Start uploading first file");
	            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
	            inputStream.close();
	            if (done) {
	                System.out.println("The first file is uploaded successfully.");
	            }
	             */
	            // APPROACH #2: uploads second file using an OutputStream
	            secondRemoteFile = "public_html/test/"+secondRemoteFile;
	            //InputStream inputStream = new FileInputStream(secondLocalFile);
	 
	            System.out.println("Start uploading second file" + secondRemoteFile);
	            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
	            byte[] bytesIn = new byte[4096];
	            int read = 0;
	 
	            while ((read = inputStream.read(bytesIn)) != -1) {
	                outputStream.write(bytesIn, 0, read);
	            }
	            inputStream.close();
	            outputStream.close();
	 
	            boolean completed = ftpClient.completePendingCommand();
	            if (completed) {
	                System.out.println("The second file is uploaded successfully.");
	            }
	 
	        } catch (IOException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return secondRemoteFile;
	    }
}
