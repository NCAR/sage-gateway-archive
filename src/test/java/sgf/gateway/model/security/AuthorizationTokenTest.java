package sgf.gateway.model.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import static org.junit.Assert.*;

public class AuthorizationTokenTest {

    /**
     * The Constant TEST_LIFETIME.
     */
    private static final int TEST_LIFETIME = 5;

    /**
     * The authorization token.
     */
    private AuthorizationToken authorizationToken;

    /**
     * The test date.
     */
    private Calendar testDate;

    /**
     * The test expires.
     */
    private Calendar testExpires;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {

        this.authorizationToken = new AuthorizationToken();
        Assert.assertNull("Date should be null for default ctor", this.authorizationToken.getDate());
        Assert.assertNull("Expires should be null for default ctor", this.authorizationToken.getExpires());

        // Grab the system time and grab and exact copy to test the expiration
        // against.
        this.testDate = Calendar.getInstance();
        this.testExpires = (Calendar) this.testDate.clone();
    }

    /**
     * Test authorization token uri user int ctor.
     *
     * @throws URISyntaxException the URI syntax exception
     */
    @Test
    public void testAuthorizationTokenURIUserIntCtor() throws URISyntaxException {

        final User user = new User();
        final URI uri = new URI("test");

        this.authorizationToken = new AuthorizationToken(uri, user, Calendar.MINUTE, TEST_LIFETIME);

        Assert.assertNotNull("Token was not assigned", this.authorizationToken.getToken());
        Assert.assertEquals("Token was not of expected length", 36, this.authorizationToken.getToken().toString().length());

        // At this level just check the nullness of the dates
        Assert.assertNotNull("Date should not be null", this.authorizationToken.getDate());
        Assert.assertNotNull("Expires should not be null", this.authorizationToken.getExpires());
    }

    /**
     * Test initialize dates.
     */
    @Test
    public void testInitializeDates() {

        this.authorizationToken.initializeDates(Calendar.MINUTE, TEST_LIFETIME);

        final long created = this.authorizationToken.getDate().getTime();
        final long expires = this.authorizationToken.getExpires().getTime();

        assertTrue("Expiration should be after the start time", expires > created);

        // Check the difference against the lifetime passed in.
        final long difference = expires - created;

        assertEquals("Difference didn't match the lifetime", 5, difference / 1000 / 60);
    }

    /**
     * Test is expired true.
     */
    @Test
    public void testIsExpiredTrue() {

        this.testExpires.add(Calendar.MINUTE, -1 * (TEST_LIFETIME));
        this.authorizationToken.setExpires(this.testExpires.getTime());

        assertTrue("Token should expire, expiration past", this.authorizationToken.isExpired());
    }

    /**
     * Test is expired false.
     */
    @Test
    public void testIsExpiredFalse() {

        this.testExpires.add(Calendar.MINUTE, TEST_LIFETIME);
        this.authorizationToken.setExpires(this.testExpires.getTime());

        assertFalse("Token should not expire, expiration not passed", this.authorizationToken.isExpired());
    }

    /**
     * Test is expires now.
     */
    @Test
    public void testIsExpiresNow() {

        this.authorizationToken.setDate(this.testDate.getTime());
        this.authorizationToken.setExpires(this.testExpires.getTime());
    }
}
