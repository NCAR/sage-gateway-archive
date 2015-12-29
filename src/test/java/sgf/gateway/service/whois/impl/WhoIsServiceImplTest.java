package sgf.gateway.service.whois.impl;

import org.junit.Test;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.whois.RemoteWhoIsFacade;

import java.io.Serializable;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class WhoIsServiceImplTest {

    @Test
    public void ifLatestWhoIsIpAddressIsNull_DoNotUpdate() {

        RemoteWhoIsFacadeSpy facade = new RemoteWhoIsFacadeSpy();
        MetricsDownloadDAOSpy dao = new FinishedMetricsDownloadDAOSpy();

        WhoIsServiceImpl service = new WhoIsServiceImpl(dao, facade);

        service.updateWhoIsOfLatestFileDownloads();

        assertThat(facade.getIpAddressArgument(), nullValue());
        assertThat(dao.getIpAddressArgument(), nullValue());
        assertThat(dao.getWhoIsArgument(), nullValue());
    }

    @Test
    public void ifLatestWhoIsIpAddressIsNotNull_Update() {

        RemoteWhoIsFacadeSpy facade = new RemoteWhoIsFacadeSpy();
        MetricsDownloadDAOSpy dao = new UnfinishedMetricsDownloadDAOSpy();

        WhoIsServiceImpl service = new WhoIsServiceImpl(dao, facade);

        service.updateWhoIsOfLatestFileDownloads();

        assertThat(facade.getIpAddressArgument(), is("128.117.11.135"));
        assertThat(dao.getIpAddressArgument(), is("128.117.11.135"));
        assertThat(dao.getWhoIsArgument(), is("ncar"));
    }

    private class RemoteWhoIsFacadeSpy implements RemoteWhoIsFacade {

        private String ipAddress;

        public String getIpAddressArgument() {
            return ipAddress;
        }

        @Override
        public String getNameByIPAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return "ncar";
        }
    }

    private class UnfinishedMetricsDownloadDAOSpy extends MetricsDownloadDAOSpy {
        @Override
        public String getLatestWhoIsIpAddress() {
            return "128.117.11.135";
        }
    }

    private class FinishedMetricsDownloadDAOSpy extends MetricsDownloadDAOSpy {
        @Override
        public String getLatestWhoIsIpAddress() {
            return null;
        }
    }

    private abstract class MetricsDownloadDAOSpy implements sgf.gateway.dao.metrics.MetricsDownloadDAO {

        private String ipAddress = null;
        private String whoIs = null;

        private String getWhoIsArgument() {
            return whoIs;
        }

        private String getIpAddressArgument() {
            return ipAddress;
        }

        @Override
        public void storeFileDownload(FileDownload fileDownload) {

        }

        @Override
        public String getLatestGeoCodeIpAddress() {
            return null;
        }

        @Override
        public abstract String getLatestWhoIsIpAddress();

        @Override
        public void updateGeoCodesForIpAddress(String ipAddress, GeoCode geocode) {

        }

        @Override
        public void updateWhoIsesForIpAddress(String ipAddress, String whoIs) {
            this.ipAddress = ipAddress;
            this.whoIs = whoIs;
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
}