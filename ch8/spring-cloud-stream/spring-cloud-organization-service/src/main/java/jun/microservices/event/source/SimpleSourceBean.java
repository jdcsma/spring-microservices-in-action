package jun.microservices.event.source;

import jun.microservices.event.model.OrganizationChangeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class SimpleSourceBean {

    private final CustomSource source;

    @Autowired
    public SimpleSourceBean(CustomSource source) {
        Objects.requireNonNull(source);
        this.source = source;
    }

    public void publishOrganizationChange(String action, String organizationId) {

        OrganizationChangeModel model = new OrganizationChangeModel(
                action, organizationId);

        Message<OrganizationChangeModel> message =
                MessageBuilder.withPayload(model).build();
        this.source.customOutput().send(message);
    }
}
