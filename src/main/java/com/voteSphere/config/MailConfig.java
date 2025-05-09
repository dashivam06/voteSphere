package com.voteSphere.config;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailConfig {
 
    private static final Logger logger = LogManager.getLogger(MailConfig.class);  // Logger initialization

    public static Session createSession() throws NamingException {
        
        logger.info("Creating mail session...");

        String emailHost = AppConfig.get("mail.host");
        String emailPort = AppConfig.get("mail.port");
        String emailUsername = AppConfig.get("mail.username");
        String emailPassword = AppConfig.get("mail.password");
        String sslProtocol = AppConfig.get("mail.smtp.ssl.protocols");
        String startTtlsEnable = AppConfig.get("mail.smtp.starttls.enable");
        String smtpAuth = AppConfig.get("mail.smtp.auth");
        String httpsProtocol = AppConfig.get("https.protocols");


        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", startTtlsEnable);
        props.put("mail.smtp.ssl.protocols", sslProtocol);
        props.put("https.protocols",httpsProtocol);
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtp.port", emailPort);

        try {
            logger.info("Connecting to mail server: {}", emailHost);
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailUsername, emailPassword);
                }
            });

            logger.info("Mail session created successfully.");
            return session;
        } catch (Exception e) {
            logger.error("Error creating mail session: {}", e.getMessage(), e);
            throw new NamingException("Error creating mail session: " + e.getMessage());
        }
    }
}
