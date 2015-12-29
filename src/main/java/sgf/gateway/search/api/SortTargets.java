package sgf.gateway.search.api;

import java.util.Set;

public interface SortTargets {

    Set<String> getNames();

    String getFieldName(String name);
}
