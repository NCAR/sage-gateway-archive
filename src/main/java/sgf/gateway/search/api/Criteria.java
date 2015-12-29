package sgf.gateway.search.api;

import sgf.gateway.search.core.SpatialFilter;
import sgf.gateway.search.core.TemporalFilter;

public interface Criteria {

    String getFreeText();

    SearchParameters getSearchParameters();

    SpatialFilter getSpatialFilter();

    TemporalFilter getTemporalFilter();

    Facets getFacets();

    Integer getResultSize();

    SortOptions getSortOptions();

    Integer getStartIndex();
}
