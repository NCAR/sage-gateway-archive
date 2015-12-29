package sgf.gateway.web.controllers.browse;

import org.safehaus.uuid.UUID;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, DownloadDatasetFilesCommand.class})
public class DownloadDatasetFilesCommand {

    private UUID datasetId;

    @NotNull(groups = Required.class, message = "No files selected for download.")
    private UUID[] logicalFileIds;

    public DownloadDatasetFilesCommand(UUID datasetId) {

        this.datasetId = datasetId;
    }

    public UUID getDatasetId() {

        return this.datasetId;
    }

    public void setLogicalFileId(UUID[] logicalFileIds) {

        this.logicalFileIds = logicalFileIds;
    }

    public UUID[] getLogicalFileId() {

        return this.logicalFileIds;
    }
}
