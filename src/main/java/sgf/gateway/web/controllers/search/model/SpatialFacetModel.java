package sgf.gateway.web.controllers.search.model;

import com.kennardconsulting.core.net.UrlEncodedQueryString;

import java.net.URI;

public class SpatialFacetModel {

    private final String northernLatitude;
    private final String easternLongitude;
    private final String southernLatitude;
    private final String westernLongitude;

    private URI searchPath;

    public SpatialFacetModel(final URI searchPath, final String northernLatitude, final String easternLongitude, final String southernLatitude, final String westernLongitude) {

        this.searchPath = searchPath;
        this.northernLatitude = northernLatitude;
        this.easternLongitude = easternLongitude;
        this.southernLatitude = southernLatitude;
        this.westernLongitude = westernLongitude;
    }

    public String getRemovalUrl() {

        UrlEncodedQueryString queryString = UrlEncodedQueryString.parse(this.searchPath);

        queryString.remove("northernLatitude");
        queryString.remove("easternLongitude");
        queryString.remove("southernLatitude");
        queryString.remove("westernLongitude");

        // Need a better way to do this!, since this reruns search all pagination information should be reset
        queryString.remove("page");

        String url = queryString.apply(this.searchPath).toString();

        return url;
    }

    public String getNorthernLatitude() {

        return this.northernLatitude;
    }

    public String getEasternLongitude() {

        return this.easternLongitude;
    }

    public String getSouthernLatitude() {

        return this.southernLatitude;
    }

    public String getWesternLongitude() {

        return this.westernLongitude;
    }
}
