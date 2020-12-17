package jun.microservices.configuration;

import jun.microservices.event.source.CustomSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;


/**
 * Enables the binding of targets annotated with Input and Output to a broker,
 * according to the list of interfaces passed as value to the annotation.
 */
//@EnableBinding(Source.class)
@EnableBinding(CustomSource.class)
@Configuration
public class StreamConfiguration {
}
