package in.venkat.foodiesapi.service;

import in.venkat.foodiesapi.entity.ContactMessage;
import in.venkat.foodiesapi.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public void saveMessage(ContactMessage message) {
//        System.out.println("Saving to DB: " + message);
        contactRepository.save(message);
    }
}