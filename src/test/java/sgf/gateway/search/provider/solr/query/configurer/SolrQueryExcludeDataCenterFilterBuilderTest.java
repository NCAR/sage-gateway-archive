package sgf.gateway.search.provider.solr.query.configurer;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import sgf.gateway.search.core.CriteriaImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

public class SolrQueryExcludeDataCenterFilterBuilderTest {

    private String datacenterField = "datacenter";

    @Test
    public void testExcludeNoDatacenter() {

        SolrQueryExcludeDataCenterFilterBuilder builder = new SolrQueryExcludeDataCenterFilterBuilder(this.datacenterField, new ArrayList<String>());

        String filter = builder.build(new CriteriaImpl());

        assertThat(filter, IsNull.nullValue());
    }

    @Test
    public void testExcludeSingleDatacenter() {

        List<String> excludedDatacenters = new ArrayList<String>();
        excludedDatacenters.add("dc1");

        SolrQueryExcludeDataCenterFilterBuilder builder = new SolrQueryExcludeDataCenterFilterBuilder(this.datacenterField, excludedDatacenters);

        String filter = builder.build(new CriteriaImpl());

        assertThat(filter, IsEqual.equalTo("-datacenter:dc1"));
    }

    @Test
    public void testExcludeSingleDatacenterNeedingEscape() {

        List<String> excludedDatacenters = new ArrayList<String>();
        excludedDatacenters.add("dc 1");

        SolrQueryExcludeDataCenterFilterBuilder builder = new SolrQueryExcludeDataCenterFilterBuilder(this.datacenterField, excludedDatacenters);

        String filter = builder.build(new CriteriaImpl());

        assertThat(filter, IsEqual.equalTo("-datacenter:dc\\ 1"));
    }

    @Test
    public void testExcludeMultipleDatacenters() {

        List<String> excludedDatacenters = new ArrayList<String>();
        excludedDatacenters.add("dc1");
        excludedDatacenters.add("dc2");
        excludedDatacenters.add("dc 3");

        SolrQueryExcludeDataCenterFilterBuilder builder = new SolrQueryExcludeDataCenterFilterBuilder(this.datacenterField, excludedDatacenters);

        String filter = builder.build(new CriteriaImpl());

        assertThat(filter, IsEqual.equalTo("-(datacenter:dc1 OR datacenter:dc2 OR datacenter:dc\\ 3)"));
    }
}
