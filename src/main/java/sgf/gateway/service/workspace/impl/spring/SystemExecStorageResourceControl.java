package sgf.gateway.service.workspace.impl.spring;

import gov.lbl.srm.StorageResourceManager.TStatusCode;
import gov.lbl.srm.client.exception.SRMClientException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.model.workspace.DataTransferRequestStatus;
import sgf.gateway.service.workspace.StorageEvent;
import sgf.gateway.service.workspace.StorageEventListener;
import sgf.gateway.service.workspace.StorageResourceControl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * System Exec Bestman SRM Implementation
 *
 * @author ejn
 */
public class SystemExecStorageResourceControl implements StorageResourceControl {

    private static final String SUBMIT_REQUEST_ERROR_MESSAGE = "Could not create request";
    private static final String SUBMIT_ITEM_ERROR_MESSAGE = "Failed to submit item to transfer system.";

    private String workingDirectory = null;
    private String temporaryDirectory = null;
    private String prefix = "srm-xfer-";
    private String suffix = ".xml";
    private String serviceURL = null;
    private String srmCopyCommandPath = "/srm/bin/srm-copy.bat"; // inject
    private String srmStatusCommandPath = "/srm/bin/srm-req-summary.bat";
    private String srmFileStatusCommandPath = "/srm/bin/srm-copy-status.bat";
    private String transferBaseURI = "gsiftp://dataportal/";
    private String accessCapability = "TDSatNCAR";
    private final String noStatus = "NO_STATUS";

    private SystemExecProcess systemExecProcess = null;

    private String proxyFilePath = null;

    private Map<String, String> storageInfoMap = new HashMap<>();

    private HashSet<StorageEventListener> listeners = new HashSet<>();

    private Log logger = LogFactory.getLog(SystemExecStorageResourceControl.class);

    public SystemExecStorageResourceControl() {

        setSystemExecProcess(new SystemExecProcess());
    }

    public void setSystemExecProcess(SystemExecProcess systemExecProcess) {

        this.systemExecProcess = systemExecProcess;
    }

    public boolean submit(DataTransferRequest request) {

        request.setStatus(DataTransferRequestStatus.ACTIVE);

        try {

            // build input file listing...

            File inputFile;

            log("td: " + temporaryDirectory);

            if (temporaryDirectory != null) {

                inputFile = File.createTempFile(prefix, suffix, new File(temporaryDirectory));
            } else {

                inputFile = File.createTempFile(prefix, suffix);
            }

            log("srm command input file: " + inputFile);

            Set<DataTransferItem> items = request.getItems();

            FileWriter writer = new FileWriter(inputFile);

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            writer.write("<request>\n");

            for (DataTransferItem item : items) {

                String surl = item.getSource().getEndpoint().toString();

                writer.append("<file>");
                writer.append("<sourceurl>");
                writer.append(surl);
                writer.append("</sourceurl>");
                writer.append("</file>\n");

                log("srm input file: " + surl);
            }

            writer.append("</request>\n");

            writer.close();

            // execute - build command

            StringBuilder command = new StringBuilder();

            command.append(srmCopyCommandPath);
            command.append(" -f ");
            command.append(inputFile.getPath());

            if (proxyFilePath != null) {

                command.append(" -proxyfile ");
                command.append(proxyFilePath);
            }

            command.append(" -serviceurl ");
            command.append(serviceURL);
            command.append(" -storageinfo ");

            command.append(getStorageInfo(request));
            command.append(" -submitonly ");
            command.append(" -nodownload ");

            log("Command: " + command);

            ProcessOutput processOutput = executeCommand(command.toString());

            String output = processOutput.getOutput();

            log("Output: " + output);

            StringBuilder requestToken = new StringBuilder();

            String label = "SRM-CLIENT*REQUEST_TOKEN=";

            int idx = output.indexOf(label);

            // parse request token

            if ((idx != -1) && (output.length() > idx + label.length())) {

                idx += label.length();

                char l = output.charAt(idx);

                while (!Character.isWhitespace(l) && (idx + 1 < output.length())) {

                    requestToken.append(l);

                    idx++;

                    l = output.charAt(idx);
                }
            }

            // logger.info("Token: " + requestToken);

            if (requestToken.length() > 0) {

                request.setSubsystemId(requestToken.toString());

                boolean deleteOutcome = inputFile.delete();
                if (deleteOutcome == false) {
                    logger.error("inputFile.delete() did not succeed for " + inputFile);
                }
            } else { // error

                logger.error("no token found: ");
                logger.error("command: " + command);
                logger.error("output: " + output);
                logger.error("user.dir: " + System.getProperty("user.dir"));
                logger.error("workingDirectory: " + workingDirectory);

                // TODO: Change the following to try multiple times in this case, using the "error count"
                // Ultimately, the entire request should be in error and setError(DTR, Ex) should be called.

                // request.setStatus(DataTransferRequestStatus.ERROR);

                setError(request, "No subsystem token found - could not submit");
            }

        } catch (IOException ioe) {

            setError(request, ioe);

            logger.error("SRMClientException: " + ioe, ioe);
        } catch (Exception e) {

            setError(request, e);

            logger.error("SRMClientException: " + e, e);
        }

        return true;
    }

