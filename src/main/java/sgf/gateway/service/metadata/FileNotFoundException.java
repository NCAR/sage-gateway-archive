package sgf.gateway.service.metadata;

import java.io.File;

public class FileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final File missingFile;

    public FileNotFoundException(File missingFile) {

        this.missingFile = missingFile;
    }

    public File getFile() {

        return this.missingFile;
    }
}
