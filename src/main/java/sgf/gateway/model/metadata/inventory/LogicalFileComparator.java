package sgf.gateway.model.metadata.inventory;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Utility class to allow ordering of logical files by name (when the ordering is not executed as part of the database query).
 */
public class LogicalFileComparator implements Comparator, Serializable {

    /**
     * Class version identifier for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public int compare(Object obj1, Object obj2) {

        LogicalFile lf1 = (LogicalFile) obj1;
        LogicalFile lf2 = (LogicalFile) obj2;
        return lf1.getName().compareTo(lf2.getName());

    }
}
