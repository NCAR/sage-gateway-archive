package sgf.gateway.web.controllers.dataset.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.LogicalFileComparator;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.metadata.LogicalFileService;
import sgf.gateway.service.security.AuthorizationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class MassDeleteController {

    private static final String DELETE_FILES = "dataset/files/mass-delete-files";

    private final LogicalFileService logicalFileService;
    private final AuthorizationUtils authorizationUtils;


    public MassDeleteController(LogicalFileService logicalFileService, AuthorizationUtils authorizationUtils) {

        this.logicalFileService = logicalFileService;
        this.authorizationUtils = authorizationUtils;
    }


    @ModelAttribute("command")
    public DeleteFilesCommand setupCommand() {

        DeleteFilesCommand command = new DeleteFilesCommand();

        return command;
    }

    @ModelAttribute("files")
    public Collection<LogicalFile> getFilesList(@PathVariable(value = "dataset") Dataset dataset) {

        Collection<LogicalFile> logicalFiles = dataset.getCurrentDatasetVersion().getLogicalFiles();
        List logicalFilesList = new ArrayList(logicalFiles);

        Collections.sort(logicalFilesList, new LogicalFileComparator());

        return logicalFilesList;

    }

    // Show the page with the list of checkboxes
    @RequestMapping(value = "/dataset/{dataset}/massDeleteFileForm.html", method = RequestMethod.GET)
    public ModelAndView getFileDeleteList(@PathVariable(value = "dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForRead(dataset);

        ModelAndView modelAndView = new ModelAndView(DELETE_FILES);

        return modelAndView;
    }

    // Show the mass delete confirm form (need command with list of files?)
    @RequestMapping(value = "/dataset/{dataset}/confirmMassDeleteFileForm.html")
    ModelAndView showForm(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") DeleteFilesCommand deleteFilesCommand) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("/dataset/files/confirm-mass-delete-files");

        return modelAndView;

    }

    @RequestMapping(value = {"/dataset/{dataset}/file.html"}, method = RequestMethod.DELETE)
    public ModelAndView deleteFile(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") DeleteFilesCommand deleteFilesCommand,
                                   @RequestParam("deleteConfirmed") boolean deleteConfirmed, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (deleteConfirmed) {

            List<String> filesToDelete = new ArrayList<String>();
            filesToDelete = deleteFilesCommand.getFilesToDelete();

            if (filesToDelete != null && filesToDelete.size() > 0) {

                for (String filename : filesToDelete) {

                    this.logicalFileService.deleteFile(dataset.getShortName(), filename);
                }

                modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + "/editfiles.html");

                redirectAttributes.addFlashAttribute("successMessage", "Files Deleted.");

                // User didn't pick anything
            } else {
                modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + "/editfiles.html");

                redirectAttributes.addFlashAttribute("infoMessage", "No files selected.");
            }

        } else {

            modelAndView = new ModelAndView("redirect:dataset/files/confirm-mass-delete-files");
        }

        return modelAndView;
    }

}
