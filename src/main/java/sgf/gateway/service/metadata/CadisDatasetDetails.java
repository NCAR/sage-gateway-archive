package sgf.gateway.service.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.CadisResolutionType;
import sgf.gateway.model.metadata.DataType;
import sgf.gateway.model.metadata.DatasetProgress;

import java.util.Collection;

public interface CadisDatasetDetails {

    String getTitle();

    String getShortName();

    String getDescription();

    Collection<UUID> getLocationIds();

    Collection<UUID> getPlatformTypeIds();

    Collection<UUID> getInstrumentKeywordIds();

    Collection<UUID> getScienceKeywordIds();

    Collection<UUID> getIsoTopicIds();

    Collection<UUID> getDistributionDataFormatIds();

    String getStartDate();

    String getEndDate();

    String getNorthernLatitude();

    String getSouthernLatitude();

    String getWesternLongitude();

    String getEasternLongitude();

    Collection<UUID> getTimeFrequencyIds();

    Collection<DataType> getSpatialDataTypes();

    Collection<CadisResolutionType> getResolutionTypes();

    DatasetProgress getDatasetProgress();

    String getLanguage();
}
