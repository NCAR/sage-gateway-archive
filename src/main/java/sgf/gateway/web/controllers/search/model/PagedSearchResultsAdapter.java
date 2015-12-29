package sgf.gateway.web.controllers.search.model;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import sgf.gateway.search.api.SearchResult;

import java.util.List;

public class PagedSearchResultsAdapter implements PaginatedList {

    private SearchResult searchResult;
    private int resultsPerPage;

    protected PagedSearchResultsAdapter(int resultsPerPage, SearchResult searchResult) {
        super();
        this.searchResult = searchResult;
        this.resultsPerPage = resultsPerPage;
    }

    public static PagedSearchResultsAdapter adapt(int resultsPerPage, SearchResult searchResult) {

        return new PagedSearchResultsAdapter(resultsPerPage, searchResult);
    }

    @Override
    public int getFullListSize() {

        return this.searchResult.getResultCount().intValue();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getList() {
        return this.searchResult.getResults();
    }

    @Override
    public int getObjectsPerPage() {
        return this.resultsPerPage;
    }

    @Override
    public int getPageNumber() {
        int page;

        try {
            page = (this.searchResult.getCriteria().getStartIndex() / this.resultsPerPage + 1);
        } catch (ArithmeticException aex) {
            //No results, divide by 0 exception
            page = 0;
        }

        return page;
    }

    @Override
    public String getSearchId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSortCriterion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SortOrderEnum getSortDirection() {
        // TODO Auto-generated method stub
        return null;
    }

}
