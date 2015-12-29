package sgf.gateway.dao.metrics;

import sgf.gateway.dao.Repository;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.GeoCode;

import java.io.Serializable;

public interface MetricsDownloadDAO extends Repository<FileDownload, Serializable> {

    void storeFileDownload(FileDownload fileDownload);

    String getLatestGeoCodeIpAddress();

    String getLatestWhoIsIpAddress();

    void updateGeoCodesForIpAddress(String ipAddress, GeoCode geocode);

    void updateWhoIsesForIpAddress(String ipAddress, String whoIs);
}
