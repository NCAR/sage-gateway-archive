package sgf.gateway.model.security;

import java.util.Comparator;

/**
 * Utility class to compare GroupData by type first, then alphabetically by name.
 */
public class GroupDataTypeComparator implements Comparator<GroupData> {

    public int compare(GroupData gd1, GroupData gd2) {
        if (gd1.getType() != gd2.getType()) {
            return gd1.getType().compareTo(gd2.getType());
        } else {
            return gd1.getName().compareTo(gd2.getName());
        }
    }

}
