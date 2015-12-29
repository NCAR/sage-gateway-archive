package sgf.gateway.model.metadata;

/**
 * Interface marking a dataset (or collection) as being of type "software".
 */
public interface SoftwareProperties {

    String getSoftwareVersion();

    void setSoftwareVersion(String softwareVersion);

}
