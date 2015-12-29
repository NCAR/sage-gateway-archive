package sgf.gateway.model.metadata.descriptive;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import sgf.gateway.audit.Auditable;

import java.io.Serializable;

@Audited
public class GeographicBoundingBoxImpl implements GeographicBoundingBox, Auditable {

    private Serializable identifier;

    @NotAudited
    private DescriptiveMetadata parent;

    private Double westBoundLongitude;
    private Double eastBoundLongitude;

    private Double southBoundLatitude;
    private Double northBoundLatitude;

    private GeographicBoundingBoxImpl() {
        super();
    }

    public GeographicBoundingBoxImpl(DescriptiveMetadata parent) {
        super();
        this.parent = parent;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#getWestBoundLongitude()
     */
    @Override
    public Double getWestBoundLongitude() {
        return westBoundLongitude;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#setWestBoundLongitude(java.lang.Double)
     */
    @Override
    public void setWestBoundLongitude(Double westBoundLongitude) {
        this.westBoundLongitude = westBoundLongitude;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#getEastBoundLongitude()
     */
    @Override
    public Double getEastBoundLongitude() {
        return eastBoundLongitude;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#setEastBoundLongitude(java.lang.Double)
     */
    @Override
    public void setEastBoundLongitude(Double eastBoundLongitude) {
        this.eastBoundLongitude = eastBoundLongitude;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#getSouthBoundLatitude()
     */
    @Override
    public Double getSouthBoundLatitude() {
        return southBoundLatitude;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#setSouthBoundLatitude(java.lang.Double)
     */
    @Override
    public void setSouthBoundLatitude(Double southBoundLatitude) {
        this.southBoundLatitude = southBoundLatitude;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#getNorthBoundLatitude()
     */
    @Override
    public Double getNorthBoundLatitude() {
        return northBoundLatitude;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.model.metadata.descriptive.GeograhicBoundingBox#setNorthBoundLatitude(java.lang.Double)
     */
    @Override
    public void setNorthBoundLatitude(Double northBoundLatitude) {
        this.northBoundLatitude = northBoundLatitude;
    }

}
