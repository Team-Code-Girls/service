package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.data.TicketEntity;

@Repository
public interface TicketRepository extends MongoRepository<TicketEntity, String> {

    List<TicketEntity> findAll();
}
