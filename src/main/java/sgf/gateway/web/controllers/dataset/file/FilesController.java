package sgf.gateway.web.controllers.dataset.file;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.file.endpoint.EndpointStrategy;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.security.User;
import sgf.gateway.script.core.impl.FileDownloadScriptModel;
import sgf.gateway.service.metadata.LogicalFileService;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.web.controllers.browse.models.DatasetDownloadRow;
import sgf.gateway.web.controllers.browse.models.LogicalFileDownloadRow;
import sgf.gateway.web.controllers.download.WgetScriptViewSelector;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Controller
public class FilesController {

    private static final String VIEW_FILES = "dataset/files/viewFiles";
    private static final String EDIT_FILES = "dataset/files/editFiles";

    private final Gateway gateway;
    private final LogicalFileService logicalFileService;
    private final RuntimeUserService runtimeUserService;
    private final AuthorizationUtils authorizationUtils;
    private final EndpointStrategy httpEndpointStrategy;
    private final EndpointStrategy gridftpEndpointStrategy;
    private final WgetScriptViewSelector wgetViewSelector;

    public FilesController(Gateway gateway, LogicalFileService logicalFileService, RuntimeUserService runtimeUserService, AuthorizationUtils authorizationUtils,
                           EndpointStrategy httpEndpointStrategy, EndpointStrategy gridftpEndpointStrategy, WgetScriptViewSelector wgetViewSelector) {

        this.gateway = gateway;
        this.logicalFileService = logicalFileService;
        this.runtimeUserService = runtimeUserService;
        this.authorizationUtils = authorizationUtils;
        this.httpEndpointStrategy = httpEndpointStrategy;
        this.gridftpEndpointStrategy = gridftpEndpointStrategy;
        this.wgetViewSelector = wgetViewSelector;
    }

