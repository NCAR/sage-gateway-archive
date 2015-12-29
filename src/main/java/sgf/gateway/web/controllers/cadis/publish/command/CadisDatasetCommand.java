package sgf.gateway.web.controllers.cadis.publish.command;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.CadisResolutionType;
import sgf.gateway.model.metadata.DataType;
import sgf.gateway.model.metadata.DatasetProgress;
import sgf.gateway.service.metadata.CadisDatasetDetails;
import sgf.gateway.validation.data.*;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.AssertUniqueShortName;
import sgf.gateway.validation.persistence.AssertUniqueTitle;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, CadisDatasetCommand.class})

@ValidTemporalBounds(groups = Data.class, startDateField = "startDate", endDateField = "endDate", format = "yyyy-MM-dd", message = "Start Date must be before End Date.")
@ValidSpatialBounds(groups = Required.class, northernLatitudeField = "northernLatitude", southernLatitudeField = "southernLatitude", easternLongitudeField = "easternLongitude", westernLongitudeField = "westernLongitude", message = "All Latitude and Logitude values must be included.")
@ValidLatitudeSpatialBounds(groups = Data.class, northernLatitudeField = "northernLatitude", southernLatitudeField = "southernLatitude", message = "North Latitude must be greater than South Latitude.")
@DatasetShortNameCharacters(groups = Data.class, shortNameField = "shortName")
@AssertUniqueShortName(groups = Persistence.class, identifierField = "identifier", shortNameField = "shortName")
@AssertUniqueTitle(groups = Persistence.class, identifierField = "identifier", nameField = "title")
public class CadisDatasetCommand implements CadisDatasetDetails {

    private UUID identifier;

    @NotBlank(groups = Required.class, message = "Title is required.")
    private String title;

    @NotBlank(groups = Required.class, message = "Short Name is required.")
    private String shortName;

    @NotBlank(groups = Required.class, message = "Description is required.")
    private String description;

    @NotEmpty(groups = Required.class, message = "Location is required.")
    private Collection<UUID> locationIds;

    @NotEmpty(groups = Required.class, message = "Platform is required.")
    private Collection<UUID> platformTypeIds;

    private Collection<UUID> instrumentKeywordIds;

    @NotEmpty(groups = Required.class, message = "Science Keyword is required.")
    private Collection<UUID> scienceKeywordIds;

    @NotEmpty(groups = Required.class, message = "ISO Topic is required.")
    private Collection<UUID> isoTopicIds;

    @NotEmpty(groups = Required.class, message = "Distribution Format is required.")
    private Collection<UUID> distributionDataFormatIds;

    @ValidDate(groups = Type.class, format = "yyyy-MM-dd", message = "Start Date must use the following format: YYYY-MM-DD")
    private String startDate;

    @ValidDate(groups = Type.class, format = "yyyy-MM-dd", message = "End Date must use the following format: YYYY-MM-DD")
    private String endDate;

    @NotBlank(groups = Required.class, message = "Northern Latitude is required (-90 to 90).")
    @ValidDecimal(groups = Type.class, message = "Northern Latitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-90", upper = "90", message = "Northern Latitude must be between 90 and -90.")
    private String northernLatitude;

    @NotBlank(groups = Required.class, message = "Southern Latitude is required (-90 to 90).")
    @ValidDecimal(groups = Type.class, message = "Southern Latitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-90", upper = "90", message = "Southern Latitude must be between 90 and -90.")
    private String southernLatitude;

    @NotBlank(groups = Required.class, message = "Western Longitude is required (-180 to 180).")
    @ValidDecimal(groups = Type.class, message = "Western Longitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-180", upper = "180", message = "Western Longitude must be between 180 and -180.")
    private String westernLongitude;

    @NotBlank(groups = Required.class, message = "Eastern Longitude is required (-180 to 180).")
    @ValidDecimal(groups = Type.class, message = "Eastern Longitude must be a number.")
    @ValidDecimalRange(groups = Data.class, lower = "-180", upper = "180", message = "Eastern Longitude must be between 180 and -180.")
    private String easternLongitude;


    private Collection<UUID> timeFrequencyIds;
    private Collection<DataType> spatialDataTypes;
    private Collection<CadisResolutionType> resolutionTypes;

    @NotNull(groups = Required.class, message = "Dataset Progress is required.")
    private DatasetProgress datasetProgress;

    private String language = "English";

    public UUID getIdentifier() {

        return this.identifier;
    }

    public void setIdentifier(UUID identifier) {

        this.identifier = identifier;
    }

    public String getTitle() {

        return this.title;
    }

    public void setTitle(String title) {

        this.title = title;
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

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Collection<UUID> getLocationIds() {

        return locationIds;
    }

    public void setLocationIds(Collection<UUID> locationIds) {

        this.locationIds = locationIds;
    }

    public Collection<UUID> getPlatformTypeIds() {

        return platformTypeIds;
    }

    public void setPlatformTypeIds(Collection<UUID> platformTypeIds) {

        this.platformTypeIds = platformTypeIds;
    }

    public Collection<UUID> getInstrumentKeywordIds() {

        return this.instrumentKeywordIds;
    }

    public void setInstrumentKeywordIds(Collection<UUID> instrumentKeywordIds) {

        this.instrumentKeywordIds = instrumentKeywordIds;
    }

    public Collection<UUID> getScienceKeywordIds() {

        return scienceKeywordIds;
    }

    public void setScienceKeywordIds(Collection<UUID> scienceKeywordIds) {

        this.scienceKeywordIds = scienceKeywordIds;
    }

    public Collection<UUID> getIsoTopicIds() {

        return this.isoTopicIds;
    }

    public void setIsoTopicIds(Collection<UUID> isoTopicIds) {

        this.isoTopicIds = isoTopicIds;
    }

    public Collection<UUID> getDistributionDataFormatIds() {

        return this.distributionDataFormatIds;
    }

    public void setDistributionDataFormatIds(Collection<UUID> distributionDataFormatIds) {

        this.distributionDataFormatIds = distributionDataFormatIds;
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

    public String getSouthernLatitude() {

        return this.southernLatitude;
    }

    public void setSouthernLatitude(String southernLatitude) {

        this.southernLatitude = southernLatitude;
    }

    public String getNorthernLatitude() {

        return this.northernLatitude;
    }

    public void setNorthernLatitude(String northernLatitude) {

        this.northernLatitude = northernLatitude;
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

    public Collection<UUID> getTimeFrequencyIds() {

        return this.timeFrequencyIds;
    }

    public void setTimeFrequencyIds(Collection<UUID> timeFrequencyIds) {

        this.timeFrequencyIds = timeFrequencyIds;
    }

    public DatasetProgress getDatasetProgress() {

        return this.datasetProgress;
    }

    public Collection<DataType> getSpatialDataTypes() {

        return this.spatialDataTypes;
    }

    public void setSpatialDataTypes(Collection<DataType> spatialDataTypes) {

        this.spatialDataTypes = spatialDataTypes;
    }

    public Collection<CadisResolutionType> getResolutionTypes() {

        return this.resolutionTypes;
    }

    public void setResolutionTypes(Collection<CadisResolutionType> resolutionTypes) {

        this.resolutionTypes = resolutionTypes;
    }

    public void setDatasetProgress(DatasetProgress datasetProgress) {

        this.datasetProgress = datasetProgress;
    }

    public String getLanguage() {

        return this.language;
    }

    public void setLanguage(String language) {

        this.language = language;
    }
}
