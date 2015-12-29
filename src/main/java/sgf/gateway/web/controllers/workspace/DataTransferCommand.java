package sgf.gateway.web.controllers.workspace;

import sgf.gateway.model.workspace.ConfirmationItems;
import sgf.gateway.model.workspace.DataTransferRequest;

import java.util.ArrayList;
import java.util.List;

public class DataTransferCommand {

    private ConfirmationItems confirmationItems = new ConfirmationItems();
    private List<DataTransferRequest> userRequests = new ArrayList<DataTransferRequest>();
    private DataTransferRequest selectedRequest = new DataTransferRequest();

    public ConfirmationItems getConfirmationItems() {
        return confirmationItems;
    }

    public void setConfirmationItems(ConfirmationItems confirmationItems) {
        this.confirmationItems = confirmationItems;
    }

    public List<DataTransferRequest> getUserRequests() {
        return this.userRequests;
    }

    public void setUserRequests(List<DataTransferRequest> requests) {
        this.userRequests = requests;
    }

    public DataTransferRequest getSelectedRequest() {
        return selectedRequest;
    }

    public void setSelectedRequest(DataTransferRequest selectedRequest) {
        this.selectedRequest = selectedRequest;
    }
}
