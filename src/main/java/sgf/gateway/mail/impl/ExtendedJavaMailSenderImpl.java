package sgf.gateway.mail.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Session;

public class ExtendedJavaMailSenderImpl extends JavaMailSenderImpl {

    /**
     * The debug.
     */
    private boolean debug;

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Session getSession() {

        Session session = super.getSession();

        session.setDebug(debug);

        return session;
    }

    /**
     * Gets the debug.
     *
     * @return the debug
     */
    public boolean getDebug() {

        return this.debug;
    }

    /**
     * Sets the debug.
     *
     * @param debug the new debug
     */
    public void setDebug(boolean debug) {

        this.debug = debug;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUsername(String username) {

        if (StringUtils.isNotBlank(username)) {
            super.setUsername(username);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPassword(String password) {

        if (StringUtils.isNotBlank(password)) {
            super.setPassword(password);
        }
    }
}
