package sgf.gateway.service.workspace;

import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;

public class StorageEvent {

    private DataTransferRequest dataTransferRequest;
    private DataTransferItem dataTransferItem;
    private String path;
    private String capabilityName;

    public StorageEvent(DataTransferRequest request, DataTransferItem item, String capabilityName, String path) {

        this.dataTransferRequest = request;
        this.dataTransferItem = item;
        this.path = path;
        this.capabilityName = capabilityName;
    }

    public DataTransferRequest getDataTransferRequest() {

        return dataTransferRequest;
    }

    public DataTransferItem getDataTransferItem() {

        return dataTransferItem;
    }

    public String getPath() {

        return path;
    }

    public String getCapabilityName() {

        return capabilityName;
    }

    @Override
    public String toString() {

        return super.toString() + " " + getCapabilityName() + " " + getPath();
    }
}
