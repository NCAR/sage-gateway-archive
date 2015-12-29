package sgf.gateway.model.workspace;

import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.security.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DataTransferRequest extends AbstractPersistableEntity {

    /**
     * The user.
     */
    private User user;

    /**
     * The dataset.
     */
    private Dataset dataset;

    /**
     * The name.
     */
    private String name;

    /**
     * The request number.
     */
    private Integer requestNumber;

    /**
     * The total size.
     */
    private Long totalSize;

    /**
     * The status.
     */
    private DataTransferRequestStatus status = DataTransferRequestStatus.UNKNOWN;

    /**
     * The start time.
     */
    private Date startTime;

    /**
     * The stop time.
     */
    private Date stopTime;

    /**
     * The last update.
     */
    private Date lastUpdate;

    /**
     * The subsystem id.
     */
    private String subsystemId;

    /**
     * The error count.
     */
    private Integer errorCount;

    /**
     * The latest message
     */
    private String message;

    /**
     * The items.
     */
    private Set<DataTransferItem> items = new HashSet<>();

    /**
     * Instantiates a new data transfer request.
     */
    public DataTransferRequest() {

        super(false);
    }

    /**
     * Instantiates a new data transfer request.
     *
     * @param user the user making the request
     * @param name the display name of the request
     */
    public DataTransferRequest(User user, String name) {

        super(true);

        this.user = user;

        this.name = name;

        this.startTime = new Date();

    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {

        this.user = user;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {

        return this.user;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(DataTransferRequestStatus status) {

        this.status = status;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public DataTransferRequestStatus getStatus() {

        return this.status;
    }

    /**
     * Sets the start time.
     *
     * @param d the new start time
     */
    public void setStartTime(Date d) {

        this.startTime = d;
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public Date getStartTime() {

        return this.startTime;
    }

    /**
     * Sets the stop time.
     *
     * @param d the new stop time
     */
    public void setStopTime(Date d) {

        this.stopTime = d;
    }

    /**
     * Gets the stop time.
     *
     * @return the stop time
     */
    public Date getStopTime() {

        return this.stopTime;
    }

    /**
     * Sets the subsystem id.
     *
     * @param id the new subsystem id
     */
    public void setSubsystemId(String id) {

        this.subsystemId = id;
    }

    /**
     * Gets the subsystem id.
     *
     * @return the subsystem id
     */
    public String getSubsystemId() {

        return this.subsystemId;
    }

    /**
     * Gets the data transfer items.
     *
     * @return the items
     */
    public Set<DataTransferItem> getItems() {

        return this.items;
    }

    /**
     * Sets the items.
     *
     * @param items the new items
     */
    public void setItems(Set<DataTransferItem> items) {

        this.items = items;
    }

    /**
     * Adds one item.
     *
     * @param item the new item
     */
    public void addItem(DataTransferItem item) {

        this.items.add(item);

        item.setDataTransferRequest(this);
    }

    /**
     * Gets the percent complete.
     *
     * @return the percent complete
     */
    public Integer getPercentComplete() {

        int count = 0;
        int success = 0;

        for (DataTransferItem item : this.items) {

            if (item.getStatus() != DataTransferRequestStatus.ACTIVE) {

                success++;
            }

            count++;
        }

        double percentComplete = ((double) success / (double) count * 100);

        if ((percentComplete > 0.0) && (percentComplete < 1.0)) {

            percentComplete = Math.ceil(percentComplete);
        }

        return (int) percentComplete;
    }

    /**
     * Gets the fraction complete.
     *
     * @return the fraction complete
     */
    public double getFractionComplete() {

        return (double) getPercentComplete() * 0.01;
    }

    /**
     * Gets the item count.
     *
     * @return the item count
     */
    public int getItemCount() {

        return this.items.size();
    }

    /**
     * Gets the dataset.
     *
     * @return the dataset
     */
    public Dataset getDataset() {

        return this.dataset;
    }

    /**
     * Sets the dataset.
     *
     * @param dataset the new dataset
     */
    public void setDataset(Dataset dataset) {

        this.dataset = dataset;
    }

    /**
     * Gets the request number.
     *
     * @return the request number
     */
    public Integer getRequestNumber() {

        return this.requestNumber;
    }

    /**
     * Sets the request number.
     *
     * @param requestNumber the new request number
     */
    public void setRequestNumber(Integer requestNumber) {

        this.requestNumber = requestNumber;
    }

    /**
     * Gets the last update.
     *
     * @return lastUpdate the last update
     */
    public Date getLastUpdate() {

        return lastUpdate;
    }

    /**
     * Sets the last update time
     *
     * @param lastUpdate the last update
     */
    public void setLastUpdate(Date lastUpdate) {

        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets the total size in bytes represented by this request
     *
     * @return
     */
    public Long getTotalSize() {

        return totalSize;
    }

    /**
     * Sets the total size
     *
     * @param totalSize
     */
    public void setTotalSize(Long totalSize) {

        this.totalSize = totalSize;
    }

    /**
     * Sets the message
     *
     * @return
     */
    public String getMessage() {

        return message;
    }

    /**
     * Gets the message
     *
     * @param message
     */
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * Gets the request error count
     *
     * @return
     */
    public Integer getErrorCount() {

        return errorCount;
    }

    /**
     * Sets the request error count
     *
     * @param errorCount
     */
    public void setErrorCount(Integer errorCount) {

        this.errorCount = errorCount;
    }

    public void increaseTotalSize(Long l) {

        long current = 0;

        if (totalSize != null) {

            current = totalSize.longValue();
        }

        totalSize = new Long(current + l.longValue());
    }
}
