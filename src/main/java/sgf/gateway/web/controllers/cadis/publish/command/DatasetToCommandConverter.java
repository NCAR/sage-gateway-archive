package sgf.gateway.web.controllers.cadis.publish.command;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.TransformerUtils;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.ScienceKeyword;
import sgf.gateway.model.metadata.descriptive.TimePeriod;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


public class DatasetToCommandConverter {

    public void convert(Dataset dataset, CadisDatasetCommand command) {

        this.setIdentifier(dataset, command);
        this.setTitle(dataset, command);
        this.setShortName(dataset, command);

        this.setDescription(dataset, command);

        this.setLocationIds(dataset, command);

        this.setPlatformTypeIds(dataset, command);

        this.setInstrumentKeywordIds(dataset, command);

        this.setScienceKeywordIds(dataset, command);

        this.setIsoTopicIds(dataset, command);

        this.setDistributionDataFormatIds(dataset, command);

        this.setTimeCoordinateAxis(dataset, command);
        this.setGeographicBoundingBox(dataset, command);

        this.setTimeFrequencyIds(dataset, command);

        this.setSpatialDataTypes(dataset, command);

        this.setResolutionTypes(dataset, command);

        this.setDatasetProgress(dataset, command);

        this.setLanguage(dataset, command);

    }

    public void setLanguage(Dataset dataset, CadisDatasetCommand command) {

        command.setLanguage(dataset.getDescriptiveMetadata().getLanguage());
    }

    public void setDatasetProgress(Dataset dataset, CadisDatasetCommand command) {

        command.setDatasetProgress(dataset.getDescriptiveMetadata().getDatasetProgress());
    }

    public void setResolutionTypes(Dataset dataset, CadisDatasetCommand command) {

        command.setResolutionTypes((dataset.getDescriptiveMetadata()).getResolutionTypes());
    }

    public void setSpatialDataTypes(Dataset dataset, CadisDatasetCommand command) {

        command.setSpatialDataTypes(dataset.getDescriptiveMetadata().getDataTypes());
    }

    public void setTimeFrequencyIds(Dataset dataset, CadisDatasetCommand command) {

        Collection<UUID> frequencyIds = CollectionUtils.collect(dataset.getDescriptiveMetadata().getTimeFrequencies(), TransformerUtils.invokerTransformer("getIdentifier"));
        command.setTimeFrequencyIds(frequencyIds);
    }

    public void setTimeCoordinateAxis(Dataset dataset, CadisDatasetCommand command) {

        TimePeriod timePeriod = dataset.getDescriptiveMetadata().getTimePeriod();

        if (timePeriod != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date begin = timePeriod.getBegin();

            if (begin != null) {
                command.setStartDate(dateFormat.format(begin));
            }

            Date end = timePeriod.getEnd();

            if (end != null) {
                command.setEndDate(dateFormat.format(end));
            }
        }
    }

    public void setGeographicBoundingBox(Dataset dataset, CadisDatasetCommand command) {

        if ((null != dataset.getDescriptiveMetadata()) && (null != dataset.getDescriptiveMetadata().getGeographicBoundingBox())) {

            command.setWesternLongitude(dataset.getDescriptiveMetadata().getGeographicBoundingBox().getWestBoundLongitude().toString());
            command.setEasternLongitude(dataset.getDescriptiveMetadata().getGeographicBoundingBox().getEastBoundLongitude().toString());
            command.setNorthernLatitude(dataset.getDescriptiveMetadata().getGeographicBoundingBox().getNorthBoundLatitude().toString());
            command.setSouthernLatitude(dataset.getDescriptiveMetadata().getGeographicBoundingBox().getSouthBoundLatitude().toString());
        }

    }


    public void setDistributionDataFormatIds(Dataset dataset, CadisDatasetCommand command) {

        Collection<UUID> formatIds = CollectionUtils.collect(dataset.getDataFormats(), TransformerUtils.invokerTransformer("getIdentifier"));
        command.setDistributionDataFormatIds(formatIds);
    }


    public void setScienceKeywordIds(Dataset dataset, CadisDatasetCommand command) {

        Collection<ScienceKeyword> scienceKeywords = dataset.getDescriptiveMetadata().getScienceKeywords();

        Collection<UUID> scienceKeywordIds = CollectionUtils.collect(scienceKeywords, TransformerUtils.invokerTransformer("getIdentifier"));
        command.setScienceKeywordIds(scienceKeywordIds);
    }

    public void setIsoTopicIds(Dataset dataset, CadisDatasetCommand command) {

        Collection<UUID> isoTopicIds = CollectionUtils.collect(dataset.getIsoTopics(), TransformerUtils.invokerTransformer("getIdentifier"));
        command.setIsoTopicIds(isoTopicIds);
    }

    public void setInstrumentKeywordIds(Dataset dataset, CadisDatasetCommand command) {

        Collection<UUID> instrumentIds = CollectionUtils.collect(dataset.getDescriptiveMetadata().getInstrumentKeywords(), TransformerUtils.invokerTransformer("getIdentifier"));
        command.setInstrumentKeywordIds(instrumentIds);
    }

    public void setPlatformTypeIds(Dataset dataset, CadisDatasetCommand command) {

        Collection<UUID> platformIds = CollectionUtils.collect(dataset.getDescriptiveMetadata().getPlatformTypes(), TransformerUtils.invokerTransformer("getIdentifier"));
        command.setPlatformTypeIds(platformIds);
    }

    public void setLocationIds(Dataset dataset, CadisDatasetCommand command) {

        Collection<UUID> locationIds = CollectionUtils.collect(dataset.getDescriptiveMetadata().getLocations(), TransformerUtils.invokerTransformer("getIdentifier"));
        command.setLocationIds(locationIds);
    }

    public void setIdentifier(Dataset dataset, CadisDatasetCommand command) {

        command.setIdentifier(dataset.getIdentifier());
    }

    public void setTitle(Dataset dataset, CadisDatasetCommand command) {

        command.setTitle(dataset.getTitle());
    }

    public void setShortName(Dataset dataset, CadisDatasetCommand command) {

        command.setShortName(dataset.getShortName());
    }

    public void setDescription(Dataset dataset, CadisDatasetCommand command) {

        command.setDescription(dataset.getDescription());
    }
}