    private void setError(DataTransferRequest request, Exception exception) {

        setError(request, exception.toString());
    }

    private void setError(DataTransferRequest request, String errorString) {

        request.setStatus(DataTransferRequestStatus.ERROR);

        request.setMessage(SUBMIT_REQUEST_ERROR_MESSAGE + " (" + errorString + ")");

        for (DataTransferItem item : request.getItems()) {

            item.setStatus(DataTransferRequestStatus.ERROR);
            item.setMessage(SUBMIT_ITEM_ERROR_MESSAGE);
        }
    }

    private String getStorageInfo(DataTransferRequest request) {

        // TODO generate based on access type

        String storageInfo = null;

        for (DataTransferItem item : request.getItems()) {

            String capabilityName = "I think this could be a fixed value"; //dataAccessCapability.getName();

            log("cn: " + capabilityName);

            storageInfo = storageInfoMap.get(capabilityName);

            break;
        }

        if (null == storageInfo) {

            storageInfo = ""; // default
        }

        log("storageInfo: " + storageInfo);

        return storageInfo;
    }

    public boolean update(DataTransferRequest request) {

        log("SystemExecStorageResourceControl.update()");

        return checkRequest(request);
    }

    public Collection<DataTransferRequest> update(Collection<DataTransferRequest> requestList) {

        log("SystemExecStorageResourceControl.update()");

        ArrayList<DataTransferRequest> updated = new ArrayList<>();

        for (DataTransferRequest request : requestList) {

            if (checkRequest(request)) {

                updated.add(request);
            }
        }

        return updated;
    }

    public boolean release(DataTransferRequest request) {

        try {

            // SRMRequest srmRequest = getSRMRequest(request);

            // srmRequest.releaseFiles(null);

            request.setStatus(DataTransferRequestStatus.EXPIRED);

            request.setStopTime(new Date());

            Set<DataTransferItem> items = request.getItems();

            // Aborting each item in the request.

            for (DataTransferItem requestItem : items) {

                requestItem.setStatus(DataTransferRequestStatus.EXPIRED);
            }

        } catch (Exception e) {

            logger.error("Exception: " + e, e);

        }

        return true;
    }

    public boolean release(DataTransferRequest request, DataTransferItem item) {

        // TODO HACK - release files specified

        return release(request);
    }

    public boolean cancel(DataTransferRequest request) {

        try {

            // TODO - implement request to the SRM to cancel

            request.setStatus(DataTransferRequestStatus.ABORTED);

            request.setStopTime(new Date());

            Set<DataTransferItem> items = request.getItems();

            // Aborting each item in the request.

            for (DataTransferItem requestItem : items) {

                requestItem.setStatus(DataTransferRequestStatus.ABORTED);
            }

        } catch (Exception e) {

            // throw something

            logger.error("could not cancel SRM request: " + e);
        }

        return false;
    }

    public boolean cancel(DataTransferRequest request, DataTransferItem item) {

        // TODO - implement cancel request

        return false;
    }

    public boolean extendLifetime(DataTransferRequest request) {

        // TODO - implement

        return false;
    }

