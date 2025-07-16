package in.venkat.foodiesapi.repository;

import in.venkat.foodiesapi.entity.ContactMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<ContactMessage, String> {
}