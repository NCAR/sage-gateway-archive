package sgf.gateway.search.service;

import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.search.api.*;
import sgf.gateway.search.core.CriteriaImpl;
import sgf.gateway.search.core.SearchParameterImpl;
import sgf.gateway.search.core.SearchParametersImpl;

import java.util.ArrayList;
import java.util.List;


public class MoreLikeThisServiceImpl implements MoreLikeThisService {

    private final SearchQuery mltDatasetQuery;
    private final String idFieldName;

    public MoreLikeThisServiceImpl(SearchQuery mltDatasetQuery, String idFieldName) {
        super();
        this.mltDatasetQuery = mltDatasetQuery;
        this.idFieldName = idFieldName;
    }

    @Override
    public Results moreLikeThis(Dataset dataset) {

        Criteria criteria = this.getMoreLikeThisDatasetSearchCriteria(dataset);
        SearchResult searchResult = this.mltDatasetQuery.execute(criteria);
        Results results = searchResult.getMoreLikeThisResults();

        return results;
    }

    private Criteria getMoreLikeThisDatasetSearchCriteria(Dataset dataset) {

        CriteriaImpl criteria = new CriteriaImpl();

        criteria.setSearchParameters(this.createSearchParameters(dataset));
        criteria.setResultSize(1);

        return criteria;
    }

    private SearchParameters createSearchParameters(Dataset dataset) {

        List<SearchParameter> searchParameters = new ArrayList<SearchParameter>();

        searchParameters.add(new SearchParameterImpl(this.idFieldName, dataset.getIdentifier().toString()));

        return new SearchParametersImpl(searchParameters);
    }
}
