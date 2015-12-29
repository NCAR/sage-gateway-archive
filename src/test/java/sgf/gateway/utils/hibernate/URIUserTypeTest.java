package sgf.gateway.utils.hibernate;

import org.junit.Test;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class URIUserTypeTest {

    @Test
    public void testEqualsWithTwoNulls() {

        URIUserType uriUserType = new URIUserType();

        boolean test = uriUserType.equals(null, null);

        assertThat("Unexpected equals value", test, is(true));
    }

    @Test
    public void testEqualsWithFirstNull() throws Exception {

        URIUserType uriUserType = new URIUserType();

        URI uri = new URI("http://test.com");

        boolean test = uriUserType.equals(null, uri);

        assertThat("Unexpected equals value", test, is(false));
    }

    @Test
    public void testEqualsWithSecondNull() throws Exception {

        URIUserType uriUserType = new URIUserType();

        URI uri = new URI("http://test.com");

        boolean test = uriUserType.equals(uri, null);

        assertThat("Unexpected equals value", test, is(false));
    }

    @Test
    public void testEqualsWithBothEqual() throws Exception {

        URIUserType uriUserType = new URIUserType();

        URI uri1 = new URI("http://test.com");
        URI uri2 = new URI("http://test.com");

        boolean test = uriUserType.equals(uri1, uri2);

        assertThat("Unexpected equals value", test, is(true));
    }

    @Test
    public void testEqualsWithUnequal() throws Exception {

        URIUserType uriUserType = new URIUserType();

        URI uri1 = new URI("http://test1.com");
        URI uri2 = new URI("http://test2.com");

        boolean test = uriUserType.equals(uri1, uri2);

        assertThat("Unexpected equals value", test, is(false));
    }

    @Test
    public void testEqualsWithFirstNonURI() throws Exception {

        URIUserType uriUserType = new URIUserType();

        String string = "http://test.com";
        URI uri = new URI("http://test.com");

        boolean test = uriUserType.equals(string, uri);

        assertThat("Unexpected equals value", test, is(false));
    }

    @Test
    public void testEqualsWithSecondNonURI() throws Exception {

        URIUserType uriUserType = new URIUserType();

        URI uri = new URI("http://test.com");
        String string = "http://test.com";

        boolean test = uriUserType.equals(uri, string);

        assertThat("Unexpected equals value", test, is(false));
    }

    @Test
    public void testReturnedClass() {

        URIUserType uriUserType = new URIUserType();

        assertTrue("Unexpected class value", uriUserType.returnedClass() == URI.class);
    }
}
