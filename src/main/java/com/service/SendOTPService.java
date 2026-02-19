package com.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class SendOTPService {

    public static void sendOTP(String email, String genOTP) {

        String to = email;
        String from = "kunalhand30@gmail.com";   // your Gmail
        String host = "smtp.gmail.com";

        Properties properties = new Properties();

        // TLS configuration (recommended)
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        from,
                        "tkclfwiqqzntxuhm"
                );
            }
        });

        session.setDebug(true);

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            message.setSubject("File Enc ka OTP");

            message.setText("Your One time Password for File Enc app is " + genOTP);

            System.out.println("sending...");

            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}