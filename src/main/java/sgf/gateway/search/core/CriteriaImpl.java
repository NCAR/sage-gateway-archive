package sgf.gateway.search.core;

import sgf.gateway.search.api.*;

import java.text.DateFormat;

public class CriteriaImpl implements Criteria {

    private String freeText;
    private Facets facets = new FacetsImpl();
    private Integer resultSize = 20;
    private SortOptions sortOptions = new SortOptionsImpl();
    private Integer startIndex = 0;
    private SearchParameters searchParameters = new SearchParametersImpl();
    private SpatialFilter spatialFilter;
    private TemporalFilter temporalFilter;

    public CriteriaImpl() {
        super();
    }

    @Override
    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }

    @Override
    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    @Override
    public Integer getResultSize() {
        return resultSize;
    }

    public void setResultSize(Integer resultSize) {
        this.resultSize = resultSize;
    }

    public void setSortOptions(SortOptions sortOptions) {
        this.sortOptions = sortOptions;
    }

    public void addSortOption(SortOption sortOption) {
        this.sortOptions.add(sortOption);
    }

    @Override
    public SortOptions getSortOptions() {
        return this.sortOptions;
    }

    @Override
    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public SearchParameters getSearchParameters() {
        return this.searchParameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public void setSpatialFilter(Double westernLongitude, Double easternLongitude, Double southernLatitude, Double northernLatitude) {
        this.spatialFilter = new SpatialFilter(westernLongitude, easternLongitude, southernLatitude, northernLatitude);
    }

    public SpatialFilter getSpatialFilter() {
        return this.spatialFilter;
    }

    public void setTemporalFilter(String startDate, String endDate, DateFormat dateFormat) {
        this.temporalFilter = new TemporalFilter(startDate, endDate, dateFormat);
    }

    public TemporalFilter getTemporalFilter() {
        return this.temporalFilter;
    }
}
