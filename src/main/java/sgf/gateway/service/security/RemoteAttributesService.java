package sgf.gateway.service.security;

/**
 * Interface for querying user attributes (granted authorities and personal information) by remote clients.
 */
public interface RemoteAttributesService {

    /**
     * Method to query the user personal information (first and last name, email etc.)
     *
     * @param: the user OpenID identifier
     * @return: the user first name, last name, and email address (in this order)
     */
    String[] getUserInfo(String openid) throws IllegalArgumentException;

    /**
     * Method to query the user access control attributes, i.e. the user memberships.
     *
     * @param openid : the user OpenID identifier
     * @return the user access control attributes serialized as strings
     */
    String[] getUserAttributes(String openid) throws IllegalArgumentException;

}
