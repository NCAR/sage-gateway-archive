package sgf.gateway.web.controllers.archive;

import org.safehaus.uuid.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.workspace.DataTransferRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.AuthorizationToken;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.script.core.impl.FileDownloadModel;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.AuthorizationTokenService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.RequestParameterConstants;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;


@Controller
public class ArchiveRequestController {

    private final RuntimeUserService runtimeUserService;
    private final AuthorizationService authorizationService;
    private final AuthorizationTokenService authorizationTokenService;
    private final DataTransferRepository dataTransferRepository;
    private final Gateway gateway;

    public ArchiveRequestController(final RuntimeUserService runtimeUserService, final AuthorizationService authorizationService, final AuthorizationTokenService authorizationTokenService, final DataTransferRepository dataTransferRepository, final Gateway gateway) {

        this.runtimeUserService = runtimeUserService;
        this.authorizationService = authorizationService;
        this.authorizationTokenService = authorizationTokenService;
        this.dataTransferRepository = dataTransferRepository;
        this.gateway = gateway;
    }

    // TODO is there a way to set cache headers with annotations?
    @RequestMapping(value = "/archive/request/{request_id}.wget", method = RequestMethod.GET)
    public ModelAndView getArchiveRequest(@PathVariable(value = "request_id") UUID requestId, HttpServletResponse response) {

        DataTransferRequest request = this.getDataTransferRequest(requestId);

        this.checkAuthorization(request);

        Collection<FileDownloadModel> fileDownloadModels = this.createFileDownloadModels(request);

        response.addHeader("Content-Disposition", "attachment; filename=download-request.sh");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, private");

        ModelAndView modelAndView = new ModelAndView("token-wget-script");
        modelAndView.addObject("fileDownloadModels", fileDownloadModels);

        return modelAndView;
    }

    public DataTransferRequest getDataTransferRequest(UUID identifier) {

        DataTransferRequest request = this.dataTransferRepository.getDataTransferRequest(identifier);

        if (request == null) {

            throw new ObjectNotFoundException(identifier);
        }

        return request;
    }

    public void checkAuthorization(DataTransferRequest request) {

        User user = this.runtimeUserService.getUser();

        Collection<DataTransferItem> items = request.getItems();

        for (DataTransferItem item : items) {

            Dataset dataset = item.getSource().getLogicalFile().getDataset();

            boolean authorized = this.authorizationService.authorize(user, dataset, Operation.READ);

            if (!authorized) {

                throw new AccessDeniedException("Authorization denied for transfer request: " + request.getIdentifier());
            }
        }
    }

    public Collection<FileDownloadModel> createFileDownloadModels(DataTransferRequest request) {

        Collection<FileDownloadModel> uris = new ArrayList<>();

        Collection<DataTransferItem> items = request.getItems();

        for (DataTransferItem item : items) {

            LogicalFile logicalFile = item.getSource().getLogicalFile();

            URI downloadURI = this.createArchiveDownloadURIWithToken(request, item);

            FileDownloadModel model = new FileDownloadModel(logicalFile, downloadURI);

            uris.add(model);
        }

        return uris;
    }

    protected URI createArchiveDownloadURIWithToken(DataTransferRequest request, DataTransferItem item) {

        URI downloadURI = this.createBaseArchiveDownloadURI(request, item);

        User user = this.runtimeUserService.getUser();

        AuthorizationToken token = this.authorizationTokenService.generateAuthorizationToken(downloadURI, user);

        downloadURI = URI.create(downloadURI.toString() + "?" + RequestParameterConstants.AUTHORIZATION_TOKEN + "=" + token.getIdentifier());

        downloadURI = downloadURI.normalize();

        return downloadURI;
    }

    protected URI createBaseArchiveDownloadURI(DataTransferRequest request, DataTransferItem item) {

        URI uri = URI.create(gateway.getBaseURL() + "/archive/request/" + request.getIdentifier() + "/file/" + item.getIdentifier());

        uri = uri.normalize();

        return uri;
    }
}