    private boolean checkRequest(DataTransferRequest request) {

        boolean update;

        try {

            SimpleRequest srmRequest = getSimpleRequest(request);

            String statusCode = srmRequest.getStatus();

            boolean complete = request.getStatus() == DataTransferRequestStatus.SUCCESS;

            if (statusCode.equals(TStatusCode.SRM_REQUEST_INPROGRESS.toString()) || statusCode.equals(TStatusCode.SRM_REQUEST_QUEUED.toString())) {

                request.setStatus(DataTransferRequestStatus.ACTIVE);

            } else if (statusCode.equals(TStatusCode.SRM_SUCCESS.toString()) || statusCode.equals(TStatusCode.SRM_PARTIAL_SUCCESS.toString())) {

                if (request.getStatus() != DataTransferRequestStatus.SUCCESS) {

                    request.setStatus(DataTransferRequestStatus.SUCCESS);
                    request.setStopTime(new Date());

                    fireCompletion(request);
                }
            } else if (statusCode.equals(TStatusCode.SRM_ABORTED.toString())) {

                if (request.getStatus() != DataTransferRequestStatus.ABORTED) {

                    request.setStatus(DataTransferRequestStatus.ABORTED);
                    request.setStopTime(new Date());
                }

            } else if (statusCode.equals(TStatusCode.SRM_INVALID_REQUEST.toString())) {

                // could not find request in srm - cancel entire request and all
                // items

                request.setStatus(DataTransferRequestStatus.ERROR);
                request.setStopTime(new Date());

                for (DataTransferItem item : request.getItems()) {

                    item.setStatus(DataTransferRequestStatus.ERROR);

                    expireItem(request, item);
                }

                request.setLastUpdate(new Date()); // set current time stamp

                if (!complete) {

                    fireCompletion(request);
                }

                return true;

            } else { // ERROR REQUEST, ETC

                request.setStatus(DataTransferRequestStatus.ERROR);
                request.setStopTime(new Date());

                if (!complete) {

                    fireCompletion(request);
                }
            }

            request.setLastUpdate(new Date()); // set current time stamp

            int activeCount = 0;
            int pinnedCount = 0;
            int itemCount = 0;
            int errorCount = 0;

            for (DataTransferItem item : request.getItems()) {

                SimpleFile fileStatus = srmRequest.getFileHash().get(item.getSource().getEndpoint().toString());

                if (fileStatus != null) {

                    String fileStatusCode = fileStatus.getStatus();

                    log("Transfer File: " + item.getSource().getEndpoint().toString());
                    log("Status: " + fileStatusCode);

                    if (fileStatusCode.equals(TStatusCode.SRM_REQUEST_INPROGRESS.toString())
                            || fileStatusCode.equals(TStatusCode.SRM_REQUEST_QUEUED.toString())) {

                        if (item.getStatus() != DataTransferRequestStatus.ACTIVE) {

                            item.setStatus(DataTransferRequestStatus.ACTIVE);
                        }

                        activeCount++;
                    } else if (fileStatusCode.equals(TStatusCode.SRM_FILE_PINNED.toString())) {

                        if (item.getStatus() != DataTransferRequestStatus.SUCCESS) {

                            item.setStatus(DataTransferRequestStatus.SUCCESS);

                            exposeItem(request, item, fileStatus.getTransferSURL());
                        }

                        pinnedCount++;

                    } else if (fileStatusCode.equals(TStatusCode.SRM_FILE_LIFETIME_EXPIRED.toString())
                            || fileStatusCode.equals(TStatusCode.SRM_RELEASED.toString())) {

                        if (item.getStatus() != DataTransferRequestStatus.EXPIRED) {

                            item.setStatus(DataTransferRequestStatus.EXPIRED);

                            expireItem(request, item);
                        }
                    } else {

                        if (item.getStatus() != DataTransferRequestStatus.ERROR) {

                            item.setStatus(DataTransferRequestStatus.ERROR);

                            if ((fileStatus.getExplanation() != null) && !"".equals(fileStatus.getExplanation())) {

                                item.setMessage(fileStatus.getExplanation());
                            }

                            expireItem(request, item);
                        }
                    }

                    itemCount++;
                } else {

                    // problem, request mis-coordinated w/ srm - force to error

                    logger.error("Cannot find request item - set to ERROR"); // TODO

                    item.setStatus(DataTransferRequestStatus.ERROR);

                    expireItem(request, item);

                    errorCount++;
                }
            }

            if (itemCount > 0) {

                if ((activeCount == 0) && (pinnedCount == 0)) {

                    if (request.getStatus() == DataTransferRequestStatus.SUCCESS) {

                        request.setStatus(DataTransferRequestStatus.EXPIRED);
                    }
                } else if (errorCount == itemCount) {

                    request.setStatus(DataTransferRequestStatus.ERROR);

                    request.setLastUpdate(new Date());

                    if (!complete) {

                        fireCompletion(request);
                    }
                }
            }
        } catch (SRMClientException e) {

            logger.error("Request: " + request.getIdentifier() + " Exception" + e, e);

        } catch (Exception e) {

            logger.error("Request: " + request.getIdentifier() + " Exception" + e, e);
        }

        // TODO check if updated...

        update = true;

        return update;
    }

