package sgf.gateway.script.core.impl;

import sgf.gateway.model.Gateway;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.User;
import sgf.gateway.script.core.DownloadScriptModel;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

public class FileDownloadScriptModel implements DownloadScriptModel {

    private final User user;
    private final Gateway gateway;
    private final EndpointStrategy endpointStrategy;

    private String myProxyServer;
    private String myProxyPort;

    private Collection<FileDownloadModel> fileDownloadModels;

    public FileDownloadScriptModel(Gateway gateway, User user, Collection<LogicalFile> logicalFiles, EndpointStrategy endpointStrategy) {

        super();

        this.gateway = gateway;
        this.user = user;
        this.endpointStrategy = endpointStrategy;

        this.initialize(logicalFiles);
    }

    protected void initialize(Collection<LogicalFile> logicalFiles) {
        setMyProxyServer();
        setMyProxyServerPort();
        createFileDownloadModels(logicalFiles);
    }

    private void setMyProxyServer() {

        try {
            this.myProxyServer = this.user.getMyProxyEndpoint().getHost();
        } catch (Exception e) {
            this.myProxyServer = "<myproxy-server>";
        }
    }

    private void setMyProxyServerPort() {

        try {
            this.myProxyPort = Integer.toString(this.user.getMyProxyEndpoint().getPort());
        } catch (Exception e) {
            this.myProxyPort = "<myproxy-server-port>";
        }
    }

    private void createFileDownloadModels(Collection<LogicalFile> logicalFiles) {

        this.fileDownloadModels = new ArrayList<FileDownloadModel>();

        for (LogicalFile logicalFile : logicalFiles) {

            URI downloadURI = this.endpointStrategy.getEndpoint(logicalFile);

            if (downloadURI != null) {

                FileDownloadModel fileDownloadModel = new FileDownloadModel(logicalFile, downloadURI);
                this.fileDownloadModels.add(fileDownloadModel);
            }
        }
    }

    public Gateway getGateway() {
        return this.gateway;
    }

    public User getUser() {
        return this.user;
    }

    public String getMyProxyServer() {
        return this.myProxyServer;
    }

    public String getMyProxyServerPort() {
        return this.myProxyPort;
    }

    public Collection<FileDownloadModel> getFileDownloadModels() {
        return this.fileDownloadModels;
    }

    public Integer getSize() {
        return this.fileDownloadModels.size();
    }
}
