package sgf.gateway.service.publishing.dataset.file;

import sgf.gateway.model.metadata.Dataset;

import java.io.File;

public class LocalFileDirectoryStrategy {

    private final File baseDataDirectory;

    public LocalFileDirectoryStrategy(File baseDataDirectory) {
        super();
        this.baseDataDirectory = baseDataDirectory;
    }

    public File getDirectory(Dataset dataset) {
        File directory = new File(baseDataDirectory, dataset.getIdentifier().toString());
        return directory;
    }
}
