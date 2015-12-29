package sgf.gateway.web.controllers.api.dataset.file;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metadata.DatasetNotEditableException;
import sgf.gateway.service.metadata.LogicalFileService;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.service.security.impl.acegi.DatasetAccessDeniedException;
import sgf.gateway.web.HttpHeaderConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.List;

@Controller
public class FileController {

    private final LogicalFileService logicalFileService;
    private final AuthorizationUtils authorizationUtils;
    private final EndpointStrategy endpointStrategy;
    private final ExceptionHandlingService exceptionHandlingService;

    public FileController(LogicalFileService logicalFileService, AuthorizationUtils authorizationUtils, EndpointStrategy endpointStrategy, ExceptionHandlingService exceptionHandlingService) {

        this.logicalFileService = logicalFileService;
        this.authorizationUtils = authorizationUtils;
        this.endpointStrategy = endpointStrategy;
        this.exceptionHandlingService = exceptionHandlingService;
    }

    @ModelAttribute("command")
    public FileUploadCommand setupCommand(@PathVariable(value = "dataset") Dataset dataset, HttpServletRequest request) {

        this.authorizationUtils.authorizeForWrite(dataset);

        if (dataset.isBrokered()) {

            throw new DatasetNotEditableException(dataset.getShortName());
        }

        FileUploadCommand command = new FileUploadCommand(dataset);

        CommonsMultipartFile file = (CommonsMultipartFile) request.getAttribute("file");

        if (file != null) {
            command.setFile(file);
        }

        return command;
    }

    /*
     * The {file_name:.+} is actually a regular expression.  It is needed because spring by default trims off the extension of a request.
     * Which in general is a good thing, except in this case where we want the file's extension.
     *
     * Please see the following Spring Jira Tickets for more information:
     * https://jira.springsource.org/browse/SPR-6164  This ticket actually shows the use of the :.+ regular expression.
     * https://jira.springsource.org/browse/SPR-7632
     * https://jira.springsource.org/browse/SPR-9333
     */
    @RequestMapping(value = {"/api/dataset/{dataset}/file/{file_name:.+}", "/api/v1/dataset/{dataset}/file/{file_name:.+}"}, method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<String> putFile(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "file_name") String fileName, @ModelAttribute("command") @Valid FileUploadCommand command, BindingResult bindingResult) throws IOException {

        ResponseEntity<String> responseEntity;

        if (bindingResult.hasErrors()) {

            HttpHeaders responseHeaders = new HttpHeaders();

            responseHeaders.set("Validation Error(s)", bindingResult.getAllErrors().toString());

            responseEntity = new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);

        } else {

            List<LogicalFile> logicalFiles = this.logicalFileService.transferFilesToDataset(command);

            URI logicalFileUri = this.endpointStrategy.getEndpoint(logicalFiles.get(0));

            HttpHeaders responseHeaders = new HttpHeaders();

            responseHeaders.set("Location", logicalFileUri.toString());

            responseEntity = new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
        }

        return responseEntity;
    }

    /*
     * The {file_name:.+} is actually a regular expression.  It is needed because spring by default trims off the extension of a request.
     * Which in general is a good thing, except in this case where we want the file's extension.
     *
     * Please see the following Spring Jira Tickets for more information:
     * https://jira.springsource.org/browse/SPR-6164  This ticket actually shows the use of the :.+ regular expression.
     * https://jira.springsource.org/browse/SPR-7632
     * https://jira.springsource.org/browse/SPR-9333
     */
    @RequestMapping(value = {"/api/dataset/{dataset}/file/{file_name:.+}", "/api/v1/dataset/{dataset}/file/{file_name:.+}"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> putFile(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "file_name") String fileName) throws IOException {

        this.logicalFileService.deleteFile(dataset.getShortName(), fileName);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        return responseEntity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(HttpServletRequest request, Exception exception) {

        this.reportException(request, exception);

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.set("Exception", exception.getMessage());

        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

        return responseEntity;
    }

    @ExceptionHandler(DatasetAccessDeniedException.class)
    public ResponseEntity<String> handleDatasetAccessDeniedException(HttpServletRequest request, DatasetAccessDeniedException exception) {

        this.reportException(request, exception);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        return responseEntity;
    }

    @ExceptionHandler(DatasetNotEditableException.class)
    public ResponseEntity<String> handleDatasetNotEditableException(HttpServletRequest request, DatasetNotEditableException exception) {

        this.reportException(request, exception);

        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.set("Validation Error(s)", "Dataset not editable.");

        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseHeaders, HttpStatus.BAD_REQUEST);

        return responseEntity;
    }

    private void reportException(HttpServletRequest request, Exception exception) {

        UnhandledException unhandledException = this.createUnhandledException(request, exception);

        this.exceptionHandlingService.handleUnexpectedException(unhandledException);
    }

    private UnhandledException createUnhandledException(HttpServletRequest request, Exception exception) {

        UnhandledException unhandledException = new UnhandledException(exception);

        unhandledException.put("Remote Address", request.getRemoteAddr());
        unhandledException.put("User Agent", request.getHeader(HttpHeaderConstants.USER_AGENT));
        unhandledException.put("Server Name", request.getServerName());
        unhandledException.put("Server Port", "" + request.getServerPort());
        unhandledException.put("Method", request.getMethod());
        unhandledException.put("URL", request.getRequestURI());
        unhandledException.put("Referer", request.getHeader(HttpHeaderConstants.REFERRER));

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length != 0) {

            for (Cookie cookie : cookies) {

                unhandledException.put(cookie.getName(), cookie.getValue());
            }
        }

        unhandledException.put("Query String", request.getQueryString());

        Enumeration parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {

            String parameterName = (String) parameterNames.nextElement();

            String parameterValue = request.getParameter(parameterName);

            if (parameterName.equals("j_password") || parameterName.equals("password") || parameterName.equals("confirmationPassword") || parameterName.equals("oldPassword") || parameterName.equals("confirmNewPassword")) {

                parameterValue = "********";
            }

            unhandledException.put(parameterName, "'" + parameterValue + "'");
        }

        return unhandledException;
    }
}
