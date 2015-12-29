package sgf.gateway.model.metrics.factory.impl;

import sgf.gateway.model.metrics.FileDownload;
import sgf.gateway.model.metrics.UserAgent;
import sgf.gateway.service.metrics.UserAgentService;

public class AddUserAgentTransformStep implements TransformStep {

    private final UserAgentService userAgentService;

    public AddUserAgentTransformStep(UserAgentService userAgentService) {

        this.userAgentService = userAgentService;
    }

    @Override
    public FileDownload transform(FileDownload fileDownload) {

        String userAgentName = fileDownload.getUserAgentName();

        UserAgent userAgent = this.userAgentService.getUserAgent(userAgentName);

        fileDownload.setUserAgentIdentifier(userAgent.getIdentifier());

        return fileDownload;
    }
}
