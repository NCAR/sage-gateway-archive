package sgf.gateway.model.metadata;

import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.util.Comparator;

public class LogicalFileComparator implements Comparator<LogicalFile> {

    public int compare(LogicalFile file1, LogicalFile file2) {
        return file1.getName().compareTo(file2.getName());
    }

}
