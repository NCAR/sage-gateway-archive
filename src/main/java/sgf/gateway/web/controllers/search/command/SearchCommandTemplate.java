package sgf.gateway.web.controllers.search.command;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.EnumerationUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.util.StringUtils;
import sgf.gateway.search.api.Facet;
import sgf.gateway.search.api.Facets;
import sgf.gateway.search.api.SortOption;
import sgf.gateway.search.api.SortOptions;
import sgf.gateway.search.core.FacetsImpl;
import sgf.gateway.web.controllers.search.model.SelectedFacetFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class SearchCommandTemplate implements SearchCommand {

    public static final String PAGE_PARAM_NAME = "page";

    public static final String SORT_PARAM_NAME = "sort";

    public static final String RPP_PARAM_NAME = "rpp";

    protected final URI searchPath;

    private final List<String> facetNames;

    protected final SelectedFacetFactory selectedFacetFactory;

    protected Facets facets = new FacetsImpl();

    protected Integer resultSize = 20;

    protected Integer startIndex;

    protected Integer page;

    protected SortOptions sortOptions;

    public SearchCommandTemplate(URI searchPath, List<String> facetNames) {
        this.searchPath = searchPath;
        this.facetNames = facetNames;
        this.selectedFacetFactory = new SelectedFacetFactory();
    }

    @Override
    public void populateFacets(HttpServletRequest request) {

        Collection<String> facetParameterNames = this.getFacetParameterNames(request);

        for (String parameterName : facetParameterNames) {

            List<String> parameterValues = Arrays.asList(request.getParameterValues(parameterName));
            List<String> constraintValues = new ArrayList<String>();

            for (String value : parameterValues) {
                if (StringUtils.hasText(value)) {
                    constraintValues.add(value);
                }
            }

            if (!constraintValues.isEmpty()) {
                Facet selectedFacet = this.selectedFacetFactory.build(parameterName, constraintValues);
                this.facets.add(selectedFacet);
            }
        }
    }

    protected Collection<String> getFacetParameterNames(HttpServletRequest request) {

        List<String> parameterNames = this.getParameterNames(request);
        List<String> facetNames = this.getFacetNames();
        List<String> facetParameterNames = this.filterNames(parameterNames, facetNames);

        return facetParameterNames;
    }

    protected List<String> getParameterNames(HttpServletRequest request) {

        List<String> parameterNames = EnumerationUtils.toList(request.getParameterNames());

        return parameterNames;
    }

    protected List<String> getFacetNames() {
        return this.facetNames;
    }

    protected List<String> filterNames(Collection<String> input, final Collection<String> filter) {

        Collection output = CollectionUtils.select(input, new Predicate() {

            @Override
            public boolean evaluate(Object parameterName) {
                return filter.contains(parameterName);
            }
        });

        return new ArrayList<String>(output);
    }

    @Override
    public Facets getFacets() {
        return this.facets;
    }

    public URI getSearchPath() {
        return this.searchPath;
    }

    public String getSearchURI() {
        return this.getSearchPath().getPath();
    }

    @Override
    public Integer getResultSize() {
        return this.resultSize;
    }

    @Override
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

        Integer result = 0;

        if (null != this.startIndex) {

            result = this.startIndex;

        } else if (null != this.page) {

            result = (this.page - 1) * this.resultSize;

        }

        return result;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRpp() {
        return this.getResultSize().intValue();
    }

    public void setRpp(Integer rpp) {
        this.setResultSize(rpp);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
