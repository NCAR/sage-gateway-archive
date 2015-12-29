package sgf.gateway.web.controllers.download;

import org.safehaus.uuid.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.event.DownloadEvent;
import sgf.gateway.event.publisher.DownloadEventPublisher;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.LogicalFileNotFoundException;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.utils.time.DateStrategy;
import sgf.gateway.web.HttpHeaderConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Date;

@Controller
public class FileDownloadController {

    private RuntimeUserService runtimeUserService;
    private AuthorizationService authorizationService;
    private FileHandler fileDownloadHandler;
    private LogicalFileRepository logicalFileRepository;
    private DownloadEventPublisher downloadEventPublisher;
    private DateStrategy dateStrategy;
    private ExceptionHandlingService exceptionHandlingService;
    private EndpointStrategy httpEndpointStrategy;


    public FileDownloadController(RuntimeUserService runtimeUserService, AuthorizationService authorizationService, LogicalFileRepository logicalFileRepository,
                                  FileHandler fileDownloadHandler, DownloadEventPublisher downloadEventPublisher, DateStrategy dateStrategy, ExceptionHandlingService exceptionHandlingService, EndpointStrategy httpEndpointStrategy) {

        super();
        this.runtimeUserService = runtimeUserService;
        this.authorizationService = authorizationService;
        this.logicalFileRepository = logicalFileRepository;
        this.fileDownloadHandler = fileDownloadHandler;
        this.downloadEventPublisher = downloadEventPublisher;
        this.dateStrategy = dateStrategy;
        this.exceptionHandlingService = exceptionHandlingService;
        this.httpEndpointStrategy = httpEndpointStrategy;
    }

    @RequestMapping(value = {"/download/fileDownload.htm"}, params = "logicalFileId", method = RequestMethod.GET)
    public ModelAndView fileDownloadRequest(@RequestParam("logicalFileId") UUID logicalFileId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView modelAndView = null;

        LogicalFile logicalFile = this.logicalFileRepository.get(logicalFileId);

        if (logicalFile == null) {

            throw new LogicalFileNotFoundException(logicalFileId);
        }

        this.checkAuthorization(logicalFile);

        User user = this.runtimeUserService.getUser();

        if (this.isFileOnLocalDisk(logicalFile)) {

            Date dateStarted = dateStrategy.getDate();

            this.fileDownloadHandler.handle(request, response, logicalFile.getFile(), user);

            Date dateCompleted = dateStrategy.getDate();

            // Do we care about unsuccessful downloads here?
            this.fireFileDownloadEvent(request, logicalFile, true, dateStarted, dateCompleted);

        } else {

            modelAndView = this.redirectToRemoteEndpoint(logicalFile);
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/download/fileDownload.htm"}, params = "fileAccessPointId", method = RequestMethod.GET)
    public ModelAndView oldFileDownloadRequest(@RequestParam("fileAccessPointId") UUID fileAccessPointId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView modelAndView;

        LogicalFile logicalFile = this.logicalFileRepository.findLogicalFileByAccessPointId(fileAccessPointId);

        if (logicalFile == null) {

            throw new LogicalFileNotFoundException(fileAccessPointId);

        } else {

            String redirectUrl = "/download/fileDownload.htm?logicalFileId=" + logicalFile.getIdentifier();
            Boolean contextRelative = true;

            RedirectView redirectView = new RedirectView(redirectUrl, contextRelative);
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

            modelAndView = new ModelAndView(redirectView);
        }

        return modelAndView;
    }

    protected void checkAuthorization(LogicalFile logicalFile) {

        User user = this.runtimeUserService.getUser();

        boolean authorized = this.authorizationService.authorize(user, logicalFile.getDataset(), Operation.READ);

        if (!authorized) {

            throw new AccessDeniedException("Authorization denied for file: " + logicalFile.getName());
        }

    }

    protected boolean isFileOnLocalDisk(LogicalFile logicalFile) {

        boolean exists = false;

        if (logicalFile.getDiskLocation() != null) {

            exists = true;
        }

        return exists;
    }

    protected void fireFileDownloadEvent(HttpServletRequest request, LogicalFile logicalFile, boolean downloadCompleted, Date dateStarted, Date dateCompleted) {

        try {

            User user = this.runtimeUserService.getUser();

            Long fileSize = logicalFile.getSize();

            Long bytesSent = 0L;

            if (downloadCompleted) {

                bytesSent = fileSize;
            }

            String remoteAddress = request.getRemoteAddr();

            String userAgentName = request.getHeader(HttpHeaderConstants.USER_AGENT);

            try {

                this.downloadEventPublisher.publishDownloadEvent(new DownloadEvent(this, URI.create(request.getRequestURI()), user, dateStarted, dateCompleted, downloadCompleted, remoteAddress, fileSize, userAgentName, bytesSent, logicalFile));

            } catch (Exception e) {

                UnhandledException unhandledException = new UnhandledException(e);
                unhandledException.put("logicalFile.getIdentifier()", logicalFile.getIdentifier().toString());
                unhandledException.put("logicalFile.getDiskLocation()", logicalFile.getDiskLocation());
                this.exceptionHandlingService.handledException(unhandledException);
            }

        } catch (Exception e) {

            UnhandledException unhandledException = new UnhandledException(e);
            this.exceptionHandlingService.handledException(unhandledException);
        }
    }

    protected ModelAndView redirectToRemoteEndpoint(LogicalFile logicalFile) {

        URI redirectEndpoint = this.httpEndpointStrategy.getEndpoint(logicalFile);

        ModelAndView modelAndView = new ModelAndView(new RedirectView(redirectEndpoint.toString()));

        return modelAndView;
    }
}
