package com.generic.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotifServiceImpl implements NotifService {

    private static final Logger log = Logger.getLogger(String.valueOf(NotifServiceImpl.class));

    @Value("${mail.smtp.host}")
    String mailSmtpHost;
    @Value("${mail.smtp.port}")
    String mailSmtpPort;
    @Value("${mail.smtp.username}")
    String mailSmtpUsername;
    @Value("${mail.smtp.email}")
    String mailSmtpEmail;
    @Value("${mail.smtp.password}")
    String mailSmtpPassword;

//    @Autowired
//    private HttpServletRequest request;

    @Override
    public String sendEmail(List<String> emailTo, List<String> ccList, String subject, String content) throws
            MessagingException,
            IOException {
        //FileInputStream propStream = new FileInputStream(propertyDir);
        //PROPS.load(propStream);

//        URL url = new URL(request.getRequestURL().toString());
//        String addr = url.getHost();

        log.info("EMAIL UTIL - START");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", mailSmtpHost);
        properties.setProperty("mail.smtp.port", mailSmtpPort);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.port", mailSmtpPort);

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSmtpUsername, mailSmtpPassword);
            }
        };

        log.info("sending start part");
        Session session = Session.getInstance(properties, auth);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailSmtpEmail));
            for (String emailAdd : emailTo) {
                message.addRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(emailAdd, false));
            }
            if (!ccList.isEmpty()) {
                for (String emailAdd : ccList) {
                    message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(emailAdd, false));
                }
            }
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8"); //change this to html content

            Transport transport = session.getTransport();
            try {
                log.info("Sending emails");
                transport.connect(mailSmtpHost, Integer.parseInt(mailSmtpPort), mailSmtpUsername, mailSmtpPassword);
                transport.sendMessage(message, message.getAllRecipients());
                log.info("Emails sent");
            } catch (Exception e) {
                log.info("EMAIL UTIL - START");
                log.info("Error Sending: " + e);
                return "Failed in sending notification";
            }
            transport.close();
            log.info("EMAIL UTIL - END");
            return "Sending completed";
        } catch (MessagingException e) {
            throw e;
        }

    }

    @Override
    public String sendEmail(List<String> emailTo, String subject, String content) throws MessagingException,
            IOException {
        //FileInputStream propStream = new FileInputStream(propertyDir);
        //PROPS.load(propStream);

//        URL url = new URL(request.getRequestURL().toString());
//        String addr = url.getHost();

        log.info("EMAIL UTIL - START");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", mailSmtpHost);
        properties.setProperty("mail.smtp.port", mailSmtpPort);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.port", mailSmtpPort);

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSmtpUsername, mailSmtpPassword);
            }
        };

        log.info("sending start part");
        Session session = Session.getInstance(properties, auth);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailSmtpEmail));
            for (String emailAdd : emailTo) {
                message.addRecipients(
                        Message.RecipientType.TO, InternetAddress.parse(emailAdd, false));
            }

            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8"); //change this to html content

            Transport transport = session.getTransport();
            try {
                log.info("Sending emails");
                transport.connect(mailSmtpHost, Integer.parseInt(mailSmtpPort), mailSmtpUsername, mailSmtpPassword);
                transport.sendMessage(message, message.getAllRecipients());
                log.info("Emails sent");
            } catch (Exception e) {
                log.info("EMAIL UTIL - START");
                log.info("Error Sending: " + e);
                return "Failed in sending notification";
            }
            transport.close();
            log.info("EMAIL UTIL - END");
            return "Sending completed";
        } catch (MessagingException e) {
            throw e;
        }

    }

    private String sendEmailNoAuth(List<String> emailTo, List<String> ccList, String subject, String content) throws
            MessagingException, IOException {

        log.info("Sending email with no authentication");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", mailSmtpHost);
        properties.setProperty("mail.smtp.port", mailSmtpPort);
        properties.setProperty("mail.debug", "true");

        log.info("sending with no authentication start");
        Session session = Session.getInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailSmtpEmail));
            for (String emailAdd : emailTo) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAdd, false));
            }
            if (!ccList.isEmpty()) {
                for (String emailAdd : ccList) {
                    message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(emailAdd, false));
                }
            }
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8"); //change this to html content

            Transport transport = session.getTransport();
            try {
                log.info("Sending emails");
                transport.sendMessage(message, message.getAllRecipients());
                log.info("Emails sent");
            } catch (Exception e) {
                log.info("EMAIL UTIL - START");
                log.info("Error Sending: " + e);
                return "Failed in sending notification";
            }
            transport.close();
            log.info("EMAIL UTIL - END");
            return "Sending completed";
        } catch (MessagingException e) {
            throw e;
        }

    }

}
