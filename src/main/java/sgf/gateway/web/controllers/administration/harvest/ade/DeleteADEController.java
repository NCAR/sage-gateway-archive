package sgf.gateway.web.controllers.administration.harvest.ade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sgf.gateway.search.service.DeletionService;

@Controller
public class DeleteADEController {

    DeletionService deletionService;
    String datacenterName;

    public DeleteADEController(DeletionService deletionService, String datacenterName) {
        this.deletionService = deletionService;
        this.datacenterName = datacenterName;
    }

    @RequestMapping(value = "/delete/root/ADEDatasets.html", method = RequestMethod.GET)
    public String delete() {

        deletionService.deleteByDatacenter(datacenterName);

        return "cadis/root/index";
    }


}
