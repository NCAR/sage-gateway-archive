package sgf.gateway.web.controllers.archive.file;

import org.safehaus.uuid.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sgf.gateway.dao.workspace.DataTransferRepository;
import sgf.gateway.event.DownloadEvent;
import sgf.gateway.event.publisher.DownloadEventPublisher;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.AuthorizationToken;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferItem;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.AuthorizationTokenService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.utils.time.DateStrategy;
import sgf.gateway.web.HttpHeaderConstants;
import sgf.gateway.web.controllers.RequestParameterConstants;
import sgf.gateway.web.controllers.download.FileHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URI;
import java.util.Date;

@Controller
public class ArchiveFileController {

    private final RuntimeUserService runtimeUserService;
    private final AuthorizationService authorizationService;
    private final DataTransferRepository dataTransferRepository;
    private final FileHandler fileDownloadHandler;
    private final DownloadEventPublisher downloadEventPublisher;
    private final DateStrategy dateStrategy;
    private final ExceptionHandlingService exceptionHandlingService;
    private final AuthorizationTokenService authorizationTokenService;


    public ArchiveFileController(final RuntimeUserService runtimeUserService, final AuthorizationService authorizationService, final DataTransferRepository dataTransferRepository,
                                 final FileHandler fileDownloadHandler, DownloadEventPublisher downloadEventPublisher, DateStrategy dateStrategy, ExceptionHandlingService exceptionHandlingService,
                                 final AuthorizationTokenService authorizationTokenService) {

        this.runtimeUserService = runtimeUserService;
        this.authorizationService = authorizationService;
        this.dataTransferRepository = dataTransferRepository;
        this.fileDownloadHandler = fileDownloadHandler;
        this.downloadEventPublisher = downloadEventPublisher;
        this.dateStrategy = dateStrategy;
        this.exceptionHandlingService = exceptionHandlingService;
        this.authorizationTokenService = authorizationTokenService;
    }

    @RequestMapping(value = "/archive/request/{request_id}/file/{file_id}", method = RequestMethod.GET)
    public void getArchiveRequest(@PathVariable(value = "request_id") UUID requestId, @PathVariable(value = "file_id") UUID itemId, HttpServletRequest request, HttpServletResponse response) {

        User user = this.runtimeUserService.getUser();

        DataTransferItem item = this.getDataTransferItem(itemId);

        this.checkAuthorization(user, item);

        sendFile(item, user, request, response);
    }

    @RequestMapping(value = "/archive/request/{request_id}/file/{file_id}", method = RequestMethod.GET, params = {RequestParameterConstants.AUTHORIZATION_TOKEN})
    public void getArchiveRequestWithToken(@PathVariable(value = "request_id") UUID requestId, @PathVariable(value = "file_id") UUID itemId, @RequestParam(value = RequestParameterConstants.AUTHORIZATION_TOKEN, required = true) UUID tokenValue,
                                           HttpServletRequest request, HttpServletResponse response) {

        AuthorizationToken authorizationToken = this.authorizationTokenService.validateToken(tokenValue, URI.create(request.getRequestURI()));

        User user = authorizationToken.getUser();

        DataTransferItem item = this.getDataTransferItem(itemId);

        this.checkAuthorization(user, item);

        sendFile(item, user, request, response);
    }

    protected void sendFile(DataTransferItem item, User user, HttpServletRequest request, HttpServletResponse response) {

        Date dateStarted = dateStrategy.getDate();

        boolean completed = false;

        try {

            this.fileDownloadHandler.handle(request, response, item.getFile(), user);

            completed = true;

        } finally {

            Date dateCompleted = dateStrategy.getDate();

            this.fireFileDownloadEvent(request, user, item, dateStarted, dateCompleted, completed);
        }
    }

    public DataTransferItem getDataTransferItem(UUID identifier) {

        DataTransferItem item = this.dataTransferRepository.getDataTransferItem(identifier);

        if (item == null) {

            throw new ObjectNotFoundException(identifier);
        }

        return item;
    }

    protected void checkAuthorization(final User user, final DataTransferItem item) {

        LogicalFile logicalFile = item.getSource().getLogicalFile();

        boolean authorized = this.authorizationService.authorize(user, logicalFile.getDataset(), Operation.READ);

        if (!authorized) {

            throw new AccessDeniedException("Authorization denied for file: " + logicalFile.getName());
        }

    }

    public void fireFileDownloadEvent(HttpServletRequest request, User user, DataTransferItem item, Date dateStarted, Date dateCompleted, boolean completed) {

        try {

            File file = item.getFile();

            Long fileSize = file.length();

            Long bytesSent = fileSize;

            String remoteAddress = request.getRemoteAddr();

            String userAgentName = request.getHeader(HttpHeaderConstants.USER_AGENT);

            LogicalFile logicalFile = item.getSource().getLogicalFile();

            this.downloadEventPublisher.publishDownloadEvent(new DownloadEvent(this, URI.create(request.getRequestURI()), user, dateStarted, dateCompleted, completed, remoteAddress, fileSize, userAgentName, bytesSent, logicalFile));

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException("dataTransferItem: " + item.getIdentifier() + " completed: " + completed, e);
            this.exceptionHandlingService.handledException(unhandledException);
        }
    }
}
