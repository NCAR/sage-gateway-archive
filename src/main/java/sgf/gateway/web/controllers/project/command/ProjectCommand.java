package sgf.gateway.web.controllers.project.command;

import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.validation.data.*;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.AssertUniqueShortName;
import sgf.gateway.validation.persistence.AssertUniqueTitle;

import javax.validation.GroupSequence;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, ProjectCommand.class})

@ValidTemporalBounds(groups = Data.class, startDateField = "startDate", endDateField = "endDate", format = "yyyy-MM-dd", message = "Start Date must be before End Date.")
@ValidLatitudeSpatialBounds(groups = Data.class, northernLatitudeField = "northernLatitudeString", southernLatitudeField = "southernLatitudeString", message = "North Latitude must be greater than South Latitude.")
//@ProjectNameCharacters (groups=Data.class, nameField="title")
@ProjectShortNameCharacters(groups = Data.class, shortNameField = "shortName")
@AssertUniqueTitle(groups = Persistence.class, identifierField = "identifier", nameField = "title")
@AssertUniqueShortName(groups = Persistence.class, identifierField = "identifier", shortNameField = "shortName")
public class ProjectCommand {

    private String identifier;

    @NotBlank(groups = Required.class, message = "Short Name is required.")
    private String shortName;

    DateFormat expectedDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @NotBlank(groups = Required.class, message = "Title is required")
    private String title;

    @NotBlank(groups = Required.class, message = "Description is required")
    private String description;

    @NotBlank(groups = Required.class, message = "Start Date is required")
    @ValidDate(groups = Type.class, format = "yyyy-MM-dd", message = "Start Date must use the following format: YYYY-MM-DD")
    private String startDate;

    @ValidDate(groups = Type.class, format = "yyyy-MM-dd", message = "End Date must use the following format: YYYY-MM-DD")
    private String endDate;

    private String projectGroup;
    private String dataCenterName;

    @NotBlank(groups = Required.class, message = "Northern Latitude is required.")
    @ValidDecimal(groups = Type.class, message = "Northern Latitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-90", upper = "90", message = "Northern Latitude must be between 90 and -90.")
    private String northernLatitudeString;

    @NotBlank(groups = Required.class, message = "Southern Latitude is required.")
    @ValidDecimal(groups = Type.class, message = "Southern Latitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-90", upper = "90", message = "Southern Latitude must be between 90 and -90.")
    private String southernLatitudeString;

    @NotBlank(groups = Required.class, message = "Western Longitude is required.")
    @ValidDecimal(groups = Type.class, message = "Western Longitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-180", upper = "180", message = "Western Longitude must be between 180 and -180.")
    private String westernLongitudeString;

    @NotBlank(groups = Required.class, message = "Eastern Longitude is required.")
    @ValidDecimal(groups = Type.class, message = "Eastern Longitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-180", upper = "180", message = "Eastern Longitude must be between 180 and -180.")
    private String easternLongitudeString;

    public ProjectCommand() {

    }

    public ProjectCommand(Dataset dataset) {

        this.identifier = dataset.getIdentifier().toString();
        this.shortName = dataset.getShortName();
        this.title = dataset.getTitle();
        this.description = dataset.getDescription();

        DescriptiveMetadata metadata = dataset.getDescriptiveMetadata();

        this.startDate = this.expectedDateFormat.format(metadata.getTimePeriod().getBegin());

        if (metadata.getTimePeriod().getEnd() != null) {
            this.endDate = this.expectedDateFormat.format(metadata.getTimePeriod().getEnd());
        }

        this.northernLatitudeString = metadata.getGeographicBoundingBox().getNorthBoundLatitude().toString();
        this.southernLatitudeString = metadata.getGeographicBoundingBox().getSouthBoundLatitude().toString();
        this.westernLongitudeString = metadata.getGeographicBoundingBox().getWestBoundLongitude().toString();
        this.easternLongitudeString = metadata.getGeographicBoundingBox().getEastBoundLongitude().toString();

        this.projectGroup = dataset.getProjectGroup();
    }

    public String getIdentifier() {

        return this.identifier;
    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }


    public String getShortName() {

        return this.shortName;
    }

    public void setShortName(String shortName) {

        if (shortName != null) {

            this.shortName = convertSpacesToUnderscores(shortName);

        } else {

            this.shortName = shortName;
        }
    }

    private String convertSpacesToUnderscores(String original) {
        return original.replaceAll(" ", "_");
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {

        this.description = description;
    }

    public String getProjectGroup() {

        return this.projectGroup;
    }

    public void setProjectGroup(String projectGroup) {

        this.projectGroup = projectGroup;
    }

    // START/END DATES

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

    // LAT/LONG
    public Double getNorthernLatitude() {

        return Double.valueOf(this.northernLatitudeString);
    }

    public void setNorthernLatitude(Double northLat) {

        this.northernLatitudeString = northLat.toString();
    }

    public String getNorthernLatitudeString() {

        return this.northernLatitudeString;
    }

    public void setNorthernLatitudeString(String northernLatitude) {

        this.northernLatitudeString = northernLatitude;
    }


    public Double getSouthernLatitude() {

        return Double.valueOf(this.southernLatitudeString);
    }

    public void setSouthernLatitude(Double southLat) {

        this.southernLatitudeString = southLat.toString();
    }

    public String getSouthernLatitudeString() {

        return this.southernLatitudeString;
    }

    public void setSouthernLatitudeString(String southernLatitude) {

        this.southernLatitudeString = southernLatitude;
    }

    public Double getWesternLongitude() {

        return Double.valueOf(this.westernLongitudeString);
    }

    public void setWesternLongitude(Double westLong) {

        this.westernLongitudeString = westLong.toString();
    }

    public String getWesternLongitudeString() {

        return this.westernLongitudeString;
    }

    public void setWesternLongitudeString(String westernLongitude) {

        if (westernLongitude != null) {

            westernLongitude = westernLongitude.trim();
        }

        this.westernLongitudeString = westernLongitude;
    }

    public void setEasternLongitude(Double eastLong) {

        this.easternLongitudeString = eastLong.toString();
    }

    public Double getEasternLongitude() {

        return Double.valueOf(this.easternLongitudeString);
    }

    public String getEasternLongitudeString() {

        return this.easternLongitudeString;
    }

    public void setEasternLongitudeString(String easternLongitude) {

        this.easternLongitudeString = easternLongitude;
    }

    public String getDataCenterName() {
        return this.dataCenterName;
    }

    public void setDataCenterName(String dataCenterName) {
        this.dataCenterName = dataCenterName;
    }

}
