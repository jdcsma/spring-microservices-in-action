package jun.microservices.event.handler;

import jun.microservices.event.model.OrganizationChangeModel;
import jun.microservices.event.sink.CustomSink;
import jun.microservices.repository.OrganizationRedisRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;


@Component
public class OrganizationChangeHandler {

    private static final Logger logger = LogManager.getLogger();

    private final OrganizationRedisRepository organizationRepository;

    @Autowired
    public OrganizationChangeHandler(OrganizationRedisRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @StreamListener(CustomSink.INPUT)
    public void loggerSink(OrganizationChangeModel model) {
        switch (model.getAction()) {
            case "UPDATE":
                logger.info("Received a UPDATE event " +
                                "the organization service for organization id {}",
                        model.getOrganizationId());
                this.organizationRepository.deleteByOrganizationId(
                        model.getOrganizationId());
                break;

            case "DELETE":
                logger.info("Received a DELETE event " +
                                "the organization service for organization id {}",
                        model.getOrganizationId());
                this.organizationRepository.deleteByOrganizationId(
                        model.getOrganizationId());
                break;

            default:
                logger.error("Received an UNKNOWN event from " +
                        "the organization service of action: {}", model.getAction());
                break;
        }
    }
}
