package sgf.gateway.model.metadata.inventory;

import org.safehaus.uuid.UUID;

public class ChecksumMismatchException extends RuntimeException {

    private UUID logicalFileId;
    private Algorithm algorithm;
    private String originalChecksum;
    private String newChecksum;

    public enum Algorithm {
        MD5,
        SHA_1,
        SHA_256
    }

    public ChecksumMismatchException(String originalChecksum) {
        this.originalChecksum = originalChecksum;
    }

    public ChecksumMismatchException(String originalChecksum, Algorithm algorithm) {
        this.originalChecksum = originalChecksum;
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm.toString();
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getOriginalChecksum() {
        return originalChecksum;
    }

    public void setOriginalChecksum(String originalChecksum) {
        this.originalChecksum = originalChecksum;
    }

    public String getNewChecksum() {
        return newChecksum;
    }

    public void setNewChecksum(String newChecksum) {
        this.newChecksum = newChecksum;
    }


    public UUID getLogicalFileId() {
        return logicalFileId;
    }

    public void setLogicalFileId(UUID logicalFileId) {
        this.logicalFileId = logicalFileId;
    }
}
