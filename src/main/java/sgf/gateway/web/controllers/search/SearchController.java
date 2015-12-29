package sgf.gateway.web.controllers.search;


import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.search.api.*;
import sgf.gateway.search.core.SearchQueryException;
import sgf.gateway.search.core.SearchQuerySyntaxError;
import sgf.gateway.web.controllers.search.command.SearchCommand;
import sgf.gateway.web.controllers.search.command.SearchCommandGeographic;
import sgf.gateway.web.controllers.search.command.SearchCommandTemplate;
import sgf.gateway.web.controllers.search.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Controller for performing search using Criteria to be passed to a SearchProvider.
 * <p/>
 * Parameters may be submitted via a Search button to present free query text in addition to existing params. Or, select a link containing Facets and Constraints which accumulate
 * on successive requests. Link also includes Target (e.g. Datasets) and querytext params.
 * <p/>
 * Setup and Handler methods are both GET so that URL can be bookmarked/referenced.
 * <p/>
 * Maintains list of selected parameters in command object.
 * <p/>
 * Form backing object is SearchCommand, which is passed back and forth in the session to preserve previous request, etc. values.
 */
@Controller
public class SearchController {

    protected static final String SEARCH_COMMAND = "searchForm";
    protected static final String SEARCH_RESULT = "searchResults";

    private final SearchQuery searchQuery;
    private final String searchViewPath;
    private final List<String> facetNames;

    private List<Integer> resultsPerPageValues;

    public SearchController(SearchQuery searchQuery, String searchViewPath, List<String> facetNames) {
        this.searchQuery = searchQuery;
        this.searchViewPath = searchViewPath;
        this.facetNames = facetNames;
        this.resultsPerPageValues = new ArrayList<>();
    }

    public void setResultsPerPageValues(String valueList) {

        StringTokenizer st = new StringTokenizer(valueList, ",");

        while (st.hasMoreTokens()) {
            this.resultsPerPageValues.add(Integer.valueOf(st.nextToken()));
        }
    }

    @ModelAttribute(value = "searchForm")
    public SearchCommand createSearchCommand(HttpServletRequest request) {

        String searchRequest = request.getRequestURI();

        if (null != request.getQueryString()) {
            searchRequest += "?" + request.getQueryString();
        }

        SearchCommand command = new SearchCommandGeographic(URI.create(searchRequest), this.facetNames);

        // If this is a new search request
        if (!request.getParameterNames().hasMoreElements()) {
            command.setResultSize(0);
        }

        return command;
    }

    @RequestMapping(value = "/search/search-help", method = RequestMethod.GET)
    public ModelAndView searchHelp() throws Exception {
        return new ModelAndView("/search/search-help");
    }

