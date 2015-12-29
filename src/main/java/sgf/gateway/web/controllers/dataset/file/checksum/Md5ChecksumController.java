package sgf.gateway.web.controllers.dataset.file.checksum;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.metadata.ChecksumNotFoundException;
import sgf.gateway.service.metadata.ChecksumService;
import sgf.gateway.service.metadata.LogicalFileNotFoundException;
import sgf.gateway.service.security.AuthorizationUtils;

@Controller
public class Md5ChecksumController {

    private final AuthorizationUtils authorizationUtils;
    private final ChecksumService checksumService;

    public Md5ChecksumController(AuthorizationUtils authorizationUtils, ChecksumService checksumService) {

        this.authorizationUtils = authorizationUtils;
        this.checksumService = checksumService;
    }

    @RequestMapping(value = "/dataset/{dataset}/file/{fileName}/checksum/md5.txt", method = RequestMethod.GET)
    public ModelAndView getMd5Checksum(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "fileName") String fileName) {

        this.authorizationUtils.authorizeForWrite(dataset);

        LogicalFile logicalFile = dataset.getCurrentDatasetVersion().getLogicalFileByFileName(fileName);

        if (logicalFile == null) {

            throw new LogicalFileNotFoundException(fileName);
        }

        String md5 = this.getMd5Checksum(logicalFile);

        ModelAndView modelAndView = new ModelAndView("dataset/file/checksum");
        modelAndView.addObject("checksum", md5);

        return modelAndView;
    }

    private String getMd5Checksum(LogicalFile logicalFile) {

        String md5 = logicalFile.getMd5Checksum();

        if (md5 == null) {

            if (logicalFile.getDiskLocation() != null) {

                this.checksumService.addChecksumToFile(logicalFile.getIdentifier());

                md5 = logicalFile.getMd5Checksum();

            } else {

                throw new ChecksumNotFoundException("md5");
            }
        }

        return md5;
    }
}
