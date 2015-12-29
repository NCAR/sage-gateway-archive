package sgf.gateway.web.controllers.search.command;

import com.kennardconsulting.core.net.UrlEncodedQueryString;
import sgf.gateway.search.api.Facet;
import sgf.gateway.search.core.SpatialFilter;
import sgf.gateway.search.core.TemporalFilter;
import sgf.gateway.utils.Parameter;

import java.net.URI;
import java.util.*;
import java.util.Map.Entry;

public class SearchCommandFreeText extends SearchCommandTemplate {

    protected static final String FREETEXT_PARAM_NAME = "freeText";

    protected static final Set<String> VISIBLE_PARAMETERS = new HashSet<String>();

    static {
        VISIBLE_PARAMETERS.add(FREETEXT_PARAM_NAME);
    }

    protected String freeText;

    public SearchCommandFreeText(URI searchPath, List<String> facetNames) {
        super(searchPath, facetNames);
    }

    public List<Parameter> getHiddenSearchFormParameters() {

        UrlEncodedQueryString searchQuery = UrlEncodedQueryString.parse(this.getSearchPath());

        searchQuery.remove("page");
        Map<String, List<String>> queryParameters = searchQuery.getMap();

        List<Parameter> results = new ArrayList<Parameter>();

        for (Entry<String, List<String>> entry : queryParameters.entrySet()) {
            if (!VISIBLE_PARAMETERS.contains(entry.getKey())) {
                for (String value : entry.getValue()) {
                    results.add(new Parameter(entry.getKey(), value));
                }
            }
        }

        return results;
    }

    public Facet getVariableNameFacet() {
        Facet nameFacet = null;

        for (Facet facet : this.facets) {

            if (facet.getName().equalsIgnoreCase("Variable Name")) {
                nameFacet = facet;
            }
        }

        return nameFacet;
    }

    public Facet getVariableStandardNameFacet() {

        Facet nameFacet = null;

        for (Facet facet : this.facets) {

            if (facet.getName().equalsIgnoreCase("CF Variable")) {
                nameFacet = facet;
            }
        }

        return nameFacet;
    }

    public String getFreeText() {
        return this.freeText;
    }

    public void setFreeText(String text) {
        this.freeText = text;
    }

    @Override
    public void populateFilters() {
    }

    @Override
    public SpatialFilter getSpatialFilter() {
        return null;
    }

    @Override
    public TemporalFilter getTemporalFilter() {
        return null;
    }
}
