package sgf.gateway.service.workspace.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;
import sgf.gateway.service.workspace.DataTransferService;
import sgf.gateway.service.workspace.StorageResourceControl;

import java.util.ArrayList;
import java.util.List;

public class DataTransferMonitorJob {

    private final Log logger = LogFactory.getLog(DataTransferMonitorJob.class);

    private DataTransferService transferService = null;

    private DataTransferMonitorJobListener monitorJobListener = new DataTransferMonitorJobListener();

    private boolean enabled;

    private DataTransferMonitorJob(boolean enabled) {

        this.enabled = enabled;
    }

    public void setDataTransferService(DataTransferService transferService) {

        this.transferService = transferService;
        this.monitorJobListener.setDataTransferService(transferService);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void execute() {

        if (this.enabled) {

            List<DataTransferRequest> activeRequests = DataTransferMonitorJob.this.transferService.getAllRequestsByStatus(DataTransferRequestStatus.ACTIVE);
            List<DataTransferRequest> successfulRequests = DataTransferMonitorJob.this.transferService.getAllRequestsByStatus(DataTransferRequestStatus.SUCCESS);

            activeRequests.addAll(successfulRequests);

            List<String> idList = new ArrayList<String>();

            for (DataTransferRequest request : activeRequests) {

                idList.add(request.getIdentifier().toString());
            }

            checkRequestList(idList);
        }
    }

    private void checkRequestList(List<String> idList) {

        for (String id : idList) {

            final UUID requestId = new UUID(id);

            try {

                Runnable command = new Runnable() {

                    public void run() {

                        DataTransferRequest request = DataTransferMonitorJob.this.transferService.getRequest(null, requestId);

                        checkRequest(request);
                    }
                };

                this.transferService.executeCommand(command); // run in tx

            } catch (Exception e) {

                this.logger.error("DataTransferMonitorJob exception: " + e);
                throw new RuntimeException(e);
            }
        }
    }

    private void checkRequest(DataTransferRequest request) {

        String type = request.getName();

        StorageResourceControl control = SubsystemResourceFactory.getStorageResourceControl(type);

        control.addStorageEventListener(monitorJobListener);

        if (control.update(request)) {

            this.transferService.store(null, request);
        }
    }
}
