package sgf.gateway.service.geocode.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metrics.MetricsDownloadDAO;
import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.GeoCodeService;
import sgf.gateway.service.geocode.RemoteGeoCodeFacade;

public class GeoCodeServiceImpl implements GeoCodeService {

    private MetricsDownloadDAO dao;
    private RemoteGeoCodeFacade facade;

    public GeoCodeServiceImpl(MetricsDownloadDAO dao, RemoteGeoCodeFacade facade) {
        this.dao = dao;
        this.facade = facade;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateGeoCodeOfLatestFileDownloads() {

        String latestIpAddress = dao.getLatestGeoCodeIpAddress();

        if (latestIpAddress != null) {
            GeoCode geoCode = getGeoCode(latestIpAddress);
            dao.updateGeoCodesForIpAddress(latestIpAddress, geoCode);
        }
    }

    protected GeoCode getGeoCode(String latestIpAddress) {
        return facade.getForIpAddress(latestIpAddress);
    }
}
