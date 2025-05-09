package com.voteSphere.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.voteSphere.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.voteSphere.config.MailConfig;
import com.voteSphere.exception.EmailException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

public class EmailService {

	private static final Logger logger = LogManager.getLogger(EmailService.class);
	
	 // Template names
    private static final String KYC_FAILED_TEMPLATE = "kyc-failed.html";
    private static final String VERIFICATION_TEMPLATE = "email-verification.html";
    private static final String VOTE_CASTED_TEMPLATE = "vote-confirmation.html";
    
    private static final ExecutorService emailExecutor = Executors.newFixedThreadPool(10);




	private static Message buildMessage(Session session, List<String> toEmails, List<String> ccEmails,
										String subject, String body) throws MessagingException, UnsupportedEncodingException {

		String emailUsername = AppConfig.get("mail.username");
		String emailName = AppConfig.get("mail.name");

		logger.debug("Building message from: {} <{}>", emailName, emailUsername);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailUsername, emailName));

		// Set recipients
		message.setRecipients(Message.RecipientType.TO,
				toEmails.stream()
						.map(email -> {
							try {
								return new InternetAddress(email);
							} catch (Exception e) {
								throw new RuntimeException("Invalid email address: " + email, e);
							}
						})
						.toArray(InternetAddress[]::new));

		// Set CC if provided
		if (!ccEmails.isEmpty()) {
			message.setRecipients(Message.RecipientType.CC,
					ccEmails.stream()
							.map(email -> {
								try {
									return new InternetAddress(email);
								} catch (Exception e) {
									throw new RuntimeException("Invalid CC email address: " + email, e);
								}
							})
							.toArray(InternetAddress[]::new));
		}

		message.setSubject(subject);
		message.setContent(body, "text/html; charset=utf-8");
		return message;
	}

	public static String loadEmailTemplate(ServletContext context, String templateName) throws IOException {
		try (InputStream is = context.getResourceAsStream("/WEB-INF/pages/email-templates/" + templateName)) {
			if (is == null) {
				throw new FileNotFoundException("Template not found: " + templateName);
			}
			return new String(is.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	// Core Mail sending method
	private static void sendEmail(List<String> toEmails, List<String> ccEmails, String subject, String body)
			throws EmailException {
		try {
			Session session = MailConfig.createSession();
			Message message = buildMessage(session, toEmails, ccEmails, subject, body);

			logger.debug("Attempting to send email to: {}", toEmails);
			Transport.send(message);
			logger.info("Email successfully sent to: {}", toEmails);

		} catch (Exception e) {
			logger.error("Failed to send email to: {}", toEmails, e);
			throw new EmailException("Failed to send email", e);
		}
	}

	public static void sendToSingle(String recipientEmail, String subject, String body) throws EmailException {
		sendEmail(List.of(recipientEmail), List.of(), subject, body);
	}

	public static void sendToMultiple(List<String> recipientEmails, String subject, String body) throws EmailException {
		sendEmail(recipientEmails, List.of(), subject, body);
	}

	public static void sendWithCC(String recipientEmail, List<String> ccEmails, String subject, String body)
			throws EmailException {
		sendEmail(List.of(recipientEmail), ccEmails, subject, body);
	}

	public static void sendToMultipleWithCC(List<String> recipientEmails, List<String> ccEmails, String subject,
			String body) throws EmailException {
		sendEmail(recipientEmails, ccEmails, subject, body);
	}

    /**
     * Sends verification email asynchronously
     */
    public static void sendEmailVerificationAsync(ServletContext context, String baseUrl, String firstName, String email, int userId) {
        emailExecutor.submit(() -> {
            try {
                // Generate token
                JwtUtil jwtUtil = new JwtUtil();
                String token = jwtUtil.generateToken(email, userId);

                // Build verification link
                String verificationLink = baseUrl + "verify-email?token=" + token;

                // Prepare email
                String subject = "Verify your VoteSphere account";
                String emailTemplate = loadEmailTemplate(context, VERIFICATION_TEMPLATE)
                    .replace("{{userName}}", firstName)
                    .replace("{{confirmationLink}}", verificationLink);

                // Send email
                sendToSingle(email, subject, emailTemplate);
                logger.info("Verification email sent to {}", email);
            } catch (Exception e) {
                logger.error("Failed to send verification email to {}", email, e);
            }
        });
    }

    /**
     * Sends KYC failed email asynchronously
     */
    public static void sendKycFailedEmailAsync(ServletContext context, String baseUrl, String firstName, String email, String failureReason) {
        emailExecutor.submit(() -> {
            try {
                // Prepare email content
                String subject = "KYC Verification Failed - VoteSphere";
                String emailTemplate = loadEmailTemplate(context,KYC_FAILED_TEMPLATE)
                    .replace("{{userName}}", firstName)
                    .replace("{{reason}}", failureReason)
                    .replace("{{register-url}}", baseUrl + "register");

                // Send email
                sendToSingle(email, subject, emailTemplate);
                logger.info("KYC failure email sent to {}", email);
            } catch (Exception e) {
                logger.error("Failed to send KYC failure email to {}", email, e);
            }
        });
    }

    /**
     * Sends Vote Confirmation email asynchronously
     */
    public static void sendVoteSubmissionResponseAsync(ServletContext context, String baseUrl, String voterId, String firstName, 
            String email, String electionName, String submissionDate,String voteid) {
        emailExecutor.submit(() -> {
            try {
                // Prepare email content
                String subject = "Your Vote is Confirmed | VoteSphere";
                String emailTemplate = loadEmailTemplate(context,VOTE_CASTED_TEMPLATE)
                    .replace("{{userName}}", firstName)
                    .replace("{{voterID}}", voterId)
                    .replace("{{submissionDate}}", submissionDate)
                    .replace("{{electionName}}", electionName).replace("{{referenceNo}}",voteid);

                // Send email
                sendToSingle(email, subject, emailTemplate);
                logger.info("Vote confirmation email sent to {}", email);
            } catch (Exception e) {
                logger.error("Failed to send vote confirmation email to {}", email, e);
            }
        });
    }
    
    public static String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" +
               request.getServerName() + ":" +
               request.getServerPort() +
               request.getContextPath() + "/";
    }
	
    
}