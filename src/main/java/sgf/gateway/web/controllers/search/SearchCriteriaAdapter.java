package sgf.gateway.web.controllers.search;

import sgf.gateway.search.api.*;
import sgf.gateway.search.core.*;
import sgf.gateway.web.controllers.search.command.SearchCommand;

public class SearchCriteriaAdapter implements Criteria {

    private final SearchCommand searchCommand;
    private final SearchParameters searchParameters = new SearchParametersImpl();

    public SearchCriteriaAdapter(SearchCommand searchForm) {
        super();
        this.searchCommand = searchForm;
    }

    @Override
    public String getFreeText() {
        return this.searchCommand.getFreeText();
    }

    @Override
    public Facets getFacets() {
        Facets facets = this.searchCommand.getFacets();
        return facets;
    }

    @Override
    public Integer getResultSize() {
        return this.searchCommand.getResultSize();
    }

    @Override
    public Integer getStartIndex() {
        return this.searchCommand.getStartIndex();
    }

    @Override
    public SortOptions getSortOptions() {

        SortOptions sortOptions;

        if (null == this.searchCommand.getSortOptions()) {
            sortOptions = defaultSortOptions();
        } else {
            sortOptions = this.searchCommand.getSortOptions();
        }

        return sortOptions;
    }

    @Override
    public SearchParameters getSearchParameters() {
        return this.searchParameters;
    }

    private SortOptions defaultSortOptions() {

        SortOptions sortOptions = new SortOptionsImpl();

        SortOption sortOptionScore = new SortOptionImpl();
        sortOptionScore.setName("Score");
        sortOptionScore.setDescending(true);

        SortOption sortOptionName = new SortOptionImpl();
        sortOptionName.setName("Name");
        sortOptionName.setDescending(false);

        sortOptions.add(sortOptionScore);
        sortOptions.add(sortOptionName);

        return sortOptions;
    }

    @Override
    public SpatialFilter getSpatialFilter() {
        return this.searchCommand.getSpatialFilter();
    }

    @Override
    public TemporalFilter getTemporalFilter() {
        return this.searchCommand.getTemporalFilter();
    }
}