    private SimpleRequest getSimpleRequest(DataTransferRequest request) throws SRMClientException, Exception {

        SimpleRequest srmRequest = getRequestSummary(request);

        updateRequestDetails(request, srmRequest);

        return srmRequest;
    }

    private SimpleRequest getRequestSummary(DataTransferRequest request) {

        SimpleRequest srmRequest = null;

        StringBuilder command = new StringBuilder();

        command.append(srmStatusCommandPath);

        command.append(" -serviceurl ");
        command.append(serviceURL);
        command.append(" -requesttoken ");
        // command.append("'"+request.getSubsystemId()+"'"); // ?? single quotes required?
        command.append(request.getSubsystemId()); // ?? single quotes required?
        command.append(" ");

        // log("getRequestSummary Command: " + command);

        try {

            ProcessOutput processOutput = executeCommand(command.toString());

            String output = processOutput.getOutput();

            // parse output...

            StringBuilder status = new StringBuilder();

            String label = "status=";

            // note this summary output contains two "status=" and the second represents the actual request status.

            int idx = output.lastIndexOf(label);

            // parse request status

            if ((idx != -1) && (output.length() > idx + label.length())) {

                idx += label.length();

                char l = output.charAt(idx);

                while (!Character.isWhitespace(l) && (idx + 1 < output.length())) {

                    status.append(l);

                    idx++;

                    l = output.charAt(idx);
                }
            }

            srmRequest = new SimpleRequest();

            if (status.length() > 0) {
                // log("getRequestSummary status: " + status);
                srmRequest.setStatus(status.toString());
            } else { // error

                logger.error("no token found: ");
                logger.error("command: " + command);
                logger.error("output: " + output);

                srmRequest.setStatus(noStatus);
            }
        } catch (IOException ioe) {

            // TODO - increment error count, try again
            logger.error("getRequestSummary IOException: " + ioe);
        } catch (InterruptedException ie) {

            // TODO - increment error count, try again
            logger.error("getRequestSummary InterruptedException: " + ie);
        } catch (Exception e) {

            // TODO - increment error count, try again
            logger.error("getRequestSummary Exception: " + e);
        }

        return srmRequest;
    }

    private void updateRequestDetails(DataTransferRequest request, SimpleRequest srmRequest) {

        StringBuilder command = new StringBuilder();

        command.append(srmFileStatusCommandPath);

        command.append(" -serviceurl ");
        command.append(serviceURL);
        command.append(" -requesttoken ");
        // command.append("\""+request.getSubsystemId()+"\"");
        command.append(request.getSubsystemId());
        command.append(" -requesttype get ");

        // TODO maybe use ProcessBuilder instead of exec to get a process

        // log("updateRequestDetails Command: " + command);

        try {

            ProcessOutput processOutput = executeCommand(command.toString());

            String output = processOutput.getOutput();

            // log("updateRequestDetails output: " + output);

            int idx = 0;

            do {

                idx = output.indexOf("surl=", idx);

                if (idx != -1) {

                    String token = output.substring(idx);

                    // parse output for each file...

                    String[] names = {"surl=", "TransferURL=", "status=", "explanation="};

                    String[] values = new String[names.length];

                    for (int i = 0; i < names.length; i++) {

                        String name = names[i];

                        String value = getValueForName(token, name);

                        values[i] = value;

                        // log("name: " + name + " value: " + value);
                    }

                    SimpleFile simpleFile = new SimpleFile();

                    simpleFile.setSURL(values[0]);
                    simpleFile.setTransferSURL(values[1]);
                    simpleFile.setStatus(values[2]);
                    simpleFile.setExplanation(values[3]);

                    srmRequest.getFileHash().put(simpleFile.getSURL(), simpleFile);

                    idx++;
                }

            } while (idx != -1);

            // log"updateRequestDetails done: ");
        } catch (IOException ioe) {

            // TODO - increment error count, try again
            logger.error("updateRequestDetails IOException: " + ioe);
        } catch (InterruptedException ie) {

            // TODO - increment error count, try again
            logger.error("updateRequestDetails InterruptedException: " + ie);
        } catch (Exception e) {

            // TODO - increment error count, try again
            logger.error("updateRequestDetails Exception: " + e);
        }
    }

    private String getValueForName(String output, String name) {

        String result = null;

        int idx = output.indexOf(name);

        if ((idx != -1) && (output.length() > idx + name.length())) {

            idx += name.length();

            char l = output.charAt(idx);

            int start = idx;
            int end = idx;

            while ((l != '\n') && (l != '\r') && (idx + 1 < output.length())) {

                idx++;

                l = output.charAt(idx);

                end = idx;
            }

            result = output.substring(start, end);
        }

        return result;
    }

