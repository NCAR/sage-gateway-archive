package sgf.gateway.integration.metrics.filter;

import sgf.gateway.integration.metrics.FileDownloadPayload;

import java.util.Set;

public class FileDownloadFilter {

    private final Set<String> extensions;

    public FileDownloadFilter(Set<String> extensions) {
        super();
        this.extensions = extensions;
    }

    public Boolean filter(FileDownloadPayload input) {
        Boolean pass = isOfInterest(input.getFileURI());
        return pass;
    }

    private Boolean isOfInterest(String fileURI) {
        Boolean ofInterest = extensions.contains(getFileExtension(fileURI));
        return ofInterest;
    }

    private String getFileExtension(String fileURI) {

        String extension = null;

        if (fileURI.contains(".")) {
            extension = fileURI.substring(fileURI.lastIndexOf(".") + 1);
        }

        return extension;
    }
}
