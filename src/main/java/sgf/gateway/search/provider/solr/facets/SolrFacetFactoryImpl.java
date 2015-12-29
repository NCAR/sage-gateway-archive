package sgf.gateway.search.provider.solr.facets;

import org.apache.solr.client.solrj.response.FacetField;
import sgf.gateway.search.api.Constraint;
import sgf.gateway.search.api.Constraints;
import sgf.gateway.search.api.Facet;
import sgf.gateway.search.core.ConstraintImpl;
import sgf.gateway.search.core.ConstraintsImpl;
import sgf.gateway.search.core.FacetImpl;

import java.util.Map;

public class SolrFacetFactoryImpl implements SolrFacetFactory {

    private final Map<String, String> facetMap;

    public SolrFacetFactoryImpl(Map<String, String> facetMap) {
        super();
        this.facetMap = facetMap;
    }

    @Override
    public Facet build(FacetField facetField) {

        String facetName = this.facetMap.get(facetField.getName());

        Facet facet = new FacetImpl(facetName);

        Constraints constraints = new ConstraintsImpl();

        for (FacetField.Count fieldDetail : facetField.getValues()) {

            Constraint constraint = new ConstraintImpl(facet, fieldDetail.getName(), null, fieldDetail.getCount());

            constraints.add(constraint);
        }

        facet.setConstraints(constraints);

        return facet;
    }
}
