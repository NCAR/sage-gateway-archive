package sgf.gateway.model.workspace;

public enum DataTransferRequestStatus {

    /**
     * The UNKNOWN.
     */
    UNKNOWN("Unknown"),
    /**
     * The ACTIVE.
     */
    ACTIVE("Active"),
    /**
     * The SUCCESS.
     */
    SUCCESS("Success"),
    /**
     * The ERROR.
     */
    ERROR("Error"),
    /**
     * The EXPIRED.
     */
    EXPIRED("Expired"),
    /**
     * The ABORTED.
     */
    ABORTED("Aborted");

    /**
     * The status.
     */
    private String status;

    /**
     * Instantiates a new data transfer request status.
     *
     * @param status the status
     */
    private DataTransferRequestStatus(String status) {

        this.status = status;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {

        return this.status;
    }

}
