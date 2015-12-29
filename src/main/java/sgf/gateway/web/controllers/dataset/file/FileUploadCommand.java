package sgf.gateway.web.controllers.dataset.file;

import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.publishing.TransferFilesToDatasetRequest;
import sgf.gateway.validation.data.FileUploadFormDupFilenameInList;
import sgf.gateway.validation.data.FileUploadFormFileSystem;
import sgf.gateway.validation.data.PeriodFileUploadFormFilenames;
import sgf.gateway.validation.data.RegexFileUploadFormFilenames;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.FileUploadFormDatabaseFileExists;
import sgf.gateway.validation.required.FileUploadFormFilesRequired;

import javax.validation.GroupSequence;
import java.util.List;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, FileUploadCommand.class})
@FileUploadFormFilesRequired(groups = Required.class)
@PeriodFileUploadFormFilenames(groups = Data.class)
@RegexFileUploadFormFilenames(groups = Data.class)
@FileUploadFormDupFilenameInList(groups = Data.class)
@FileUploadFormFileSystem(groups = Data.class)
@FileUploadFormDatabaseFileExists(groups = Persistence.class)
public class FileUploadCommand implements TransferFilesToDatasetRequest {

    private final Dataset dataset;

    private List<MultipartFile> files;

    public FileUploadCommand(final Dataset dataset) {

        this.dataset = dataset;
    }

    @Override
    public String getDatasetIdentifier() {

        String shortName = this.dataset.getShortName();

        return shortName;
    }


    public String getDatasetTitle() {

        String title = this.dataset.getTitle();

        return title;
    }

    public void setFiles(List<MultipartFile> files) {

        this.files = files;
    }

    @Override
    public List<MultipartFile> getFiles() {

        return this.files;
    }
}
