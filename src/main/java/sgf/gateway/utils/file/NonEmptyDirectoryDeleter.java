package sgf.gateway.utils.file;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class NonEmptyDirectoryDeleter {

    public void delete(File directory) {
        try {
            if (directory.exists()) {
                FileUtils.deleteDirectory(directory);
            }
        } catch (Exception e) {
            throw new DirectoryNotDeletedException(directory, e);
        }
    }
}

class DirectoryNotDeletedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DirectoryNotDeletedException(File file, Exception e) {
        super("Unable to delete directory '" + file.getAbsolutePath() + "' from the file system.", e);
    }
}

