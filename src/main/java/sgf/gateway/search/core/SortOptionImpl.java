package sgf.gateway.search.core;

import sgf.gateway.search.api.SortOption;

public class SortOptionImpl implements SortOption {

    private String name;
    private Boolean descending = false;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    @Override
    public Boolean isDescending() {
        return this.descending;
    }

}
