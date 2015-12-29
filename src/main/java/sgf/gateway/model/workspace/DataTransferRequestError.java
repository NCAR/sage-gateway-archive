package sgf.gateway.model.workspace;

import sgf.gateway.model.AbstractPersistableEntity;

public class DataTransferRequestError extends AbstractPersistableEntity {

    /**
     * The message.
     */
    private String message;

    /**
     * Instantiates a new data transfer request error.
     */
    public DataTransferRequestError() {

        super(false);
    }

    /**
     * Instantiates a new data transfer request error.
     *
     * @param newInstance the new instance
     */
    public DataTransferRequestError(boolean newInstance) {

        super(newInstance);
    }

    /**
     * Sets the message.
     *
     * @param status the new message
     */
    public void setMessage(String status) {

        this.message = status;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {

        return this.message;
    }

}
