package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<EventEntity, String> {

    Optional<EventEntity> findByEventName(String eventName);
    List<EventEntity> findByOrganizerId(String organizerId);
    
}