    private void fireCompletion(DataTransferRequest request) {

        if (listeners.size() > 0) {

            StorageEvent event = new StorageEvent(request, null, null, null);

            for (StorageEventListener listener : listeners) {

                listener.requestCompleted(event);
            }
        }
    }

    protected void exposeItem(DataTransferRequest request, DataTransferItem item, String uriString) {

        // TODO - Improve access point mapping configuration and lookup

        String baseURI = transferBaseURI;

        String fullPath = null;

        int idx = uriString.indexOf(baseURI);

        if (idx != -1) { // found base of URL

            fullPath = uriString.replace(transferBaseURI, ""); // remove base

            if (!fullPath.startsWith("/")) {

                fullPath = "/" + fullPath;
            } else {

                fullPath = fullPath.replace("//", "/");
            }

            URI fullPathURI = URI.create(fullPath).normalize();

            fullPath = fullPathURI.toString();
        } else {

            StringBuilder errorString = new StringBuilder();

            errorString.append("Could not match transfer string: ");
            errorString.append(" baseURI: " + this.transferBaseURI);
            errorString.append(" requestURI: " + uriString);

            item.setMessage(errorString.toString());

            logger.error(errorString.toString());

            // TODO throw exception ?
        }

        // call listeners

        if ((fullPath != null) && (accessCapability != null)) {

            StorageEvent event = new StorageEvent(request, item, accessCapability, fullPath);

            for (StorageEventListener listener : listeners) {

                listener.itemExposed(event);
            }
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

    public ProcessOutput executeCommand(String command) throws IOException, InterruptedException {

        return this.systemExecProcess.executeCommand(this.workingDirectory, command);
    }

    private void log(String s) {

        logger.debug(s);
    }

    public void addStorageEventListener(StorageEventListener listener) {

        listeners.add(listener);
    }

    public void removeStorageEventListener(StorageEventListener listener) {

        listeners.remove(listener);
    }

    public String getWorkingDirectory() {

        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {

        this.workingDirectory = workingDirectory;
    }

    public String getTemporaryDirectory() {

        return temporaryDirectory;
    }

    public void setTemporaryDirectory(String temporaryDirectory) {

        this.temporaryDirectory = temporaryDirectory;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSrmCopyCommandPath() {
        return srmCopyCommandPath;
    }

    public void setSrmCopyCommandPath(String srmCopyCommandPath) {
        this.srmCopyCommandPath = srmCopyCommandPath;
    }

    public String getSrmStatusCommandPath() {
        return srmStatusCommandPath;
    }

    public void setSrmStatusCommandPath(String srmStatusCommandPath) {
        this.srmStatusCommandPath = srmStatusCommandPath;
    }

    public String getSrmFileStatusCommandPath() {
        return srmFileStatusCommandPath;
    }

    public void setSrmFileStatusCommandPath(String srmFileStatusCommandPath) {
        this.srmFileStatusCommandPath = srmFileStatusCommandPath;
    }

    class SimpleRequest {

        private String status = null;
        private String explanation = null;
        private Hashtable<String, SimpleFile> files = new Hashtable<>();

        public String getStatus() {

            return status;
        }

        public void setStatus(String s) {

            status = s;
        }

        public String getExplanation() {

            return explanation;
        }

        public void setExplanation(String s) {

            explanation = s;
        }

        public SimpleFile getFile(String s) {

            return files.get(s);
        }

        public Hashtable<String, SimpleFile> getFileHash() {

            return files;
        }
    }

    class SimpleFile {

        private String sURL = null;
        private String status = null;
        private String explanation = null;
        private String transferSURL = null;

        public String getStatus() {

            return status;
        }

        public void setStatus(String s) {

            this.status = s;
        }

        public String getExplanation() {

            return explanation;
        }

        public void setExplanation(String s) {

            this.explanation = s;
        }

        public String getTransferSURL() {

            return transferSURL;
        }

        public void setTransferSURL(String s) {

            this.transferSURL = s;
        }

        public String getSURL() {

            return sURL;
        }

        public void setSURL(String surl) {

            this.sURL = surl;
        }
    }

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

    public String getProxyFilePath() {
        return proxyFilePath;
    }

    public void setProxyFilePath(String proxyFilePath) {
        this.proxyFilePath = proxyFilePath;
    }

    public Map<String, String> getStorageInfoMap() {
        return storageInfoMap;
    }

    public void setStorageInfoMap(Map<String, String> storageInfolMap) {
        this.storageInfoMap = storageInfolMap;
    }
}
