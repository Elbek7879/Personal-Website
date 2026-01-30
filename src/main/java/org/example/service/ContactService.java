package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final EmailService emailService;

    public Contact saveContact(Contact contact) {
        Contact saved = contactRepository.save(contact);

        // Send email notification
        try {
            emailService.sendContactNotification(saved);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return saved;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Contact> getUnreadContacts() {
        return contactRepository.findByIsReadFalseOrderByCreatedAtDesc();
    }

    public long getUnreadCount() {
        return contactRepository.countByIsReadFalse();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public void markAsRead(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            Contact c = contact.get();
            c.setRead(true);
            contactRepository.save(c);
        }
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}
