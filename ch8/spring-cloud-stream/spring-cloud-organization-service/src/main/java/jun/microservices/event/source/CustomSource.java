package jun.microservices.event.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


public interface CustomSource {

    String CUSTOM_OUTPUT = "customSource";

    @Output(CustomSource.CUSTOM_OUTPUT)
    MessageChannel customOutput();
}
