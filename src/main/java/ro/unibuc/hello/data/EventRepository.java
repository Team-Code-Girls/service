package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.data.EventEntity;

@Repository
public interface EventRepository extends MongoRepository<EventEntity, String> {

    Optional<Event> findByEventName(String eventName);
    List<Event> findByOrganizerId(String organizerId);
    
}
