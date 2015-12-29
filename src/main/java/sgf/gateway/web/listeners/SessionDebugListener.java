package sgf.gateway.web.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;
import java.util.Enumeration;

public class SessionDebugListener implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener {

    private static final Logger LOG = LoggerFactory.getLogger(SessionDebugListener.class);

    public void sessionCreated(HttpSessionEvent arg0) {

        if (LOG.isTraceEnabled()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Session Created\n");
            this.buildSessionValues(arg0.getSession(), stringBuilder);
            this.buildThreadValues(stringBuilder);

            LOG.trace(stringBuilder.toString());
        }
    }

    public void sessionDestroyed(HttpSessionEvent arg0) {

        if (LOG.isTraceEnabled()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Session Destroyed\n");
            this.buildSessionValues(arg0.getSession(), stringBuilder);
            this.buildThreadValues(stringBuilder);

            LOG.trace(stringBuilder.toString());
        }
    }

    public void attributeAdded(HttpSessionBindingEvent arg0) {

        if (LOG.isTraceEnabled()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attribute Added\n");
            this.buildSessionValues(arg0.getSession(), stringBuilder);
            stringBuilder.append("  Added Attribute\n");
            stringBuilder.append("    " + arg0.getName() + ": " + arg0.getValue() + "\n");
            this.buildSessionAttributes(arg0.getSession(), stringBuilder);
            this.buildThreadValues(stringBuilder);

            LOG.trace(stringBuilder.toString());
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent arg0) {

        if (LOG.isTraceEnabled()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attribute Removed\n");
            this.buildSessionValues(arg0.getSession(), stringBuilder);
            stringBuilder.append("  Removed Attribute\n");
            stringBuilder.append("    " + arg0.getName() + ": " + arg0.getValue() + "\n");
            this.buildThreadValues(stringBuilder);

            LOG.trace(stringBuilder.toString());
        }
    }

    public void attributeReplaced(HttpSessionBindingEvent arg0) {

        if (LOG.isTraceEnabled()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attribute Replaced\n");
            this.buildSessionValues(arg0.getSession(), stringBuilder);
            stringBuilder.append("  Replaced Attribute\n");
            stringBuilder.append("    " + arg0.getName() + ": " + arg0.getValue() + "\n");
            this.buildSessionAttributes(arg0.getSession(), stringBuilder);
            this.buildThreadValues(stringBuilder);

            LOG.trace(stringBuilder.toString());
        }
    }

    public void sessionDidActivate(HttpSessionEvent arg0) {

        if (LOG.isTraceEnabled()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Session Did Activate\n");
            this.buildSessionValues(arg0.getSession(), stringBuilder);
            this.buildSessionAttributes(arg0.getSession(), stringBuilder);
            this.buildThreadValues(stringBuilder);

            LOG.trace(stringBuilder.toString());
        }

    }

    public void sessionWillPassivate(HttpSessionEvent arg0) {

        if (LOG.isTraceEnabled()) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Session Will Passivate\n");
            this.buildSessionValues(arg0.getSession(), stringBuilder);
            this.buildSessionAttributes(arg0.getSession(), stringBuilder);
            this.buildThreadValues(stringBuilder);

            LOG.trace(stringBuilder.toString());
        }
    }

    private StringBuilder buildSessionValues(HttpSession session, StringBuilder stringBuilder) {

        stringBuilder.append("  Session Values\n");
        stringBuilder.append("    Id: " + session.getId() + "\n");

        return stringBuilder;
    }

    private StringBuilder buildSessionAttributes(HttpSession session, StringBuilder stringBuilder) {

        stringBuilder.append("  Session Attibutes\n");

        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            stringBuilder.append("    " + attributeName + ": " + session.getAttribute(attributeName) + "\n");
        }

        return stringBuilder;
    }

    private StringBuilder buildThreadValues(StringBuilder stringBuilder) {

        stringBuilder.append("  Thread Values\n");
        stringBuilder.append("   Id: " + Thread.currentThread().getId() + "\n");
        stringBuilder.append("   Stack:\n");

        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();

        stringBuilder.append("     " + stackTraceElementArray[stackTraceElementArray.length - 1] + "\n");

        for (int i = stackTraceElementArray.length - 2; i > 0; i--) {

            stringBuilder.append("        at " + stackTraceElementArray[i] + "\n");
        }

        return stringBuilder;
    }
}
