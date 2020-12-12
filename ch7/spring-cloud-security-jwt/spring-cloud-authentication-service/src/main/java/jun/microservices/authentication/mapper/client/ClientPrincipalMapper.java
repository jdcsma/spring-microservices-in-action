package jun.microservices.authentication.mapper.client;

import jun.microservices.authentication.domain.client.ClientPrincipal;


public interface ClientPrincipalMapper {

    ClientPrincipal loadClientByClientId(String clientId);

    void saveClient(ClientPrincipal clientPrincipal);
}
