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
package dk.frv.eavdam.io;

import com.sun.mail.smtp.SMTPTransport;
import dk.frv.eavdam.utils.XMLHandler;
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

/**
 * Class for sending the station data in XML format to defined e-mail addresses.
 */
public class EmailSender {
    
	/**
	 * Sends station data XML file to e-mail.
	 *
	 * @param to        E-mail address to whom the data is to be sent
	 * @param from      E-mail address from whom the e-mail is
	 * @param subject   Subject of the e-mail
	 * @param host      SMTP server address
	 * @param auth      Whether the SMTP server needs authenticating
	 * @param user      Usename for the SMTP server, if it needs authenticating
	 * @param password  Password for the SMTP server, if it needs authenticating
	 */	 
    public static void sendDataToEmail(String to, String from, String subject, String host,
            boolean auth, String user, String password) throws IOException, MessagingException {

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

        String datafile = XMLHandler.getLatestDataFileName();

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