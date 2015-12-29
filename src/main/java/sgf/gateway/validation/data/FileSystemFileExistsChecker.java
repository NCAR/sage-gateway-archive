package sgf.gateway.validation.data;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.NameFileFilter;

import java.io.File;
import java.io.FileFilter;

public class FileSystemFileExistsChecker {

    public FileSystemFileExistsChecker() {
    }

    public boolean isFileSystemFileExists(File directory, String filename, boolean exactMatch) {

        boolean matches = false;

        IOCase iocase;

        if (exactMatch) {
            iocase = IOCase.SENSITIVE;
        } else {
            iocase = IOCase.INSENSITIVE;
        }

        FileFilter fileFilter = new NameFileFilter(filename, iocase);

        File[] fileList = directory.listFiles(fileFilter);

        // This means the file was there already
        if ((null != fileList) && (fileList.length > 0)) {
            matches = true;
        }

        return matches;
    }

}