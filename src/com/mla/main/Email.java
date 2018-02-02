/*
 * Copyright (C) 2017 Media Lab Asia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mla.main;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import static com.mla.main.InstallationView.Constants.CONFIG;

/**
 * Description: Email Sending utility class
 * Date Added: 15 Feb 2017
 * @author Aatif Ahmad Khan
 */
public class Email {
    /**
     * Description:
     *  Sends email to desired recipient email address from a default mail.
     *  Currently, it is sending from a GMAIL Account using SMTP (HOST: "smtp.gmail.com").
     *  In future, sender may be set to some designated Media Lab Asia Email Address with its own HOST.
     * @param recipient
     * @param subject
     * @param body
     * @return status (sent or failed)
     */
    public boolean send(String recipient, String subject, String body){
        boolean sent=false;
        try{
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", CONFIG.MAILSTARTTLS);
            props.put("mail.smtp.auth", CONFIG.MAILAUTH);
            props.put("mail.smtp.host", CONFIG.MAILSERVER);
            props.put("mail.smtp.port", CONFIG.MAILPORT);
            
            // starts a session by authenticating username & password
            Session session=Session.getDefaultInstance(props, new javax.mail.Authenticator(){
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(CONFIG.MAILUSER, CONFIG.MAILPASSWORD);
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(CONFIG.MAILUSER));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient));
            message.setSubject(subject);    // subject field of email
            message.setText(body);          // body field of email

            Transport.send(message);
            sent=true; 
        } catch (MessagingException mex) {
            new Logging("SEVERE",Email.class.getName(),"send(): error sending email",mex);
        }
        return sent;
    }
}
