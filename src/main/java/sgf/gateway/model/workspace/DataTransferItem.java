package sgf.gateway.model.workspace;

import org.safehaus.uuid.UUID;
import org.springframework.util.StringUtils;
import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;

import java.io.File;
import java.util.Date;

/**
 * The Class DataTransferItem.
 */
public class DataTransferItem extends AbstractPersistableEntity {

    private DataTransferRequest request;

    private FileAccessPoint source;

    private DataTransferRequestStatus status = DataTransferRequestStatus.ACTIVE;

    private Date lastUpdate;

    private DataTransferRequestError error = null;

    private String message;

    private String diskLocation;

    protected DataTransferItem() {

        super(false);
    }

    public DataTransferItem(UUID identifier, int version) {

        super(identifier, version);
    }

    public DataTransferItem(boolean newInstance) {

        super(newInstance);
    }

    public DataTransferItem(FileAccessPoint fileAccessPoint) {

        super(true);

        this.source = fileAccessPoint;
    }

    public void setDataTransferRequest(DataTransferRequest request) {

        this.request = request;
    }

    public DataTransferRequest getDataTransferRequest() {

        return this.request;
    }

    public FileAccessPoint getSource() {

        return this.source;
    }

    public void setStatus(DataTransferRequestStatus status) {

        this.status = status;
    }

    public DataTransferRequestStatus getStatus() {

        return this.status;
    }

    public void setError(DataTransferRequestError error) {

        this.error = error;
    }

    public DataTransferRequestError getError() {

        return this.error;
    }

    public Date getLastUpdate() {

        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {

        this.lastUpdate = lastUpdate;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getDiskLocation() {

        return this.diskLocation;
    }

    public void setDiskLocation(String diskLocation) {

        this.diskLocation = diskLocation;
    }

    public File getFile() {

        File file = null;

        if (StringUtils.hasText(this.diskLocation)) {
            file = new File(this.diskLocation);
        }

        return file;
    }
}
