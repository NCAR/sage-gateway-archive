package sgf.gateway.search.provider.solr.query.configurer;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.core.CriteriaImpl;

import static org.junit.Assert.assertThat;


public class SolrQuerySpatialFilterBuilderTest {


    private SolrQuerySpatialFilterBuilder builder;

    @Before
    public void setUp() throws Exception {
        this.builder = new SolrQuerySpatialFilterBuilder("southernlat", "northernlat", "westernlon", "easternlon");
    }

    @Test
    public void filterBoundedByReducedLongitudes() {

        Criteria criteria = this.createCriteriaWithSpatialFilter("-30.0", "45.0", "60.0", "85.0");

        String filter = builder.build(criteria);

        assertThat(filter, Is.is("southernlat:{* TO 85.0} AND northernlat:{60.0 TO *} AND westernlon:{* TO 45.0} AND easternlon:{-30.0 TO *}"));
    }

    @Test
    public void filterBoundedByLongitudesNeedingReduction() {

        Criteria criteria = this.createCriteriaWithSpatialFilter("200.0", "300.0", "60.0", "85.0");

        String filter = builder.build(criteria);

        assertThat(filter, Is.is("southernlat:{* TO 85.0} AND northernlat:{60.0 TO *} AND westernlon:{* TO -60.0} AND easternlon:{-160.0 TO *}"));
    }

    @Test
    public void filterBoundedByDatelineStraddlingLongitudes() {

        Criteria criteria = this.createCriteriaWithSpatialFilter("150.0", "-10.0", "60.0", "85.0");

        String filter = builder.build(criteria);

        assertThat(filter, Is.is("southernlat:{* TO 85.0} AND northernlat:{60.0 TO *} AND westernlon:{* TO 350.0} AND easternlon:{150.0 TO *}"));
    }

    private Criteria createCriteriaWithSpatialFilter(String westernLon, String easternLon, String southernLat, String northernLat) {

        Double westernLongitude = new Double(westernLon);
        Double easternLongitude = new Double(easternLon);
        Double southernLatitude = new Double(southernLat);
        Double northernLatitude = new Double(northernLat);

        CriteriaImpl criteria = new CriteriaImpl();
        criteria.setSpatialFilter(westernLongitude, easternLongitude, southernLatitude, northernLatitude);

        return criteria;
    }

}
