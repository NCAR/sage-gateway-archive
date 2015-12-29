package sgf.gateway.search.core;

import sgf.gateway.search.api.SortTargets;

import java.util.Map;
import java.util.Set;

public class SortTargetsImpl implements SortTargets {

    private final Map<String, String> sortTargets;

    public SortTargetsImpl(Map<String, String> sortTargets) {

        this.sortTargets = sortTargets;
    }

    @Override
    public Set<String> getNames() {
        return this.sortTargets.keySet();
    }

    @Override
    public String getFieldName(String name) {

        return this.sortTargets.get(name);
    }
}
