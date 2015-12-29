package sgf.gateway.web.controllers.download;

import org.safehaus.uuid.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.model.security.Operation;
import sgf.gateway.model.security.User;
import sgf.gateway.script.core.impl.FileDownloadScriptModel;
import sgf.gateway.service.security.AuthorizationService;
import sgf.gateway.service.security.RuntimeUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Controller
public class DownloadDatasetsScriptController {

    private final Gateway gateway;
    private final RuntimeUserService runtimeUserService;
    private final DatasetRepository datasetRepository;
    private final LogicalFileRepository logicalFileRespository;
    private final AuthorizationService authorizationService;
    private final EndpointStrategy httpEndpointStrategy;
    private final EndpointStrategy gridftpEndpointStrategy;
    private final WgetScriptViewSelector viewSelector;

    public DownloadDatasetsScriptController(final RuntimeUserService runtimeUserService, final Gateway gateway, final DatasetRepository datasetRepository,
                                            final LogicalFileRepository logicalFileRespository, final AuthorizationService authorizationService,
                                            final EndpointStrategy httpEndpointStrategy, final EndpointStrategy gridftpEndpointStrategy, final WgetScriptViewSelector viewSelector) {

        this.runtimeUserService = runtimeUserService;
        this.gateway = gateway;
        this.datasetRepository = datasetRepository;
        this.logicalFileRespository = logicalFileRespository;
        this.authorizationService = authorizationService;
        this.httpEndpointStrategy = httpEndpointStrategy;
        this.gridftpEndpointStrategy = gridftpEndpointStrategy;
        this.viewSelector = viewSelector;
    }

    @RequestMapping(value = "/download/script.wget", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wgetScript(final HttpServletRequest request, final HttpServletResponse response) {

        String[] datasetIdArray = request.getParameterValues("datasetId");

        List<Dataset> datasets = new ArrayList<Dataset>();

        for (String datasetId : datasetIdArray) {

            Dataset dataset = this.datasetRepository.getByShortName(datasetId);

            datasets.add(dataset);
        }

        datasets = this.authorizeDatasets(datasets);

        if (datasets.isEmpty()) {
            throw new AccessDeniedException("Authorization denied for all of the selected datasets.");
        }

        List<String> variableNames = new ArrayList<String>();

        if (request.getParameterMap().containsKey("variableName")) {
            variableNames.addAll(Arrays.asList(request.getParameterValues("variableName")));
        }

        List<String> standardVariableNames = new ArrayList<String>();

        if (request.getParameterMap().containsKey("standardVariableName")) {
            standardVariableNames.addAll(Arrays.asList(request.getParameterValues("standardVariableName")));
        }

        List<UUID> variableIDs = this.getVariableIdentities(variableNames, standardVariableNames);

        Collection<LogicalFile> logicalFiles = new ArrayList<LogicalFile>();

        for (Dataset dataset : datasets) {

            logicalFiles.addAll(this.datasetRepository.getFilteredLogicalFiles(dataset, "", variableIDs));
        }

        FileDownloadScriptModel fileDownloadScriptModel = this.buildScriptModel(httpEndpointStrategy, logicalFiles);

        response.addHeader("Content-Disposition", "attachment; filename=" + "wget-download.sh");

        String viewName = this.viewSelector.select(logicalFiles);

        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("gateway", this.gateway);
        modelAndView.addObject("downloadScriptModel", fileDownloadScriptModel);

        return modelAndView;
    }

    @RequestMapping(value = "/download/script.dml", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView dmlScript(final HttpServletRequest request, final HttpServletResponse response) {

        String[] datasetIdArray = request.getParameterValues("datasetId");

        List<Dataset> datasets = new ArrayList<Dataset>();

        for (String datasetId : datasetIdArray) {

            Dataset dataset = this.datasetRepository.getByShortName(datasetId);

            datasets.add(dataset);
        }

        datasets = this.authorizeDatasets(datasets);

        if (datasets.isEmpty()) {
            throw new AccessDeniedException("Authorization denied for all of the selected datasets.");
        }

        List<String> variableNames = new ArrayList<String>();

        if (request.getParameterMap().containsKey("variableName")) {
            variableNames.addAll(Arrays.asList(request.getParameterValues("variableName")));
        }

        List<String> standardVariableNames = new ArrayList<String>();

        if (request.getParameterMap().containsKey("standardVariableName")) {
            standardVariableNames.addAll(Arrays.asList(request.getParameterValues("standardVariableName")));
        }

        List<UUID> variableIDs = this.getVariableIdentities(variableNames, standardVariableNames);

        Collection<LogicalFile> logicalFiles = new ArrayList<LogicalFile>();

        for (Dataset dataset : datasets) {

            logicalFiles.addAll(this.datasetRepository.getFilteredLogicalFiles(dataset, "", variableIDs));
        }

        FileDownloadScriptModel fileDownloadScriptModel = this.buildScriptModel(gridftpEndpointStrategy, logicalFiles);

        response.addHeader("Content-Disposition", "attachment; filename=" + "dml-download.txt");

        ModelAndView modelAndView = new ModelAndView("download-dml", "downloadScriptModel", fileDownloadScriptModel);

        return modelAndView;
    }

    protected FileDownloadScriptModel buildScriptModel(EndpointStrategy endpointStrategy, Collection<LogicalFile> logicalFiles) {

        User user = this.runtimeUserService.getUser();

        FileDownloadScriptModel downloadScriptModel = new FileDownloadScriptModel(this.gateway, user, logicalFiles, endpointStrategy);

        return downloadScriptModel;
    }


    protected List<UUID> getVariableIdentities(List<String> names, List<String> standardNames) {

        List<UUID> variableIdentityList = new ArrayList<UUID>();

        variableIdentityList.addAll(this.getVariablesByName(names));

        variableIdentityList.addAll(this.getVariablesByStandardName(standardNames));

        return variableIdentityList;
    }

    protected List<UUID> getVariablesByName(List<String> names) {

        List<UUID> variableIdentityList = new ArrayList<UUID>();

        if (!names.isEmpty()) {

            Collection<Variable> variables = this.logicalFileRespository.findByNames(names);

            for (Variable variable : variables) {

                variableIdentityList.add(variable.getIdentifier());
            }
        }

        return variableIdentityList;
    }

    protected List<UUID> getVariablesByStandardName(List<String> standardNames) {

        List<UUID> variableIdentityList = new ArrayList<UUID>();

        if (!standardNames.isEmpty()) {

            Collection<Variable> variables = this.logicalFileRespository.findByStandardNames(standardNames);

            for (Variable variable : variables) {

                variableIdentityList.add(variable.getIdentifier());
            }
        }

        return variableIdentityList;
    }

    protected List<Dataset> authorizeDatasets(List<Dataset> datasetsToAuthorize) {

        User user = this.runtimeUserService.getUser();

        List<Dataset> authorizedDatasets = new ArrayList<Dataset>();

        for (Dataset dataset : datasetsToAuthorize) {

            boolean authorized = this.authorizationService.authorize(user, dataset, Operation.READ);

            if (authorized) {
                authorizedDatasets.add(dataset);
            }
        }

        return authorizedDatasets;
    }
}
