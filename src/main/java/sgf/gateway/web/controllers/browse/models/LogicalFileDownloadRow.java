package sgf.gateway.web.controllers.browse.models;

import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.file.endpoint.HttpEndpointStrategy;
import sgf.gateway.model.file.endpoint.SrmEndpointStrategy;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.text.SimpleDateFormat;

public class LogicalFileDownloadRow {

    private final LogicalFile logicalFile;

    // gateway in the http endpoint strategy can be null only because we do not need the
    // actual http endpoint to be fabricated within this class (and these strategies as
    // injected dependencies needs revisiting)
    private final EndpointStrategy httpEndpointStrategy = new HttpEndpointStrategy(null);
    private final EndpointStrategy srmEndpointStrategy = new SrmEndpointStrategy();

    public LogicalFileDownloadRow(LogicalFile logicalFile) {

        this.logicalFile = logicalFile;
    }

    public String getIdentifier() {

        return this.logicalFile.getIdentifier().toString();
    }

    public String getName() {

        return this.logicalFile.getName();
    }

    public String getModifiedDate() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'");

        return format.format(this.logicalFile.getDateUpdated());
    }

    public String getFormat() {

        String format = null;
        if (this.logicalFile.getDataFormat() != null) {
            format = this.logicalFile.getDataFormat().getName();
        }
        return format;
    }

    public String getLocation() {

        String result = null;

        if (this.httpEndpointStrategy.endpointExists(this.logicalFile)) {

            result = "DISK";

        } else if (this.srmEndpointStrategy.endpointExists(this.logicalFile)) {

            result = "SRM";
        }

        return result;
    }

    public Long getSize() {

        return this.logicalFile.getSize();
    }

    public boolean isDirectDownload() {

        boolean direct = this.httpEndpointStrategy.endpointExists(this.logicalFile);

        return direct;
    }

    public boolean isDeletable() {

        boolean isDeletable = this.logicalFile.isDeletable();

        return isDeletable;
    }
}
