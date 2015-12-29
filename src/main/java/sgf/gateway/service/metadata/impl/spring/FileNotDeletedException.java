package sgf.gateway.service.metadata.impl.spring;

import java.io.File;

public class FileNotDeletedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FileNotDeletedException(File file) {

        super("Unable to delete file '" + file.getAbsolutePath() + "' from the file system.");
    }
}
