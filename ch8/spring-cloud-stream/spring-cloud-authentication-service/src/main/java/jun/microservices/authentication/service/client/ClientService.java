package jun.microservices.authentication.service.client;

import jun.microservices.authentication.domain.client.ClientPrincipal;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.validation.annotation.Validated;


@Validated
public interface ClientService extends ClientDetailsService {

    @Override
    ClientDetails loadClientByClientId(String clientId);

    void saveClient(ClientPrincipal clientDetails);
}
