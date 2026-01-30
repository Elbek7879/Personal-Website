package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.personal.email}")
    private String personalEmail;

    @Value("${app.personal.name}")
    private String personalName;

    public void sendContactNotification(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(personalEmail);
        message.setSubject("New Contact Message: " + contact.getSubject());
        message.setText(buildEmailContent(contact));
        message.setReplyTo(contact.getEmail());

        mailSender.send(message);
        System.out.println("✅ Email sent to: " + personalEmail);
    }

    private String buildEmailContent(Contact contact) {
        return String.format("""
            You have received a new contact message from your portfolio website.
            
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            
            👤 NAME: %s
            📧 EMAIL: %s
            📱 PHONE: %s
            📝 SUBJECT: %s
            
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            
            💬 MESSAGE:
            %s
            
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            
            Received at: %s
            
            Reply to this email to respond directly to the sender.
            """,
                contact.getName(),
                contact.getEmail(),
                contact.getPhone() != null ? contact.getPhone() : "Not provided",
                contact.getSubject() != null ? contact.getSubject() : "No subject",
                contact.getMessage(),
                contact.getCreatedAt()
        );
    }

    public void sendThankYouEmail(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contact.getEmail());
        message.setSubject("Thank you for contacting me!");
        message.setText(String.format("""
            Hi %s,
            
            Thank you for reaching out! I have received your message and will get back to you as soon as possible.
            
            Best regards,
            %s
            """,
                contact.getName(),
                personalName
        ));

        mailSender.send(message);
    }
}
