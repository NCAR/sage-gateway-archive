package sgf.gateway.web.controllers.security.openid;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing parameter names as dictated by the OpenID protocol specification. It also contains additional parameters used by the specific OpenID
 * consumer and provider ESKE implementations.
 */
public class OpenidParameters {

    public static final String OPENID_IDENTIFIER = "openid_identifier";
    public static final String OPENID_COOKIE_NAME = "sgf.gateway.openid.cookie";
    public static final int OPENID_COOKIE_LIFETIME = 86400 * 365 * 10; // ten years

    public static final String SESSION_ATTRIBUTE_OPENID = "sgf.gateway.openid";
    public static final String SESSION_ATTRIBUTE_AUTHENTICATED = "sgf.gateway.openid.authenticated";
    public static final String SESSION_ATTRIBUTE_PARAMETERLIST = "sgf.gateway.openid.parameterlist";

    public static final String PARAMETER_OPENID = "openid";
    public static final String PARAMETER_USERNAME = "j_username";
    public static final String PARAMETER_PASSWORD = "j_password";
    public static final String PARAMETER_ACTION = "j_action";
    public static final String PARAMETER_REDIRECT = "redirectUrl";

    public static final String OPENID_MODE = "openid.mode";
    public static final String OPENID_CLAIMED_ID = "openid.claimed_id";
    public static final String OPENID_IDENTITY = "openid.identity";
    public static final String OPENID_REALM = "openid.realm";
    public static final String OPENID_ENDPOINT = "openid.op_endpoint";

    public static final String OPENID_MODE_ASSOCIATE = "associate";
    public static final String OPENID_CHECKID_SETUP = "checkid_setup";
    public static final String OPENID_CHECKID_IMMEDIATE = "checkid_immediate";
    public static final String OPENID_CHECK_AUTHENTICATION = "check_authentication";

    /**
     * Map of (alias,type) pairs used for OpenID Attributes Exchange
     */
    public static final Map<String, String> ATTRIBUTES = new HashMap<String, String>();

    public static final String AX_FIRST_NAME = "firstname";
    public static final String AX_MIDDLE_NAME = "middlename";
    public static final String AX_LAST_NAME = "lastname";
    public static final String AX_EMAIL = "email";
    public static final String AX_USER_NAME = "username";
    public static final String AX_ORGANIZATION = "organization";
    public static final String AX_CITY = "city";
    public static final String AX_STATE = "state";
    public static final String AX_COUNTRY = "country";
    public static final String AX_UUID = "uuid";
    public static final String AX_GATEWAY = "gateway";
    public static final String AX_AUTHORITY = "authority";

    static {

        // fields in OpenID schema
        // Found at: http://openid.net/specs/openid-attribute-properties-list-1_0-01.html
        ATTRIBUTES.put(AX_FIRST_NAME, "http://openid.net/schema/namePerson/first");
        ATTRIBUTES.put(AX_MIDDLE_NAME, "http://openid.net/schema/namePerson/middle");
        ATTRIBUTES.put(AX_LAST_NAME, "http://openid.net/schema/namePerson/last");
        ATTRIBUTES.put(AX_EMAIL, "http://openid.net/schema/contact/internet/email");
        ATTRIBUTES.put(AX_USER_NAME, "http://openid.net/schema/namePerson/friendly");
        ATTRIBUTES.put(AX_ORGANIZATION, "http://openid.net/schema/company/name");
        ATTRIBUTES.put(AX_CITY, "http://openid.net/schema/contact/city/home");
        ATTRIBUTES.put(AX_STATE, "http://openid.net/schema/contact/state/home");
        ATTRIBUTES.put(AX_COUNTRY, "http://openid.net/schema/contact/country/home");
        ATTRIBUTES.put(AX_UUID, "http://openid.net/schema/person/guid");

        // fields in proprietary schema
        ATTRIBUTES.put(AX_AUTHORITY, "http://www.earthsystemgrid.org/authority");
        ATTRIBUTES.put(AX_GATEWAY, "http://www.earthsystemgrid.org/gateway");

    }

    public static final int UNLIMITED = 1000; // TODO: set to 0 when supported by Openid4Java

}
