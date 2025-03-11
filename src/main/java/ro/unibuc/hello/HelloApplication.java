package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.EventRepository;

import jakarta.annotation.PostConstruct;
 
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = EventRepository.class)
public class HelloApplication {

	@Autowired
	private EventRepository eventRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	// @PostConstruct
	// public void runAfterObjectCreated() {
	// 	informationRepository.deleteAll();
	// 	informationRepository.save(new InformationEntity("Overview",
	// 			"This is an example of using a data storage engine running separately from our applications server"));
	// }
import ro.unibuc.hello.data.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class HelloApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }
}
