package sgf.gateway.utils.time.impl;

import sgf.gateway.utils.time.DateStrategy;

import java.util.Date;

/**
 * The Class NewDateStrategy.
 */
public class NewDateStrategy implements DateStrategy {

    /**
     * {@inheritDoc}
     */
    public Date getDate() {

        return new Date();
    }
}
