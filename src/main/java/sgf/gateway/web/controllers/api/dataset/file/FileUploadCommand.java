package sgf.gateway.web.controllers.api.dataset.file;

import org.safehaus.uuid.UUID;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.publishing.TransferFilesToDatasetRequest;
import sgf.gateway.validation.data.FileUploadFileExistsFileSystemAnyCase;
import sgf.gateway.validation.data.PeriodFileUploadFilename;
import sgf.gateway.validation.data.RegexFileUploadFilename;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.FileUploadDatabaseFileExists;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, FileUploadCommand.class})

@FileUploadDatabaseFileExists(groups = Persistence.class)
@FileUploadFileExistsFileSystemAnyCase(groups = Data.class)
public class FileUploadCommand implements TransferFilesToDatasetRequest {

    private Dataset dataset;

    @NotNull(groups = Required.class, message = "file is required.")
    @RegexFileUploadFilename(groups = Data.class)
    @PeriodFileUploadFilename(groups = Data.class)
    private MultipartFile multipartFile = null;

    public FileUploadCommand(Dataset dataset) {

        this.dataset = dataset;
    }

    public String getDatasetIdentifier() {

        return this.dataset.getShortName();
    }

    public String getDatasetTitle() {

        return this.dataset.getTitle();
    }

    public UUID getDatasetUUID() {

        return this.dataset.getIdentifier();
    }

    public void setFile(MultipartFile file) {

        this.multipartFile = file;
    }

    public MultipartFile getFile() {

        return this.multipartFile;
    }

    @Override
    public List<MultipartFile> getFiles() {

        List<MultipartFile> files = new ArrayList<>();

        if (this.multipartFile != null) {

            files.add(this.multipartFile);
        }

        return files;
    }
}
