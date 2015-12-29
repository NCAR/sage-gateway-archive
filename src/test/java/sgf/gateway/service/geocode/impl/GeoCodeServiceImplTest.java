package sgf.gateway.service.geocode.impl;

import org.junit.Test;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.GeoCodeService;
import sgf.gateway.service.geocode.RemoteGeoCodeFacade;

import java.io.Serializable;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GeoCodeServiceImplTest {

    @Test
    public void ifLatestGeoCodeIpAddressIsNull_DoNotUpdate() {

        RemoteGeoCodeFacadeSpy facade = new RemoteGeoCodeFacadeSpy();
        MetricsDownloadDAOSpy dao = new FinishedMetricsDownloadDAOSpy();

        GeoCodeService service = new GeoCodeServiceImpl(dao, facade);

        service.updateGeoCodeOfLatestFileDownloads();

        assertThat(facade.getIpAddressArgument(), nullValue());
        assertThat(dao.getIpAddressArgument(), nullValue());
        assertThat(dao.getGeoCodeArgument(), nullValue());
    }

    @Test
    public void ifLatestGeoCodeIpAddressIsNotNull_Update() {

        RemoteGeoCodeFacadeSpy facade = new RemoteGeoCodeFacadeSpy();
        MetricsDownloadDAOSpy dao = new UnfinishedMetricsDownloadDAOSpy();

        GeoCodeService service = new GeoCodeServiceImpl(dao, facade);

        service.updateGeoCodeOfLatestFileDownloads();

        assertThat(facade.getIpAddressArgument(), is("128.117.11.135"));
        assertThat(dao.getIpAddressArgument(), is("128.117.11.135"));
        assertThat(dao.getGeoCodeArgument().getCountry(), is("Good Ole U.S.A"));
    }
}

class RemoteGeoCodeFacadeSpy implements RemoteGeoCodeFacade {

    private String ipAddress;

    public String getIpAddressArgument() {
        return ipAddress;
    }

    @Override
    public GeoCode getForIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return new GeoCodeStub();
    }
}

class GeoCodeStub implements GeoCode {

    @Override
    public Double getLatitude() {
        return null;
    }

    @Override
    public Double getLongitude() {
        return null;
    }

    @Override
    public String getCountry() {
        return "Good Ole U.S.A";
    }

    @Override
    public String getCountryCode() {
        return null;
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public String getState() {
        return null;
    }
}

class UnfinishedMetricsDownloadDAOSpy extends MetricsDownloadDAOSpy {
    @Override
    public String getLatestGeoCodeIpAddress() {
        return "128.117.11.135";
    }
}

class FinishedMetricsDownloadDAOSpy extends MetricsDownloadDAOSpy {
    @Override
    public String getLatestGeoCodeIpAddress() {
        return null;
    }
}

abstract class MetricsDownloadDAOSpy implements sgf.gateway.dao.metrics.MetricsDownloadDAO {

    private String ipAddress = null;
    private GeoCode geoCode = null;

    public GeoCode getGeoCodeArgument() {
        return geoCode;
    }

    public String getIpAddressArgument() {
        return ipAddress;
    }

    @Override
    public void storeFileDownload(FileDownload fileDownload) {

    }

    @Override
    public abstract String getLatestGeoCodeIpAddress();

    @Override
    public String getLatestWhoIsIpAddress() {
        return null;
    }

    @Override
    public void updateGeoCodesForIpAddress(String ipAddress, GeoCode geocode) {
        this.ipAddress = ipAddress;
        this.geoCode = geocode;
    }

    @Override
    public void updateWhoIsesForIpAddress(String ipAddress, String whoIs) {

    }

    @Override
    public FileDownload get(Serializable id) {
        return null;
    }

    @Override
    public List<FileDownload> getAll() {
        return null;
    }

    @Override
    public void add(FileDownload newInstance) {

    }

    @Override
    public Serializable create(FileDownload newInstance) {
        return null;
    }

    @Override
    public void remove(FileDownload persistentObject) {

    }
}
