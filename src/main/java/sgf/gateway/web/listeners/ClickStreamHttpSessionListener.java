package sgf.gateway.web.listeners;

import sgf.gateway.web.HttpSessionConstants;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * The Class ClickStreamHttpSessionListener. Used to place a UUID identifier on the HttpSession. Tomcat's session identifiers are not unique across restarts.
 * That makes it difficult to track clickstream requests in reporting since we might actually have equal identifiers across restarts.
 * <p/>
 * The UUID identifier is stored under the sgf.gateway.web.HttpSessionConstants.ESK_SESSIONID attribute.
 *
 * @author nhook
 */
public class ClickStreamHttpSessionListener implements HttpSessionListener {

    /**
     * {@inheritDoc}
     */
    public void sessionCreated(HttpSessionEvent se) {

        HttpSession httpSession = se.getSession();

        httpSession.setAttribute(HttpSessionConstants.ESKE_SESSIONID, org.safehaus.uuid.UUIDGenerator.getInstance().generateRandomBasedUUID());
    }

    /**
     * {@inheritDoc}
     */
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
