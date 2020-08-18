package jun.microservices.licensingservice.clients;

import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;


public abstract class OrganizationClient {

    protected static void showStatusMessage(
            HttpStatus status, String method, Logger logger) {

        if (logger == null) {
            return;
        }

        if (status.isError()) {
            if (status.is5xxServerError()) {
                logger.error(method + ": server error:" + status.toString());
            } else if (status.is4xxClientError()) {
                logger.error(method + ": client error:" + status.toString());
            }
        } else {
            logger.info(method + ": success:" + status.toString());
        }
    }
}
