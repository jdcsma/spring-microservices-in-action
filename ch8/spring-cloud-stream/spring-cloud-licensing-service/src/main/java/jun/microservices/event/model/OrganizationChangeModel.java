package jun.microservices.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationChangeModel implements Serializable {

    private static final long serialVersionUID = 1;

    private String action;
    private String organizationId;
}
