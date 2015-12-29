package sgf.gateway.service.messaging.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.util.NestedServletException;

import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

public class WhiteListExceptionHandlingStrategyTest {

    @Test
    public void testIsWhiteListExceptionWithClass() {

        Set<Class> whiteList = new HashSet<>();
        whiteList.add(java.lang.RuntimeException.class);

        WhiteListExceptionHandlingStrategy whiteListExceptionHandlingStrategy = new WhiteListExceptionHandlingStrategy(null, whiteList);

        Assert.assertTrue(whiteListExceptionHandlingStrategy.isWhiteListException(new RuntimeException()));
    }

    /**
     * Testing to see if a Child Exception will match a Parent Exception. In this case we are checking to see if ClassCastException will be recognized as a
     * RuntimeException. The result should be false.
     */
    @Test
    public void testIsWhiteListExceptionWithChildClass() {

        Set<Class> whiteList = new HashSet<>();
        whiteList.add(java.lang.RuntimeException.class);

        WhiteListExceptionHandlingStrategy whiteListExceptionHandlingStrategy = new WhiteListExceptionHandlingStrategy(null, whiteList);

        Assert.assertFalse(whiteListExceptionHandlingStrategy.isWhiteListException(new ClassCastException()));
    }

    @Test
    public void testIsWhiteListExceptionWithManyClasses() {

        Set<Class> whiteList = new HashSet<>();
        whiteList.add(java.lang.RuntimeException.class);
        whiteList.add(java.net.SocketException.class);

        WhiteListExceptionHandlingStrategy whiteListExceptionHandlingStrategy = new WhiteListExceptionHandlingStrategy(null, whiteList);

        Assert.assertTrue(whiteListExceptionHandlingStrategy.isWhiteListException(new SocketException()));
    }

    // This test is classless. Tee hee...
    @Test
    public void testIsWhiteListExceptionWithOutClass() {

        Set<Class> whiteList = new HashSet<>();
        whiteList.add(java.lang.RuntimeException.class);

        WhiteListExceptionHandlingStrategy whiteListExceptionHandlingStrategy = new WhiteListExceptionHandlingStrategy(null, whiteList);

        Assert.assertFalse(whiteListExceptionHandlingStrategy.isWhiteListException(new Exception()));
    }

    @Test
    public void testIsWhiteListExceptionWithNestedException() {

        Set<Class> whiteList = new HashSet<>();
        whiteList.add(java.net.SocketException.class);

        Exception exception = new NestedServletException("Request processing failed", new RuntimeException(new SocketException("Broken Pipe")));

        WhiteListExceptionHandlingStrategy whiteListExceptionHandlingStrategy = new WhiteListExceptionHandlingStrategy(null, whiteList);

        Assert.assertTrue(whiteListExceptionHandlingStrategy.isWhiteListException(exception));
    }

    @Test
    public void testIsNotWhiteListExceptionWithNestedException() {

        Set<Class> whiteList = new HashSet<>();
        whiteList.add(java.net.SocketException.class);

        Exception exception = new NestedServletException("Request processing failed", new RuntimeException(new RuntimeException("Broken Pipe")));

        WhiteListExceptionHandlingStrategy whiteListExceptionHandlingStrategy = new WhiteListExceptionHandlingStrategy(null, whiteList);

        Assert.assertFalse(whiteListExceptionHandlingStrategy.isWhiteListException(exception));
    }
}
