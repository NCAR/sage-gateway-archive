package sgf.gateway.search.core;

import sgf.gateway.search.api.SearchParameter;
import sgf.gateway.search.api.SearchParameters;

import java.util.ArrayList;
import java.util.Collection;

public class SearchParametersImpl extends ArrayList<SearchParameter> implements SearchParameters {

    private static final long serialVersionUID = 1L;

    public SearchParametersImpl() {
        super();
    }

    public SearchParametersImpl(Collection<? extends SearchParameter> c) {
        super(c);
    }

    public SearchParametersImpl(int initialCapacity) {
        super(initialCapacity);
    }

    public SearchParameter find(String name) {

        SearchParameter result = null;

        for (SearchParameter searchParameter : this) {
            if (name.equals(searchParameter.getName())) {
                result = searchParameter;
            }
        }

        return result;
    }
}
