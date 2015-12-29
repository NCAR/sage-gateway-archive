package sgf.gateway.search.api;

import java.util.List;

public interface SearchParameters extends List<SearchParameter> {
    SearchParameter find(String name);
}
