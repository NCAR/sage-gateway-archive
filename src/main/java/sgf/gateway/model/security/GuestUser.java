package sgf.gateway.model.security;

import org.safehaus.uuid.UUID;

/**
 * Singleton User instance representing a guest user.
 */
public class GuestUser {

    /**
     * IMPORTANT: the username must match the value stored in the database
     */
    public static final String GUEST_USERNAME = "guest";
    public static final String GUEST_OPENID = "guest";

    /**
     * IMPORTANT: the UUID must match the value stored in the database
     */
    private static final String GUEST_ID = "9535b3b8-0933-478a-ad27-78852d97a946";

    private static final User GUEST = new User(new UUID(GUEST_ID), 1);

    static {
        GUEST.setUserName(GUEST_USERNAME);
        GUEST.setOpenid(GUEST_OPENID);
    }

    private GuestUser() {
    }

    public static User getInstance() {
        return GUEST;
    }
}