    @RequestMapping(value = {"/dataset/{dataset}/files.html", "/dataset/{dataset}/file.html"}, method = RequestMethod.GET)
    public ModelAndView getFileList(@PathVariable(value = "dataset") Dataset dataset) {

        ModelAndView modelAndView = new ModelAndView(VIEW_FILES);
        modelAndView.addObject("datasetDownloadRows", this.createDatasetDownloadRowCollection(dataset));

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/editfiles.html", "/dataset/{dataset}/editfile.html"}, method = RequestMethod.GET)
    public ModelAndView getFileEditList(@PathVariable(value = "dataset") Dataset dataset) {

        ModelAndView modelAndView = new ModelAndView(EDIT_FILES);
        modelAndView.addObject("datasetDownloadRows", this.createDatasetDownloadRowCollection(dataset));

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/files.zip", "/dataset/{dataset}/file.zip"}, method = RequestMethod.GET)
    public ModelAndView downloadFilesAsZip(@PathVariable(value = "dataset") Dataset dataset, final HttpServletResponse response) {

        this.authorizationUtils.authorizeForRead(dataset);

        ModelAndView modelAndView = new ModelAndView(VIEW_FILES);

        modelAndView.addObject("logicalFiles", dataset.getCurrentDatasetVersion().getLogicalFiles());

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/files.wget", "/dataset/{dataset}/file.wget", "/dataset/{dataset}.wget"}, method = RequestMethod.GET)
    public ModelAndView getWgetScriptForFiles(@PathVariable(value = "dataset") Dataset dataset, final HttpServletResponse response) throws IOException {

        this.authorizationUtils.authorizeForRead(dataset);

        FileDownloadScriptModel downloadScriptModel = this.buildScriptModel(httpEndpointStrategy, dataset);

        ModelAndView modelAndView = null;

        if (downloadScriptModel.getSize() == 0) {

            String message = "dataset " + dataset.getTitle() + " has no file access points matching protocol http";
            response.sendError(HttpServletResponse.SC_NOT_FOUND, message);

        } else {

            response.addHeader("Content-Disposition", "attachment; filename=wget_" + dataset.getShortName() + ".sh");

            String viewName = this.selectWGetView(dataset);

            modelAndView = new ModelAndView(viewName);

            modelAndView.addObject("gateway", this.gateway);
            modelAndView.addObject("downloadScriptModel", downloadScriptModel);
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/files.dml", "/dataset/{dataset}/file.dml", "/dataset/{dataset}.dml"}, method = {RequestMethod.GET})
    public ModelAndView datasetDML(@PathVariable(value = "dataset") Dataset dataset, final HttpServletResponse response) throws Exception {

        this.authorizationUtils.authorizeForRead(dataset);

        FileDownloadScriptModel downloadScriptModel = this.buildScriptModel(gridftpEndpointStrategy, dataset);

        ModelAndView modelAndView = null;

        if (downloadScriptModel.getSize() == 0) {
            String message = "dataset " + dataset.getTitle() + " has no file access points matching protocol gridftp";
            response.sendError(HttpServletResponse.SC_NOT_FOUND, message);
        } else {
            response.addHeader("Content-Disposition", "attachment; filename=dml_" + dataset.getShortName() + ".txt");
            modelAndView = new ModelAndView("download-dml", "downloadScriptModel", downloadScriptModel);
        }

        return modelAndView;
    }

    private String selectWGetView(Dataset dataset) {

        Collection<LogicalFile> logicalFiles = dataset.getCurrentDatasetVersion().getLogicalFiles();

        String viewName = this.wgetViewSelector.select(logicalFiles);

        return viewName;
    }

    private FileDownloadScriptModel buildScriptModel(EndpointStrategy endpointStrategy, Dataset dataset) {

        Collection<LogicalFile> logicalFiles = dataset.getCurrentDatasetVersion().getLogicalFiles();

        User user = this.runtimeUserService.getUser();

        FileDownloadScriptModel downloadScriptModel = new FileDownloadScriptModel(this.gateway, user, logicalFiles, endpointStrategy);

        return downloadScriptModel;
    }

    @RequestMapping(value = {"/dataset/{dataset}/file/{file:.+}"}, method = RequestMethod.GET)
    public ModelAndView getFile(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "file") String filename, RedirectAttributes redirectAttributes, HttpServletResponse response) throws FileNotFoundException, IOException {

        this.authorizationUtils.authorizeForRead(dataset);

        LogicalFile logicalFile = dataset.getCurrentDatasetVersion().getLogicalFileByFileName(filename);

        File fileOnDisk = new File(logicalFile.getDiskLocation());

        IOUtils.copy(new FileInputStream(fileOnDisk), response.getOutputStream());

        return null;
    }

    @RequestMapping(value = {"/dataset/{dataset}/file/{file}"}, method = RequestMethod.DELETE)
    public ModelAndView deleteFile(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "file") String filename, @RequestParam("deleteConfirmed") boolean deleteConfirmed, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (deleteConfirmed) {

            this.logicalFileService.deleteFile(dataset.getShortName(), filename);

            modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + "/editfiles.html");

            redirectAttributes.addFlashAttribute("successMessage", "File Deleted.");

        } else {

            modelAndView = new ModelAndView("redirect:dataset/files/confirm-delete-file");
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/file/{file}/confirmDeleteForm.html"})
    public ModelAndView deleteConfirm(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "file") String filename) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("dataset/files/confirm-delete-file");

        return modelAndView;
    }

    private Collection<DatasetDownloadRow> createDatasetDownloadRowCollection(Dataset dataset) {

        Collection<DatasetDownloadRow> datasetDownloadRowCollection = new ArrayList<DatasetDownloadRow>();

        Collection<LogicalFileDownloadRow> logicalFileDownloadRows = createLogicalFileDownloadRowCollection(dataset.getCurrentDatasetVersion().getLogicalFiles());

        DatasetDownloadRow datasetDownloadRow = new DatasetDownloadRow(dataset, logicalFileDownloadRows);

        datasetDownloadRowCollection.add(datasetDownloadRow);

        return datasetDownloadRowCollection;
    }

    private Collection<LogicalFileDownloadRow> createLogicalFileDownloadRowCollection(Collection<LogicalFile> logicalFileCollection) {

        Collection<LogicalFileDownloadRow> logicalFileDownloadRowCollection = new ArrayList<LogicalFileDownloadRow>();

        for (LogicalFile logicalFile : logicalFileCollection) {

            LogicalFileDownloadRow logicalFileDownloadRow = new LogicalFileDownloadRow(logicalFile);
            logicalFileDownloadRowCollection.add(logicalFileDownloadRow);
        }

        // Order the rows lexicographically
        Collections.sort((List<LogicalFileDownloadRow>) logicalFileDownloadRowCollection, new FileNameCompairitor());

        return logicalFileDownloadRowCollection;
    }

    private class FileNameCompairitor implements Comparator<LogicalFileDownloadRow> {

        public int compare(LogicalFileDownloadRow logicalFileDownloadRow1, LogicalFileDownloadRow logicalFileDownloadRow2) {

            int result = logicalFileDownloadRow1.getName().compareTo(logicalFileDownloadRow2.getName());

            return result;
        }
    }
}
