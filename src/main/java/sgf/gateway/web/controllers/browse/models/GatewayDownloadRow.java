package sgf.gateway.web.controllers.browse.models;

import org.apache.commons.io.FileUtils;
import sgf.gateway.model.Gateway;

import java.util.ArrayList;
import java.util.Collection;

public class GatewayDownloadRow {

    private Gateway gateway;

    private int datasetCount;
    private int fileCount;
    private long datasetFileSize;
    private boolean isLocal = false;

    private String filenamePattern;
    private Collection<String> variables;
    private Collection<String> datasets;

    public GatewayDownloadRow(Gateway gateway, int datasetCount, int fileCount, long datasetFileSize, boolean isLocal) {

        this.gateway = gateway;
        this.datasetCount = datasetCount;
        this.fileCount = fileCount;
        this.datasetFileSize = datasetFileSize;
        this.isLocal = isLocal;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public int getDatasetCount() {
        return datasetCount;
    }

    public void setDatasetCount(int datasetCount) {
        this.datasetCount = datasetCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public long getDatasetFileSize() {
        return datasetFileSize;
    }

    public void setDatasetFileSize(long datasetFileSize) {
        this.datasetFileSize = datasetFileSize;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getDatasetFileDisplaySize() {

        return FileUtils.byteCountToDisplaySize(this.datasetFileSize);
    }

    public String getFilenamePattern() {
        return filenamePattern;
    }

    public void setFilenamePattern(String fileFilterString) {
        this.filenamePattern = fileFilterString;
    }

    public Collection<String> getVariables() {

        if (null == variables) {
            variables = new ArrayList<>();
        }

        return variables;
    }

    public Collection<String> getDatasets() {

        if (null == datasets) {
            datasets = new ArrayList<>();
        }

        return datasets;
    }
}
