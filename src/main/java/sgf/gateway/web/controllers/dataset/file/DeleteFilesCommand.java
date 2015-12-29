package sgf.gateway.web.controllers.dataset.file;

import java.util.List;

public class DeleteFilesCommand {

    private List<String> filesToDelete;

    public DeleteFilesCommand() {
    }

    public List<String> getFilesToDelete() {
        return filesToDelete;
    }

    public void setFilesToDelete(List<String> filesToDelete) {
        this.filesToDelete = filesToDelete;
    }

}
