package sgf.gateway.web.controllers.search.command;

import sgf.gateway.search.api.Facets;
import sgf.gateway.search.api.SortOptions;
import sgf.gateway.search.core.SpatialFilter;
import sgf.gateway.search.core.TemporalFilter;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public interface SearchCommand {

    void populateFacets(HttpServletRequest request);

    void populateFilters();

    Facets getFacets();

    SortOptions getSortOptions();

    String getFreeText();

    SpatialFilter getSpatialFilter();

    TemporalFilter getTemporalFilter();

    Integer getResultSize();

    void setResultSize(Integer resultSize);

    Integer getStartIndex();

    URI getSearchPath();
}
