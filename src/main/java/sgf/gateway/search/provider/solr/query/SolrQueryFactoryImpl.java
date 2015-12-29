package sgf.gateway.search.provider.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.provider.solr.query.configurer.SolrQueryConfigurer;

import java.util.List;

public class SolrQueryFactoryImpl implements SolrQueryFactory {

    private final List<SolrQueryConfigurer> configurers;

    public SolrQueryFactoryImpl(List<SolrQueryConfigurer> configurers) {
        super();
        this.configurers = configurers;
    }

    @Override
    public SolrQuery createQuery(Criteria criteria) {

        SolrQuery query = new SolrQuery();
        configureQuery(query, criteria);

        return query;
    }

    protected void configureQuery(SolrQuery query, Criteria criteria) {
        for (SolrQueryConfigurer configurer : configurers) {
            configurer.configure(query, criteria);
        }
    }
}
