package sgf.gateway.service.workspace.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;
import sgf.gateway.service.workspace.StorageEvent;
import sgf.gateway.service.workspace.StorageEventListener;
import sgf.gateway.service.workspace.StorageResourceControl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Mocked up SRC for demonstration and testing purposes. Simulates SRM-like behavior.
 *
 * @author ejn
 */
public class MockStorageResourceControl implements StorageResourceControl {

    private static final long PIN_TIME = 60000L * 10L; // 10 minutes

    private final Log logger = LogFactory.getLog(MockStorageResourceControl.class);

    private HashSet<StorageEventListener> listeners = new HashSet<StorageEventListener>();

    private final java.util.Random random = new java.util.Random();

    private String transferBaseURI = "gsiftp://dataportal//home/srm/bestman.aux/cache/";
    private String accessCapability = "TDSatNCAR";
    private String accessPathInfo = "srm";

    public String getTransferBaseURI() {
        return transferBaseURI;
    }

    public void setTransferBaseURI(String transferBaseURI) {
        this.transferBaseURI = transferBaseURI;
    }

    public String getAccessCapability() {
        return accessCapability;
    }

    public void setAccessCapability(String accessCapability) {
        this.accessCapability = accessCapability;
    }

    public String getAccessPathInfo() {
        return accessPathInfo;
    }

    public void setAccessPathInfo(String accessPathInfo) {
        this.accessPathInfo = accessPathInfo;
    }

    public boolean submit(DataTransferRequest request) {

        request.setStatus(DataTransferRequestStatus.ACTIVE);

        request.setSubsystemId(String.valueOf(System.currentTimeMillis()));

        for (DataTransferItem item : request.getItems()) {

            try {

                String surl = item.getSource().getEndpoint().toString();

                log("MockStorageResourceControl.submit item surl: " + surl);
            } catch (Exception e) { // HACK

                log("MockStorageResourceControl.submit ex: " + e);

                request.setStatus(DataTransferRequestStatus.ERROR);
            }
        }

        return true;
    }

    public boolean update(DataTransferRequest request) {

        log("MockStorageResourceControl.update()");

        return checkRequest(request);
    }

    public boolean release(DataTransferRequest request) {

        request.setStatus(DataTransferRequestStatus.EXPIRED);
        request.setStopTime(new Date());

        Set<DataTransferItem> items = request.getItems();

        for (DataTransferItem requestItem : items) {

            requestItem.setStatus(DataTransferRequestStatus.EXPIRED);
        }

        return true;
    }

    public boolean cancel(DataTransferRequest request) {

        request.setStatus(DataTransferRequestStatus.ABORTED);
        request.setStopTime(new Date());
        Set<DataTransferItem> items = request.getItems();

        // Aborting each item in the request.
        for (DataTransferItem requestItem : items) {
            requestItem.setStatus(DataTransferRequestStatus.ABORTED);
        }

        return false;
    }

    private boolean checkRequest(DataTransferRequest request) {

        boolean update = false;

        Set<DataTransferItem> items = request.getItems();

        // boolean complete = true;

        boolean itemUpdate = false;

        for (DataTransferItem item : items) {

            log("MockStorageResourceControl.checkRequest item: ");

            if (checkItem(item)) {

                itemUpdate = true;
            }
        }

        if (itemUpdate) {

            update = itemUpdate;

            refreshRequest(request);
        }

        log("MockStorageResourceControl % complete: " + request.getPercentComplete());

        return update;
    }

    private boolean refreshRequest(DataTransferRequest request) {

        boolean update = false;

        int activeCount = 0;
        int successCount = 0;
        int expiredCount = 0;
        int errorCount = 0;

        for (DataTransferItem item : request.getItems()) {

            if (item.getStatus() == DataTransferRequestStatus.ACTIVE) {

                activeCount++;
            } else if (item.getStatus() == DataTransferRequestStatus.SUCCESS) {

                successCount++;
            } else if (item.getStatus() == DataTransferRequestStatus.EXPIRED) {

                expiredCount++;
            } else if (item.getStatus() == DataTransferRequestStatus.ERROR) {

                errorCount++;
            }
        }

        log("MockStorageResourceControl activeCount: " + activeCount);

        if (request.getStatus() == DataTransferRequestStatus.ACTIVE) {

            if (activeCount == 0) {

                log("MockStorageResourceControl active->SUCCESS");

                request.setStatus(DataTransferRequestStatus.SUCCESS);
                request.setStopTime(new Date());
                fireCompletion(request);

                update = true;
            }
        } else { // must be SUCCESS

            if (errorCount == request.getItems().size()) {

                log("MockStorageResourceControl set request ERRROR");

                request.setStatus(DataTransferRequestStatus.ERROR);
                request.setStopTime(new Date());

                fireCompletion(request);

                update = true;

                log("MockStorageResourceControl error");
            } else if (successCount == 0) {

                log("MockStorageResourceControl set request EXPIRED");

                request.setStatus(DataTransferRequestStatus.EXPIRED);

                update = true;
            }
        }

        return update;
    }

    private boolean checkItem(DataTransferItem item) {

        boolean update = false;

        if (item.getStatus() == DataTransferRequestStatus.ACTIVE) {

            if (this.random.nextFloat() > 0.75) { // mock up success

                log("MockStorageResourceControl checkItem SUCCESS");

                item.setStatus(DataTransferRequestStatus.SUCCESS);
                exposeItem(item.getDataTransferRequest(), item, "foo");
                update = true;
            } else if (this.random.nextFloat() > 0.95) { // mock up error

                log("MockStorageResourceControl checkItem ERROR");

                item.setStatus(DataTransferRequestStatus.ERROR);
                item.setMessage("Error X9999 Occurred");
                update = true;
            }
        } else if (item.getStatus() == DataTransferRequestStatus.SUCCESS) {

            if (System.currentTimeMillis() > item.getDataTransferRequest().getStartTime().getTime() + PIN_TIME) {

                log("MockStorageResourceControl checkItem EXPIRED");

                item.setStatus(DataTransferRequestStatus.EXPIRED);
                expireItem(item.getDataTransferRequest(), item);
                update = true;
            }
        }

        return update;
    }

    private void log(String s) {

        this.logger.debug(s);
    }

    public void addStorageEventListener(StorageEventListener listener) {

        listeners.add(listener);
    }

    public void removeStorageEventListener(StorageEventListener listener) {

        listeners.remove(listener);
    }

    private void fireCompletion(DataTransferRequest request) {

        for (StorageEventListener listener : listeners) {

            listener.requestCompleted(new StorageEvent(request, null, null, null));
        }
    }

    private void expireItem(DataTransferRequest request, DataTransferItem item) {

        if (item != null) {

            if (item.getDiskLocation() != null) {

                StorageEvent event = new StorageEvent(request, item, null, null);

                for (StorageEventListener listener : listeners) {

                    listener.itemExpired(event);
                }
            }
        }

    }

    private void exposeItem(DataTransferRequest request, DataTransferItem item, String uriString) {

        // build example file path...

        String fullPath = accessPathInfo + "/" + item.getSource().getLogicalFile().getName();

        // call listeners

        StorageEvent event = new StorageEvent(request, item, accessCapability, fullPath);

        for (StorageEventListener listener : listeners) {

            listener.itemExposed(event);
        }
    }
}
