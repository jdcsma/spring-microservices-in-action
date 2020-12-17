package jun.microservices.event.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;


public interface CustomSink {

    String INPUT = "customSink";

    @Input(CustomSink.INPUT)
    SubscribableChannel input();
}
