package in.venkat.foodiesapi.controller;

import in.venkat.foodiesapi.entity.ContactMessage;
import in.venkat.foodiesapi.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

//    @PostMapping
//    public ResponseEntity<String> receiveMessage(@RequestBody ContactMessage message) {
//        contactService.saveMessage(message);
//        return ResponseEntity.ok("Message saved successfully");
//    }

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody ContactMessage message) {
//        System.out.println("Received message: " + message);  // Log input
        contactService.saveMessage(message);
        return ResponseEntity.ok("Message saved successfully");
    }
}
