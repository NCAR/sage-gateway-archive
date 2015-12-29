package sgf.gateway.search.provider.solr.query.configurer;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.core.CriteriaImpl;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertThat;


public class SolrQueryTemporalFilterBuilderTest {

    private SolrQueryTemporalFilterBuilder builder;

    @Before
    public void setUp() throws Exception {
        this.builder = new SolrQueryTemporalFilterBuilder("startdate", "enddate");
    }

    @Test
    public void filterBoundedByStartAndEndDates() {

        Criteria criteria = this.createCriteriaWithTemporalFilter("2001-01-01", "2004-01-01");

        String filter = builder.build(criteria);

        assertThat(filter, Is.is("startdate:{* TO 2004-01-01T00:00:00.000Z} AND enddate:{2001-01-01T00:00:00.000Z TO *}"));
    }

    @Test
    public void filterBoundedByStartDateOnly() {

        Criteria criteria = this.createCriteriaWithTemporalFilter("2001-01-01", null);

        String filter = builder.build(criteria);

        assertThat(filter, Is.is("enddate:{2001-01-01T00:00:00.000Z TO *}"));
    }

    @Test
    public void filterBoundedByEndDateOnly() {

        Criteria criteria = this.createCriteriaWithTemporalFilter(null, "2004-01-01");

        String filter = builder.build(criteria);

        assertThat(filter, Is.is("startdate:{* TO 2004-01-01T00:00:00.000Z}"));
    }

    private Criteria createCriteriaWithTemporalFilter(String startDate, String endDate) {

        CriteriaImpl criteria = new CriteriaImpl();
        criteria.setTemporalFilter(startDate, endDate, new SimpleDateFormat("yyyy-MM-dd"));

        return criteria;
    }
}
