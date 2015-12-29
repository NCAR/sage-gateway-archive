package sgf.gateway.service.workspace.impl.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.workspace.DataTransferRepository;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.dataaccess.FileAccessPointImpl;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;
import sgf.gateway.service.mail.MailService;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.workspace.DataTransferService;
import sgf.gateway.service.workspace.StorageResourceControl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DataTransferServiceImpl implements DataTransferService {

    private final DataTransferRepository dataTransferDAO;
    private final MetadataService metadataService;
    private final MailService mailService;

    private String transferAccessType = "SRM";

    private final Log logger = LogFactory.getLog(DataTransferServiceImpl.class);

    public DataTransferServiceImpl(DataTransferRepository dataTransferDAO, MetadataService metadataService,
                                   MailService mailService) {

        this.dataTransferDAO = dataTransferDAO;
        this.metadataService = metadataService;
        this.mailService = mailService;
    }


    public List<FileAccessPointImpl> getFileAccessPointList(Collection<UUID> objectIds) {
        // TODO Lookup from LogicalFileRepository

        List<LogicalFile> logicalFiles = metadataService.findLogicalFileById(objectIds);
        List<FileAccessPointImpl> faps = new ArrayList<>();

        // Finding all the file access points for each given logical file.

        for (LogicalFile logicalFile : logicalFiles) {

            faps.add(this.getFileAccessPoint(logicalFile));
        }

        return faps;
    }

    public FileAccessPointImpl getFileAccessPoint(LogicalFile logicalFile) {

        Set<FileAccessPoint> fileAccessPoints = logicalFile.getFileAccessPoints();

        // The first SRM based file access point found is the one returned.

        FileAccessPoint fap = null;

        for (FileAccessPoint fileAccessPoint : fileAccessPoints) {
            if (fileAccessPoint.getEndpoint().getScheme().equalsIgnoreCase(transferAccessType)) { // Found Deep Store based file access point.

                fap = fileAccessPoint;
                break;
            }
        }

        /**
         * DataAccessApplication = ?? how to get instance? fap = logicalFile.getPreferredAccessPoint(dataAccessApplication);
         **/

        if (fap == null) { // Did not find a SRM based file access point.
            throw new IllegalArgumentException("No SRM Based File Access Point Found for Logical File : " + logicalFile.getName());
        }

        return (FileAccessPointImpl) fap;
    }

    /**
     * {@inheritDoc}
     */
    public List<DataTransferRequest> getRequests(User user) {

        return this.dataTransferDAO.findDataTransferRequests(user);
    }

    public DataTransferRequest getRequest(User user, UUID id) {

        return this.dataTransferDAO.getDataTransferRequest(id);
    }

    public List<DataTransferRequest> getAllRequestsByStatus(DataTransferRequestStatus status) {
        return this.dataTransferDAO.findAllDataTransferRequestsByStatus(status);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public List<DataTransferRequest> submit(User user, List<FileAccessPointImpl> fileList) {

        if ((fileList == null) || (fileList.size() == 0)) {

            throw new IllegalArgumentException("DataTransferServiceImp fileList must contain at least one file");
        }

        List<DataTransferRequest> requestList = buildDataTransferRequest(user, fileList);

        for (DataTransferRequest request : requestList) {

            this.dataTransferDAO.storeDataTransferRequest(request);
        }

        return requestList;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void store(User user, DataTransferRequest request) {

        this.dataTransferDAO.storeDataTransferRequest(request);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void store(User user, DataTransferItem item) {

        this.dataTransferDAO.storeDataTransferItem(item);
    }

    private Integer getMaxRequestNumber(User user) {

        Integer maxRequestNumber = this.dataTransferDAO.getMaxRequestNumber(user);

        if (maxRequestNumber == null) {

            maxRequestNumber = 0;
        }

        return maxRequestNumber;
    }

    /**
     * Builds List of datatransfer requests for user based on list of file access points. A separate datatransfer request is created for each data access
     * capability represented by the file access point list.
     *
     * @param user     the user the request is created for.
     * @param fileList the list of file access points to transfer.
     * @return
     */
    private List<DataTransferRequest> buildDataTransferRequest(User user, List<FileAccessPointImpl> fileList) {

        DataTransferRequest request = new DataTransferRequest(user, "SRM");

        Integer maxRequestNumber = this.getMaxRequestNumber(user);

        Integer nextRequestNumber = maxRequestNumber + 1;

        request.setRequestNumber(nextRequestNumber);

        for (FileAccessPointImpl file : fileList) {

            nextRequestNumber++;

            if (request.getDataset() == null) {

                request.setDataset(file.getLogicalFile().getDataset());
            }

            DataTransferItem item = new DataTransferItem(file);

            request.addItem(item);

            request.increaseTotalSize(file.getLogicalFile().getSize());
        }

        ArrayList<DataTransferRequest> list = new ArrayList<>();

        list.add(request);

        StorageResourceControl control = SubsystemResourceFactory.getStorageResourceControl("SRM");

        control.submit(request);

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void executeCommand(Runnable command) {

        command.run();
    }

    /**
     * {@inheritDoc}
     */
    public void cancelRequest(User user, UUID id) {

        DataTransferRequest request = this.dataTransferDAO.getDataTransferRequest(id);

        if (request == null) { // The requests UUID could not be found.
            throw new ObjectNotFoundException(id.toString());
        }

        StorageResourceControl control = getStorageResourceControl();

        control.cancel(request);

        store(user, request);
    }


    private StorageResourceControl getStorageResourceControl() {

        return SubsystemResourceFactory.getStorageResourceControl("srm");
    }

    /**
     * {@inheritDoc}
     */
    public void createFileAccessPoint(DataTransferItem transferItem, String capabilityName, String path) {

        transferItem.setDiskLocation(path);

        store(null, transferItem);
    }

    public void sendRequestNotification(DataTransferRequest dataTransferRequest) {

        User user = dataTransferRequest.getUser();

        try {

            List<User> toList = new ArrayList<>();
            toList.add(user);
            this.mailService.sendDataTransferCompleteMailMessage(toList, dataTransferRequest);

        } catch (Exception e) {

            logger.error("Problem sending mail message: " + e);
        }
    }
}
