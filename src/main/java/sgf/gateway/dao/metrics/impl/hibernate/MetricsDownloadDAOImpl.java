package sgf.gateway.dao.metrics.impl.hibernate;

import org.hibernate.Query;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metrics.MetricsDownloadDAO;
import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.GeoCode;

import java.io.Serializable;
import java.util.Date;

public class MetricsDownloadDAOImpl extends AbstractRepositoryImpl<FileDownload, Serializable> implements MetricsDownloadDAO {

    @Override
    protected Class<FileDownload> getEntityClass() {
        return FileDownload.class;
    }

    /**
     * {@inheritDoc}
     */
    public void storeFileDownload(final FileDownload fileDownload) {

        super.add(fileDownload);
    }

    @Override
    public String getLatestGeoCodeIpAddress() {

        Query query = this.getSession().createQuery("SELECT fileDownload.remoteAddress FROM FileDownload AS fileDownload WHERE fileDownload.geocodeDateUpdated IS NULL AND fileDownload.remoteAddress IS NOT NULL");
        query.setMaxResults(1);

        String ipAddress = (String) query.uniqueResult();

        return ipAddress;
    }

    @Override
    public String getLatestWhoIsIpAddress() {

        Query query = this.getSession().createQuery("SELECT fileDownload.remoteAddress FROM FileDownload AS fileDownload WHERE fileDownload.whoisDateUpdated IS NULL AND fileDownload.remoteAddress IS NOT NULL");
        query.setMaxResults(1);

        String ipAddress = (String) query.uniqueResult();

        return ipAddress;
    }

    @Override
    public void updateGeoCodesForIpAddress(String ipAddress, GeoCode geocode) {

        String hqlUpdate = "update FileDownload set geocodeLatitude = :geocodeLatitude, " +
                "geocodeLongitude = :geocodeLongitude, " +
                "geocodeCountry = :geocodeCountry, " +
                "geocodeCountryCode = :geocodeCountryCode, " +
                "geocodeCity = :geocodeCity, " +
                "geocodeState = :geocodeState, " +
                "geocodeDateUpdated = :geocodeDateUpdated " +
                "where remoteAddress = :ipAddress " +
                "and geocodeDateUpdated is NULL";

        int updatedEntities = this.getSession().createQuery(hqlUpdate)
                .setParameter("geocodeLatitude", geocode.getLatitude(), DoubleType.INSTANCE)
                .setParameter("geocodeLongitude", geocode.getLongitude(), DoubleType.INSTANCE)
                .setParameter("geocodeCountry", geocode.getCountry(), StringType.INSTANCE)
                .setParameter("geocodeCountryCode", geocode.getCountryCode(), StringType.INSTANCE)
                .setParameter("geocodeCity", geocode.getCity(), StringType.INSTANCE)
                .setParameter("geocodeState", geocode.getState(), StringType.INSTANCE)
                .setTimestamp("geocodeDateUpdated", new Date())
                .setString("ipAddress", ipAddress)
                .executeUpdate();
    }

    @Override
    public void updateWhoIsesForIpAddress(String ipAddress, String whoIs) {

        String hqlUpdate = "update FileDownload set whoisOrganization = :whoisOrganization, " +
                "whoisDateUpdated =:whoisDateUpdated " +
                "where remoteAddress = :ipAddress " +
                "and whoisDateUpdated is NULL";

        int updatedEntities = this.getSession().createQuery(hqlUpdate)
                .setString("whoisOrganization", whoIs)
                .setTimestamp("whoisDateUpdated", new Date())
                .setString("ipAddress", ipAddress)
                .executeUpdate();
    }

}
