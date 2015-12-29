package sgf.gateway.service.publishing.dataset.file;

import org.safehaus.uuid.UUID;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadedFile {

    private final MultipartFile source;
    private final File target;
    private final String datasetShortName;

    private UUID logicalFileId;
    private Boolean preexisting = false;

    public UploadedFile(String datasetShortName, MultipartFile source, File target) {
        super();
        this.datasetShortName = datasetShortName;
        this.source = source;
        this.target = target;
    }

    public File getTarget() {
        return target;
    }

    public MultipartFile getSource() {
        return source;
    }

    public String getDatasetShortName() {
        return this.datasetShortName;
    }

    public UUID getLogicalFileId() {
        return logicalFileId;
    }

    public void setLogicalFileId(UUID logicalFileId) {
        this.logicalFileId = logicalFileId;
    }

    public Boolean isPreexisting() {
        return this.preexisting;
    }

    public void setPreexisting(Boolean preexisting) {
        this.preexisting = preexisting;
    }

    public void transfer() {

        try {

            this.source.transferTo(this.target);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
