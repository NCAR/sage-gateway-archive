package sgf.gateway.search.provider.solr.query.configurer;

import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.Operation;
import sgf.gateway.search.core.SpatialFilter;
import sgf.gateway.search.extract.GeoBoundingBoxTransform;

import java.util.ArrayList;
import java.util.List;

public class SolrQuerySpatialFilterBuilder extends SolrQueryFilterBuilderAbstract {

    private final String southernLatitudeFieldName;
    private final String northernLatitudeFieldName;
    private final String westernLongitudeFieldName;
    private final String easternLongitudeFieldName;

    public SolrQuerySpatialFilterBuilder(String southernLatitudeFieldName, String northernLatitudeFieldName, String westernLongitudeFieldName, String easternLongitudeFieldName) {
        this.southernLatitudeFieldName = southernLatitudeFieldName;
        this.northernLatitudeFieldName = northernLatitudeFieldName;
        this.westernLongitudeFieldName = westernLongitudeFieldName;
        this.easternLongitudeFieldName = easternLongitudeFieldName;
    }

    @Override
    public String build(Criteria criteria) {

        String filter = null;

        if (null != criteria.getSpatialFilter()) {
            filter = this.buildSpatialFilter(criteria.getSpatialFilter());
        }

        return filter;
    }

    private String buildSpatialFilter(SpatialFilter spatialFilter) {

        Double westernLongitude = GeoBoundingBoxTransform.reduceWesternLongitude(spatialFilter.getWesternLongitude());
        Double easternLongitude = GeoBoundingBoxTransform.reduceEasternLongitude(spatialFilter.getEasternLongitude());

        if (GeoBoundingBoxTransform.boundingBoxStraddlesDateline(westernLongitude, easternLongitude)) {
            easternLongitude = GeoBoundingBoxTransform.extendEasterly(easternLongitude);
        }

        List<String> constraints = new ArrayList<String>(4);

        // note curly backets are exclusive

        // dataset south latitude must be strictly less than parameter north latitude
        constraints.add(southernLatitudeFieldName + ":{* TO " + spatialFilter.getNorthernLatitude() + "}");

        // dataset north latitude must be strictly greater than parameter south latitude
        constraints.add(northernLatitudeFieldName + ":{" + spatialFilter.getSouthernLatitude() + " TO *}");

        // dataset west longitude must be strictly less than parameter east longitude
        constraints.add(westernLongitudeFieldName + ":{* TO " + easternLongitude + "}");

        // dataset east longitude must be strictly greater than parameter west longitude
        constraints.add(easternLongitudeFieldName + ":{" + westernLongitude + " TO *}");

        String spatialFieldQuery = join(constraints, Operation.AND);

        return spatialFieldQuery;
    }
}
