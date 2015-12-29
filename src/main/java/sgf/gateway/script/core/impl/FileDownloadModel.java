package sgf.gateway.script.core.impl;

import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;

public class FileDownloadModel {

    private String name;
    private URI downloadURI;
    private Boolean authorizationRequired;
    private String size;
    private String checkSumAlgorithm;
    private String md5Checksum;

    public FileDownloadModel(LogicalFile logicalFile, URI downloadURI) {

        this.name = logicalFile.getName();
        this.downloadURI = downloadURI;
        this.size = logicalFile.getSize().toString();
        this.authorizationRequired = logicalFile.isReadRestricted();

        if (logicalFile.getMd5Checksum() != null) {
            this.checkSumAlgorithm = "md5";
            this.md5Checksum = logicalFile.getMd5Checksum();
        }
    }

    public FileDownloadModel(String fileName, URI downloadURI) {

        this.name = fileName;
        this.downloadURI = downloadURI;
    }

    public String getName() {
        return name;
    }

    public URI getDownloadURI() {
        return downloadURI;
    }

    public Boolean getAuthorizationRequired() {
        return authorizationRequired;
    }

    public String getSize() {
        return size;
    }

    public String getCheckSumAlgorithm() {
        return checkSumAlgorithm;
    }

    public String getMd5Checksum() {
        return md5Checksum;
    }

}
