package sgf.gateway.web.controllers.search.command;

import org.springframework.util.StringUtils;
import sgf.gateway.search.core.SpatialFilter;
import sgf.gateway.search.core.TemporalFilter;
import sgf.gateway.validation.data.*;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, SearchCommandGeographic.class})
@ValidTemporalBounds(groups = Data.class, startDateField = "startDate", endDateField = "endDate", format = SearchCommandGeographic.DATE_FORMAT)
@ValidSpatialBounds(groups = Required.class, northernLatitudeField = "northernLatitude", southernLatitudeField = "southernLatitude", easternLongitudeField = "easternLongitude", westernLongitudeField = "westernLongitude")
@ValidLatitudeSpatialBounds(groups = Data.class, northernLatitudeField = "northernLatitude", southernLatitudeField = "southernLatitude")
@ValidLongitudeSpatialBounds(groups = Data.class, easternLongitudeField = "easternLongitude", westernLongitudeField = "westernLongitude")
public class SearchCommandGeographic extends SearchCommandFreeText {

    protected static final String WESTERN_LONGITUDE = "westernLongitude";

    protected static final String EASTERN_LONGITUDE = "easternLongitude";

    protected static final String NORTHERN_LATITUDE = "northernLatitude";

    protected static final String SOUTHERN_LATITUDE = "southernLatitude";

    protected static final String START_DATE = "startDate";

    protected static final String END_DATE = "endDate";

    protected static final String DATE_FORMAT = "yyyy-MM-dd";

    @ValidDecimal(groups = Type.class)
    @ValidDecimalRange(groups = Data.class, lower = "-180", upper = "180", upperEndpointType = RangeEndpointType.EXCLUSIVE, message = "Western Longitude must be between 180 and -180.")
    protected String westernLongitude;

    @ValidDecimal(groups = Type.class)
    @ValidDecimalRange(groups = Data.class, lower = "-180", upper = "180", lowerEndpointType = RangeEndpointType.EXCLUSIVE, message = "Eastern Longitude must be between 180 and -180.")
    protected String easternLongitude;

    @ValidDecimal(groups = Type.class)
    @ValidDecimalRange(groups = Data.class, lower = "-90", upper = "90", lowerEndpointType = RangeEndpointType.EXCLUSIVE, message = "Northern Latitude must be between 90 and -90.")
    protected String northernLatitude;

    @ValidDecimal(groups = Type.class)
    @ValidDecimalRange(groups = Data.class, lower = "-90", upper = "90", upperEndpointType = RangeEndpointType.EXCLUSIVE, message = "Southern Latitude must be between 90 and -90.")
    protected String southernLatitude;

    @ValidDate(groups = Data.class, format = DATE_FORMAT, message = "Start Date must use the following format: YYYY-MM-DD")
    protected String startDate;

    @ValidDate(groups = Data.class, format = DATE_FORMAT, message = "End Date must use the following format: YYYY-MM-DD")
    protected String endDate;

    protected SpatialFilter spatialFilter;

    protected TemporalFilter temporalFilter;

    public SearchCommandGeographic(URI searchPath, List<String> facetNames) {
        super(searchPath, facetNames);
    }

    @Override
    public void populateFilters() {

        if (requiresSpatialFilter()) {
            populateSpatialFilter();
        }

        if (requiresTemporalFilter()) {
            populateTemporalFilter();
        }
    }

    public String getWesternLongitude() {
        return this.westernLongitude;
    }

    public void setWesternLongitude(String westernLongitude) {
        this.westernLongitude = westernLongitude;
    }

    public String getEasternLongitude() {
        return this.easternLongitude;
    }

    public void setEasternLongitude(String easternLongitude) {
        this.easternLongitude = easternLongitude;
    }

    public String getNorthernLatitude() {
        return this.northernLatitude;
    }

    public void setNorthernLatitude(String northernLatitude) {
        this.northernLatitude = northernLatitude;
    }

    public String getSouthernLatitude() {
        return this.southernLatitude;
    }

    public void setSouthernLatitude(String southernLatitude) {
        this.southernLatitude = southernLatitude;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    protected Boolean requiresSpatialFilter() {
        return StringUtils.hasText(this.southernLatitude) && StringUtils.hasText(this.northernLatitude) && StringUtils.hasText(this.westernLongitude)
                && StringUtils.hasText(this.easternLongitude);
    }

    protected void populateSpatialFilter() {
        SpatialFilter filter =
                new SpatialFilter(new Double(this.westernLongitude), new Double(this.easternLongitude), new Double(this.southernLatitude), new Double(this.northernLatitude));
        this.spatialFilter = filter;
    }

    protected Boolean requiresTemporalFilter() {
        return StringUtils.hasText(this.startDate) || StringUtils.hasText(this.endDate);
    }

    protected void populateTemporalFilter() {
        TemporalFilter filter = new TemporalFilter(this.startDate, this.endDate, new SimpleDateFormat(DATE_FORMAT));
        this.temporalFilter = filter;
    }

    @Override
    public SpatialFilter getSpatialFilter() {
        return this.spatialFilter;
    }

    @Override
    public TemporalFilter getTemporalFilter() {
        return this.temporalFilter;
    }
}
