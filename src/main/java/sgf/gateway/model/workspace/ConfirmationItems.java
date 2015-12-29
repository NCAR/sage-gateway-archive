package sgf.gateway.model.workspace;

import sgf.gateway.model.metadata.inventory.LogicalFileImpl;

import java.util.List;


public class ConfirmationItems {
    private List<LogicalFileImpl> logicalFiles;
    private Long totalFileSize = 0L;

    public List<LogicalFileImpl> getLogicalFiles() {
        return logicalFiles;
    }

    public void setLogicalFiles(List<LogicalFileImpl> logicalFile) {
        this.logicalFiles = logicalFile;
    }

    public Long getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(Long totalFileSize) {
        this.totalFileSize = totalFileSize;
    }
}