    /**
     * Method called after form submission.
     * <p/>
     * Using Get rather than Post for visible URL to bookmark, etc.
     * <p/>
     * This method populates the command {@link SearchCommand} instance with the latest constraints from the HTTP request, invokes the {@link SearchProvider}, and returns the
     * {@link SearchCommand} to the view. {@link SearchCommand} is stored in session scope.
     * <p/>
     * ModelAttribute This is how the controller gets a reference to the object holding the data entered in the form.
     * <p/>
     * Call this when URL has a "target" param (differentiate from setupForm get method)
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView processSubmit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(SEARCH_COMMAND) @Valid SearchCommand searchCommand,
                                      BindingResult bindingResult, ModelMap model) throws SolrServerException {

        if (!bindingResult.hasErrors()) {
            searchCommand.populateFilters();
        }

        searchCommand.populateFacets(request);

        this.trySearchQuery(model, searchCommand, bindingResult);

        this.preventBrowserPageCaching(response);

        return new ModelAndView(this.searchViewPath, model);
    }

    protected void trySearchQuery(ModelMap model, SearchCommand searchCommand, BindingResult bindingResult) {

        try {
            this.populateModelAttributesPreQuery(model, searchCommand);
            SearchResult searchResult = this.executeSearchQuery(searchCommand);
            this.populateModelAttributesPostQuery(model, searchCommand, searchResult);
        } catch (SearchQueryException e) {
            if (e.getCause() instanceof SearchQuerySyntaxError) {
                bindingResult.reject("search.query.syntaxerror");
            } else {
                throw e;
            }
        }
    }

    protected SearchResult executeSearchQuery(SearchCommand searchCommand) {

        SearchCriteriaAdapter criteriaAdapter = new SearchCriteriaAdapter(searchCommand);

        SearchResult searchResult = this.searchQuery.execute(criteriaAdapter);

        return searchResult;
    }

    protected void populateModelAttributesPreQuery(ModelMap model, SearchCommand searchCommand) {

        List<FacetModel> selectedFacets = this.createFacetModel(searchCommand.getSearchPath(), searchCommand.getFacets());
        model.addAttribute("selectedFacets", selectedFacets);

        SpatialFacetModel spatialFacetModel = this.createSpatialFacetModel(searchCommand.getSearchPath(), searchCommand);
        model.addAttribute("spatialFacetModel", spatialFacetModel);

        TemporalFacetModel temporalFacetModel = this.createTemporalFacetModel(searchCommand.getSearchPath(), searchCommand);
        model.addAttribute("temporalFacetModel", temporalFacetModel);

        model.addAttribute("resultsPerPageOptions", this.getResultsOptions(searchCommand.getSearchPath()));

        model.addAttribute("title", this.getTitle(searchCommand));
        model.addAttribute("pageTitle", this.getPageTitle(searchCommand));
    }

    protected void populateModelAttributesPostQuery(ModelMap model, SearchCommand searchCommand, SearchResult searchResult) {

        List<FacetModel> availableFacets = this.createFacetModel(searchCommand.getSearchPath(), searchResult.getFacets());
        model.addAttribute("availableFacets", availableFacets);

        model.addAttribute(SEARCH_RESULT, searchResult);

        int resultsPerPage = searchCommand.getResultSize().intValue();

        PagedSearchResultsAdapter pagedSearchResults = PagedSearchResultsAdapter.adapt(resultsPerPage, searchResult);

        model.addAttribute("pagedSearchResults", pagedSearchResults);
        model.addAttribute("downloadableTargets", this.downloadableTargetsExist(pagedSearchResults));
    }

    protected void preventBrowserPageCaching(HttpServletResponse response) {
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, private");
        response.setDateHeader("Expires", 0);
    }

    protected String getPageTitle(SearchCommand searchCommand) {

        String pageTitle;

        if (searchCommand.getResultSize() == 0) {
            pageTitle = "Search";
        } else {
            pageTitle = "Search Results";
        }

        return pageTitle;
    }

    protected String getTitle(SearchCommand searchCommand) {

        List<String> titleComponents = new ArrayList<>();

        titleComponents.add("Search");

        String text = searchCommand.getFreeText();
        List<Facet> facets = searchCommand.getFacets();

        if (StringUtils.hasText(text) || !facets.isEmpty()) {

            titleComponents.add("-");

            if (StringUtils.hasText(text)) {
                titleComponents.add("Text: " + text.trim());
            }

            if (!facets.isEmpty()) {

                for (Facet facet : facets) {

                    String facetSnippet = facet.getName() + ": ";

                    for (Constraint constraint : facet.getConstraints()) {
                        facetSnippet += constraint.getName() + ", ";
                    }

                    titleComponents.add(facetSnippet.substring(0, facetSnippet.length() - 2));
                }
            }
        }

        String title = StringUtils.collectionToDelimitedString(titleComponents, " ");

        return title;
    }

    protected Boolean downloadableTargetsExist(PagedSearchResultsAdapter pagedSearchResults) {

        Boolean downloadable = false;

        List<Result> resultsOnPage = pagedSearchResults.getList();

        for (Result result : resultsOnPage) {

            if (result.isDownloadable()) {
                downloadable = true;
                break;
            }
        }

        return downloadable;
    }

    protected List<ResultsPerPageOption> getResultsOptions(URI searchPath) {

        List<ResultsPerPageOption> options = new ArrayList<>();

        for (Integer value : this.resultsPerPageValues) {
            options.add(new ResultsPerPageOption(SearchCommandTemplate.RPP_PARAM_NAME, value, searchPath));
        }

        return options;
    }

    protected List<FacetModel> createFacetModel(URI searchPath, Facets facets) {

        List<FacetModel> facetModels = new ArrayList<>();

        for (Facet facet : facets) {

            List<ConstraintModel> constraintModels = createConstraintModel(searchPath, facet);

            if (!constraintModels.isEmpty()) {
                FacetModel facetModel = new FacetModel(facet, constraintModels);
                facetModels.add(facetModel);
            }
        }

        return facetModels;
    }

    protected List<ConstraintModel> createConstraintModel(URI searchPath, Facet facet) {

        List<ConstraintModel> constraintModels = new ArrayList<>();

        for (Constraint constraint : facet.getConstraints()) {
            ConstraintModel constraintModel = new ConstraintModel(searchPath, facet, constraint);
            constraintModels.add(constraintModel);
        }

        return constraintModels;
    }

    protected SpatialFacetModel createSpatialFacetModel(URI searchPath, SearchCommand searchCommand) {

        SpatialFacetModel spatialFacetModle = null;

        if (searchCommand instanceof SearchCommandGeographic) {

            SearchCommandGeographic searchCommandGeographic = (SearchCommandGeographic) searchCommand;

            if (StringUtils.hasText(searchCommandGeographic.getNorthernLatitude()) || StringUtils.hasText(searchCommandGeographic.getEasternLongitude())
                    || StringUtils.hasText(searchCommandGeographic.getSouthernLatitude()) || StringUtils.hasText(searchCommandGeographic.getWesternLongitude())) {

                spatialFacetModle =
                        new SpatialFacetModel(searchPath, searchCommandGeographic.getNorthernLatitude(), searchCommandGeographic.getEasternLongitude(),
                                searchCommandGeographic.getSouthernLatitude(), searchCommandGeographic.getWesternLongitude());
            }
        }

        return spatialFacetModle;
    }

    protected TemporalFacetModel createTemporalFacetModel(URI searchPath, SearchCommand searchCommand) {

        TemporalFacetModel temporalFacetModel = null;

        if (searchCommand instanceof SearchCommandGeographic) {

            SearchCommandGeographic searchCommandGeographic = (SearchCommandGeographic) searchCommand;

            if (StringUtils.hasText(searchCommandGeographic.getStartDate()) || StringUtils.hasText(searchCommandGeographic.getEndDate())) {

                temporalFacetModel = new TemporalFacetModel(searchPath, searchCommandGeographic.getStartDate(), searchCommandGeographic.getEndDate());
            }
        }

        return temporalFacetModel;
    }
}