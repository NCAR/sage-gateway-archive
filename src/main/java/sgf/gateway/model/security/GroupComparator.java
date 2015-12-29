package sgf.gateway.model.security;

import java.util.Comparator;

/**
 * Class to order groups alphabetically by name.
 */
public class GroupComparator implements Comparator {

    // TODO:  Change class to use generics.
    public int compare(Object obj1, Object obj2) {
        Group g1 = (Group) obj1;
        Group g2 = (Group) obj2;

        return g1.getName().compareTo(g2.getName());
    }

}
