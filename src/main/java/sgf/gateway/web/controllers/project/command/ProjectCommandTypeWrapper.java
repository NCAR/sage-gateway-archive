package sgf.gateway.web.controllers.project.command;

import org.springframework.util.StringUtils;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.service.metadata.ProjectRequest;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectCommandTypeWrapper implements ProjectRequest {

    ProjectCommand projectCommand;

    DateFormat expectedDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ProjectCommandTypeWrapper(ProjectCommand projectCommand) {
        this.projectCommand = projectCommand;
    }

    public ProjectCommandTypeWrapper(Dataset dataset) {

        this.projectCommand.setIdentifier(dataset.getIdentifier().toString());
        this.projectCommand.setShortName(dataset.getShortName());
        this.projectCommand.setTitle(dataset.getTitle());
        this.projectCommand.setDescription(dataset.getDescription());

        DescriptiveMetadata metadata = dataset.getDescriptiveMetadata();

        this.projectCommand.setStartDate(this.expectedDateFormat.format(metadata.getTimePeriod().getBegin()));

        if (metadata.getTimePeriod().getEnd() != null) {
            this.projectCommand.setEndDate(this.expectedDateFormat.format(metadata.getTimePeriod().getEnd()));
        }

        this.projectCommand.setNorthernLatitudeString(metadata.getGeographicBoundingBox().getNorthBoundLatitude().toString());
        this.projectCommand.setSouthernLatitudeString(metadata.getGeographicBoundingBox().getSouthBoundLatitude().toString());
        this.projectCommand.setWesternLongitudeString(metadata.getGeographicBoundingBox().getWestBoundLongitude().toString());
        this.projectCommand.setEasternLongitudeString(metadata.getGeographicBoundingBox().getEastBoundLongitude().toString());

        this.projectCommand.setProjectGroup(dataset.getProjectGroup());
    }

    // Type conversions for command Strings to actual data types
    @Override
    public Date getStartDate() {

        Date startDateAsDate = null;

        if (this.projectCommand.getStartDate() != null && StringUtils.hasText(this.projectCommand.getStartDate())) {

            try {

                startDateAsDate = this.expectedDateFormat.parse(this.projectCommand.getStartDate());

            } catch (ParseException e) {

                throw new RuntimeException(e);
            }
        }

        return startDateAsDate;
    }


    @Override
    public Date getEndDate() {

        Date endDateAsDate = null;

        if (this.projectCommand.getEndDate() != null && StringUtils.hasText(this.projectCommand.getEndDate())) {

            try {

                endDateAsDate = this.expectedDateFormat.parse(this.projectCommand.getEndDate());

            } catch (ParseException e) {

                throw new RuntimeException(e);
            }
        }

        return endDateAsDate;
    }

    public Double getNorthernLatitude() {

        return Double.valueOf(this.projectCommand.getNorthernLatitudeString());
    }

    public void setNorthernLatitude(Double northLat) {

        this.projectCommand.setNorthernLatitudeString(northLat.toString());
    }

    public Double getSouthernLatitude() {

        return Double.valueOf(this.projectCommand.getSouthernLatitudeString());
    }

    public void setSouthernLatitude(Double southLat) {

        this.projectCommand.setSouthernLatitudeString(southLat.toString());
    }

    public Double getWesternLongitude() {

        return Double.valueOf(this.projectCommand.getWesternLongitudeString());
    }

    public void setWesternLongitude(Double westLong) {

        this.projectCommand.setWesternLongitudeString(westLong.toString());
    }

    public Double getEasternLongitude() {

        return Double.valueOf(this.projectCommand.getEasternLongitudeString());
    }

    public void setEasternLongitude(Double eastLong) {

        this.projectCommand.setEasternLongitudeString(eastLong.toString());
    }


    // 1-1 mapping to Command fields

    public String getIdentifier() {

        return this.projectCommand.getIdentifier();
    }

    public void setIdentifier(String identifier) {

        this.projectCommand.setIdentifier(identifier);
    }

    public String getShortName() {

        return this.projectCommand.getShortName();
    }

    public void setShortName(String getShortName) {

        this.projectCommand.setShortName(getShortName);
    }

    public String getTitle() {

        return this.projectCommand.getTitle();
    }

    public void setTitle(String title) {

        this.projectCommand.setTitle(title);
    }

    public String getDescription() {

        return this.projectCommand.getDescription();
    }

    public void setDescription(String description) {

        this.projectCommand.setDescription(description);
    }

    public String getProjectGroup() {

        return this.projectCommand.getProjectGroup();
    }

    public void setProjectGroup(String projectGroup) {

        this.projectCommand.setProjectGroup(projectGroup);
    }

    public String getDataCenterName() {
        return this.projectCommand.getDataCenterName();
    }

    public void setDataCenterName(String dataCenterName) {
        this.projectCommand.setDataCenterName(dataCenterName);
    }


    public String getAuthoritativeIdentifier() {
        return null;
    }

    @Override
    public Date getAuthoritativeSourceDateCreated() {
        return null;
    }

    @Override
    public Date getAuthoritativeSourceDateModified() {
        return null;
    }

    @Override
    public URI getAuthoritativeSourceURI() {
        return null;
    }

    @Override
    public Boolean isBrokered() {
        return false;
    }
}
