package sgf.gateway.model.metrics;

import java.io.Serializable;

public class UserSearchFacet implements Serializable {

    private static final long serialVersionUID = 1;

    private UserSearch userSearch;

    private String name;

    private String value;

    public UserSearch getUserSearch() {

        return this.userSearch;
    }

    public void setUserSearch(UserSearch userSearch) {

        this.userSearch = userSearch;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) {

        this.value = value;
    }
}
