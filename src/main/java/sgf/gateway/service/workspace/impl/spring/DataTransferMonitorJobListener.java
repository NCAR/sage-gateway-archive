package sgf.gateway.service.workspace.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.service.workspace.DataTransferService;
import sgf.gateway.service.workspace.StorageEvent;
import sgf.gateway.service.workspace.StorageEventListener;

public class DataTransferMonitorJobListener implements StorageEventListener {

    private DataTransferService dataTransferService = null;

    private Log logger = LogFactory.getLog(DataTransferMonitorJobListener.class);

    /**
     * {@inheritDoc}
     */
    public void requestCompleted(StorageEvent event) {

        logger.debug("requestCompleted - IMPL");

        // TODO call Notification Service and send user email for this Request

        dataTransferService.sendRequestNotification(event.getDataTransferRequest());
    }

    /**
     * {@inheritDoc}
     */
    public void itemExposed(StorageEvent event) {

        logger.debug("itemExposed - IMPL");

        dataTransferService.createFileAccessPoint(event.getDataTransferItem(), event.getCapabilityName(), event.getPath());
    }

    /**
     * {@inheritDoc}
     */
    public void itemExpired(StorageEvent event) {

        logger.debug("itemExpired - IMPL: " + dataTransferService);

        // dataTransferService.expireItem(event.getDataTransferItem());
    }

    public DataTransferService getDataTransferService() {
        return dataTransferService;
    }

    public void setDataTransferService(DataTransferService dataTransferService) {
        this.dataTransferService = dataTransferService;
    }
}
