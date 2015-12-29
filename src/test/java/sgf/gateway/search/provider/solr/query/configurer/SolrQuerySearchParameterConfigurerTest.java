package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Operation;
import sgf.gateway.search.api.SearchParameter;
import sgf.gateway.search.api.SearchParameters;
import sgf.gateway.search.core.CriteriaImpl;
import sgf.gateway.search.core.SearchParameterImpl;
import sgf.gateway.search.core.SearchParametersImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SolrQuerySearchParameterConfigurerTest {

    private SolrQuerySearchParameterConfigurer configurer;

    @Before
    public void setUp() throws Exception {
        this.configurer = new SolrQuerySearchParameterConfigurer(Operation.AND);
    }

    @Test
    public void absentParameters() {

        SolrQuery solrQuery = mock(SolrQuery.class);

        this.configurer.configure(solrQuery, this.getEmptyCriteria());

        verify(solrQuery).setQuery("");
    }

    @Test
    public void singleParameter() {

        SolrQuery solrQuery = mock(SolrQuery.class);

        this.configurer.configure(solrQuery, this.getSingleParameterCriteria("id", "007"));

        verify(solrQuery).setQuery("id:007");
    }

    @Test
    public void singleParameterNeedingEscaping() {

        SolrQuery solrQuery = mock(SolrQuery.class);

        this.configurer.configure(solrQuery, this.getSingleParameterCriteria("doi", "doi:007"));

        verify(solrQuery).setQuery("doi:doi\\:007");
    }

    @Test
    public void multipleParameters() {

        SolrQuery solrQuery = mock(SolrQuery.class);

        SearchParameters parameters = this.addSearchParameter("author", "J. Cunning", null);
        this.addSearchParameter("region", "Yukon Territory", parameters);
        Criteria criteria = this.getCriteria(parameters);

        this.configurer.configure(solrQuery, criteria);

        verify(solrQuery).setQuery("author:J.\\ Cunning AND region:Yukon\\ Territory");
    }

    private Criteria getEmptyCriteria() {
        return new CriteriaImpl();
    }

    private Criteria getCriteria(SearchParameters parameters) {

        CriteriaImpl criteria = new CriteriaImpl();
        criteria.setSearchParameters(parameters);

        return criteria;
    }

    private Criteria getSingleParameterCriteria(String name, String value) {

        CriteriaImpl criteria = new CriteriaImpl();

        criteria.setSearchParameters(this.addSearchParameter(name, value, null));

        return criteria;
    }


    private SearchParameters addSearchParameter(String name, String value, SearchParameters parameters) {

        if (parameters == null) {
            parameters = new SearchParametersImpl();
        }

        SearchParameter parameter = new SearchParameterImpl(name, value);
        parameters.add(parameter);

        return parameters;
    }
}
