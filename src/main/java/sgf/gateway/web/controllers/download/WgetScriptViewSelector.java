package sgf.gateway.web.controllers.download;

import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.util.Collection;

public class WgetScriptViewSelector {

    private final String certificateView;
    private final String noCertificateView;

    public WgetScriptViewSelector(String certificateView, String noCertificateView) {
        this.certificateView = certificateView;
        this.noCertificateView = noCertificateView;
    }

    public String select(Collection<LogicalFile> logicalFiles) {

        if (this.isCertificateRequired(logicalFiles)) {
            return this.certificateView;
        } else {
            return this.noCertificateView;
        }
    }

    private boolean isCertificateRequired(Collection<LogicalFile> logicalFiles) {

        for (LogicalFile logicalFile : logicalFiles) {
            if (logicalFile.isReadRestricted()) {
                return true;
            }
        }

        return false;
    }
}
