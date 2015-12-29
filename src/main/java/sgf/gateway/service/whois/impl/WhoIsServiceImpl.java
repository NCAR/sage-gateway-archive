package sgf.gateway.service.whois.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metrics.MetricsDownloadDAO;
import sgf.gateway.service.whois.RemoteWhoIsFacade;
import sgf.gateway.service.whois.WhoIsService;

public class WhoIsServiceImpl implements WhoIsService {

    private MetricsDownloadDAO dao;
    private RemoteWhoIsFacade facade;

    public WhoIsServiceImpl(MetricsDownloadDAO dao, RemoteWhoIsFacade facade) {
        this.dao = dao;
        this.facade = facade;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateWhoIsOfLatestFileDownloads() {

        String ipAddress = this.dao.getLatestWhoIsIpAddress();

        if (ipAddress != null) {
            String whoIsName = this.getNameByIpAddress(ipAddress);
            this.dao.updateWhoIsesForIpAddress(ipAddress, whoIsName);
        }
    }

    public String getLatestWhoIsIpAddress() {
        return this.dao.getLatestWhoIsIpAddress();
    }

    public String getNameByIpAddress(String ipAddress) {

        return this.facade.getNameByIPAddress(ipAddress);
    }
}
