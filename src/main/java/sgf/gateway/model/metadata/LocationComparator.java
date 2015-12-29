package sgf.gateway.model.metadata;

import java.util.Comparator;

public class LocationComparator implements Comparator<Location> {

    /**
     * {@inheritDoc}
     */
    public int compare(Location location1, Location location2) {
        return location1.getName().compareTo(location2.getName());
    }
}
