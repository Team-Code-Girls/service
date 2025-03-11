package ro.unibuc.hello.data;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import main.java.ro.unibuc.hello.data.TicketEntity;

@Repository
public interface TicketRepository extends MongoRepository<TicketEntity, String> {

}
