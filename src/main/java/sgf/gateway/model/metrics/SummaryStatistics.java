package sgf.gateway.model.metrics;

public class SummaryStatistics {

    private Long identifier = 0L;
    private Long totalDatasets = 0L;
    private Long totalFiles = 0L;
    private Long totalBytes = 0L;

    public SummaryStatistics() {
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public Long getTotalDatasets() {
        return totalDatasets;
    }

    public void setTotalDatasets(Long totalDatasets) {
        this.totalDatasets = totalDatasets;
    }

    public Long getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Long totalFiles) {
        this.totalFiles = totalFiles;
    }

    public Long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(Long totalBytes) {
        this.totalBytes = totalBytes;
    }

}
