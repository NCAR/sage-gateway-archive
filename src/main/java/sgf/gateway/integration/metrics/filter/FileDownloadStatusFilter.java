package sgf.gateway.integration.metrics.filter;

import sgf.gateway.integration.metrics.FileDownloadPayload;

public class FileDownloadStatusFilter {

    public Boolean filter(FileDownloadPayload input) {
        Boolean pass = isOkayOrPartialContent(input.getStatus());
        return pass;
    }

    private Boolean isOkayOrPartialContent(String status) {

        Boolean rtn = false;

        if (status.equals("200") || status.equals("206")) {
            rtn = true;
        }

        return rtn;
    }
}
