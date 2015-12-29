package sgf.gateway.search.core;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import sgf.gateway.search.api.Result;
import sgf.gateway.search.api.Results;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

// based on the request parameters, return the appropriate model for the page you are looking at
//Uses HttpRequest, used by controller
public class PagingResultsImpl extends ArrayList<Result> implements PaginatedList, Results {

    private static final long serialVersionUID = 1L;

    /**
     * Set the default page size *
     */
    int DEFAULT_PAGE_SIZE = 25;

    public static final String SORT_PARAM = "sort";  // Sort by this field
    public static final String PAGE_PARAM = "page";  // Which page
    public static final String DIRECTION_PARAM = "dir";  // e.g ASCENDING
    public static final String ASC = "asc";  // e.g ASCENDING
    public static final String DESC = "desc";  // e.g DECENDING

    /**
     * number of results per page (number of rows per page to be displayed )
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * total number of results (the total number of rows  )
     */
    private int fullListSize;

    /**
     * current page index, starts at 0
     */
    private int index;

    /**
     * list of results (rows found ) in the current page
     */
    private Results list;

    /**
     * default sorting order
     */
    private SortOrderEnum sortDirection = SortOrderEnum.ASCENDING;

    /**
     * sort criteria (sorting property name)
     */
    private String sortCriterion;

    /**
     * Http servlet request *
     */
    private HttpServletRequest request;

    /**
     * default constructor *
     */
    public PagingResultsImpl() {
    }

    /**
     * Create <code>PagingResultsImpl</code> instance using the <code>HttpServletRequest</code> object.
     *
     * @param request    <code>HttpServletRequest</code> object.
     * @param rowPerPage the page size - the total number of rows per page.
     */
    public PagingResultsImpl(HttpServletRequest request, int rowsPerPage) {

        this.sortCriterion = request.getParameter(SORT_PARAM); // setters
        this.sortDirection = SortOrderEnum.DESCENDING; // (get from request.getParameter(DIRECTION_PARAM), then set direction)

        // Set # rows per page
        if (rowsPerPage != 0) {
            setPageSize(rowsPerPage);   //setter
        }

        // which page we are on
        String page = request.getParameter(PAGE_PARAM);

        // Set index based on page
        if (null == page) {
            setIndex(0);  // first
        } else {
            setIndex(Integer.parseInt(page) - 1);
        }

    }

    /*
     * Create PagingResultsImpl instance with pageSize Factory
     */
    public PagingResultsImpl getPaginatedListObject(int pageSize) throws Exception {
        if (request == null) {
            throw new Exception("Cannot create paginated list - Needs HttpServletRequest.");
        }

        return new PagingResultsImpl(this.request, pageSize);

    }

    /*
     * Create PagingResultsImpl instance with request Factory.
     * Turn request params into PaginatedList (PagingResultsImpl) that just needs Results and total.
     */
    public PagingResultsImpl getPaginatedListObject(HttpServletRequest request) throws Exception {
        if (request == null) {
            throw new Exception("Cannot create paginated list - Needs HttpServletRequest.");
        }

        return new PagingResultsImpl(request, this.pageSize);

    }

    /*
     * Factory to Create PagingResultsImpl instance with request and pageSize
     */
    public static PagingResultsImpl getPaginatedListObject(HttpServletRequest request, int rowsPerPage) throws Exception {
        if (request == null) {
            throw new Exception("Cannot create paginated list - Needs HttpServletRequest.");
        }

        //return new PagingResultsImpl(request, rowsPerPage);

        PagingResultsImpl pri = new PagingResultsImpl();


        pri.setSortCriterion(request.getParameter(SORT_PARAM));
        pri.setSortDirection(SortOrderEnum.DESCENDING); // (get from request.getParameter(DIRECTION_PARAM), then set direction)

        // Set # rows per page
        if (rowsPerPage != 0) {
            pri.setPageSize(rowsPerPage);
        }

        // which page we are on - used to set index
        String page = request.getParameter(PAGE_PARAM);

        // Set index based on page
        if (null == page) {
            pri.setIndex(0);  // first
        } else {
            pri.setIndex(Integer.parseInt(page) - 1);
        }

        return pri;
    }


    //Returns the size of the full list
    @Override
    public int getFullListSize() {
        return this.fullListSize;
    }


    public void setFullListSize(int fullListSize) {
        this.fullListSize = fullListSize;
    }

    //Returns the current partial list
    @Override
    public Results getList() {
        return this.list;
    }

    public void setList(Results list) {
        this.list = list;
    }

    //Returns the number of objects per page.
    @Override
    public int getObjectsPerPage() {
        return this.pageSize;
    }

    // Returns the page number of the partial list (starts from 1)
    @Override
    public int getPageNumber() {
        return this.index + 1;

    }

    // Returns an ID for the search used to get the list.
    //This is required, if we want the ID to be included in the paginated purpose.
    @Override
    public String getSearchId() {
        return null;
    }

    //Returns the sort criterion used to externally sort the full list
    @Override
    public String getSortCriterion() {
        return this.sortCriterion;
    }

    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }


    //Returns the sort direction used to externally sort the full list
    @Override
    public SortOrderEnum getSortDirection() {
        return this.sortDirection;
    }

    public void setSortDirection(SortOrderEnum sortDirection) {
        this.sortDirection = sortDirection;
    }


    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
