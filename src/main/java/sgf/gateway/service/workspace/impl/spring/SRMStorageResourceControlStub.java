package sgf.gateway.service.workspace.impl.spring;

import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;
import sgf.gateway.service.workspace.StorageEventListener;
import sgf.gateway.service.workspace.StorageResourceControl;

import java.util.Set;

public class SRMStorageResourceControlStub implements StorageResourceControl {

    @Override
    public boolean submit(DataTransferRequest request) {

        request.setStatus(DataTransferRequestStatus.ACTIVE);

        return true;
    }

    @Override
    public boolean update(DataTransferRequest request) {

        request.setStatus(DataTransferRequestStatus.SUCCESS);

        Set<DataTransferItem> items = request.getItems();

        for (DataTransferItem item : items) {

            item.setStatus(DataTransferRequestStatus.SUCCESS);
        }

        return true;
    }

    @Override
    public boolean release(DataTransferRequest request) {

        return true;
    }

    @Override
    public boolean cancel(DataTransferRequest request) {

        return true;
    }

    @Override
    public void addStorageEventListener(StorageEventListener listener) {


    }

    @Override
    public void removeStorageEventListener(StorageEventListener listener) {


    }

}
