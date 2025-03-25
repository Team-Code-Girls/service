package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.EventEntity;
import ro.unibuc.hello.data.EventRepository;

@ExtendWith(SpringExtension.class)
public class EventServiceTest {

    @Mock 
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService = new EventService();


}
