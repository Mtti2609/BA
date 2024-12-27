package com.API.API.service;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

    private final String defaultEmail = "myteam363636@gmail.com"; // Default email
    private final String defaultPassword = "kfwl oufa xxfi crgq"; // Gmail application password

    public void sendEmail(String toEmail, String subject, String body, List<String> attachmentPaths) {
        try {
            // Gmail SMTP server configuration
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Authenticate Gmail account
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(defaultEmail, defaultPassword);
                }
            });

            // Create email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(defaultEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Create multipart to send the email with HTML content and attachments
            MimeMultipart multipart = new MimeMultipart();

            // Add email body (HTML content)
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(body, "text/html; charset=UTF-8");
            multipart.addBodyPart(textPart);

            // Add attachments (images, PDFs, Word files, etc.)
            if (attachmentPaths != null && !attachmentPaths.isEmpty()) {
                for (String attachmentPath : attachmentPaths) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    FileDataSource fileDataSource = new FileDataSource(attachmentPath);
                    attachmentPart.setDataHandler(new DataHandler(fileDataSource));
                    attachmentPart.setFileName(fileDataSource.getName()); // Set file name for attachment
                    multipart.addBodyPart(attachmentPart);
                }
            }

            // Set the content of the message to be the multipart
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Error sending email: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error processing attachments: " + e.getMessage());
            throw new RuntimeException("Error processing attachments: " + e.getMessage());
        }
    }
}
