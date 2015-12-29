package sgf.gateway.service.geocode;

import sgf.gateway.model.metrics.GeoCode;

public interface RemoteGeoCodeFacade {
    GeoCode getForIpAddress(String ipAddress);
}
