package dk.frv.eavdam.io;

import com.sun.mail.smtp.SMTPTransport;
import dk.frv.eavdam.utils.DataFileHandler;
import java.io.IOException;
import java.io.File;
import java.net.InetAddress;
import java.util.Properties;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
    
    public static void sendDataToEmail(String to, String from, String subject, String host,
            boolean auth, String user, String password, String filename) throws IOException, MessagingException {

	    Properties props = System.getProperties();
	    if (host != null) {
		    props.put("mail.smtp.host", host);
	    }
	    if (auth) {
		    props.put("mail.smtp.auth", "true");
		}

        Session session = Session.getInstance(props, null);

        Message msg = new MimeMessage(session);
	    if (from != null) {
		    msg.setFrom(new InternetAddress(from));
		} else {
		    msg.setFrom();
		}

	    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

	    msg.setSubject(subject);

        String datafile = DataFileHandler.getLatestDataFileName();

	    if (datafile != null) {
		    MimeBodyPart mbp1 = new MimeBodyPart();
		    mbp1.setText("This message contains the latest data file for the EfficienSea AIS VHF Datalink Manager.");
		    MimeBodyPart mbp2 = new MimeBodyPart();
		    mbp2.attachFile(datafile);
		    MimeMultipart mp = new MimeMultipart();
		    mp.addBodyPart(mbp1);
		    mp.addBodyPart(mbp2);
		    msg.setContent(mp);
	    } else {
		    return;
	    }

	    msg.setHeader("X-Mailer", "smtpsend");
	    msg.setSentDate(new Date());

	    SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
	    try {
		    if (auth) {
		        t.connect(host, user, password);
		    } else {
		        t.connect();
		    }
		    t.sendMessage(msg, msg.getAllRecipients());
	    } finally {
		    t.close();
	    }
	    
    }

}