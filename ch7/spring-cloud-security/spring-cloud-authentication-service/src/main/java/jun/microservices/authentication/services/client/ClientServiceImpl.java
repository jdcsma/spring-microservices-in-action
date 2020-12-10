package jun.microservices.authentication.services.client;

import jun.microservices.authentication.domain.client.ClientPrincipal;
import jun.microservices.authentication.mapper.client.ClientPrincipalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class ClientServiceImpl implements ClientService {

    private ClientPrincipalMapper mapper;

    @Autowired
    public void setMapper(ClientPrincipalMapper mapper) {
        this.mapper = mapper;
    }

//    @Cacheable("ClientDetails")
    @Transactional("ClientTransactionManager")
    @Override
    public ClientDetails loadClientByClientId(String clientId) {

        ClientPrincipal principal = this.mapper.loadClientByClientId(clientId);

        if (principal == null) {
            return null;
        }

        BaseClientDetails details = new BaseClientDetails();

        details.setClientId(principal.getClientId());
        details.setClientSecret(principal.getSecret());
        details.setScope(StringUtils.commaDelimitedListToSet(principal.getScope()));
        details.setAuthorizedGrantTypes(StringUtils.commaDelimitedListToSet(
                principal.getAuthorizedGrantTypes()));

        return details;
    }

    @Transactional("ClientTransactionManager")
    @Override
    public void saveClient(ClientPrincipal clientDetails) {
        this.mapper.saveClient(clientDetails);
    }
}
