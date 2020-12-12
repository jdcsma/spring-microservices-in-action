package jun.microservices.transfer;

import lombok.Data;

import java.io.Serializable;


@Data
public class Organization implements Serializable {

    private static final long serialVersionUID = 1;

    long id;
    String organizationId;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
