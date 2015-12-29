package sgf.gateway.web.controllers.dataset.share;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;

import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.service.share.ShareDataService;


@Controller
public class ShareDatasetController {

    private ShareDataService shareDataService;
    private final AuthorizationUtils authorizationUtils;
    private String shareUrl;

    public ShareDatasetController(ShareDataService shareDataService, AuthorizationUtils authorizationUtils, String shareUrl) {

        this.shareDataService = shareDataService;
        this.authorizationUtils = authorizationUtils;
        this.shareUrl = shareUrl;
    }

    @RequestMapping(value = "/dataset/{dataset}/pushToShare.html", method = RequestMethod.GET)
    public ModelAndView getSharePage(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("/dataset/share/pushToShare");

        return modelAndView;
    }

    @RequestMapping(value = "dataset/{dataset}/pushToShare", method = RequestMethod.POST)
    public ModelAndView processSubmit(@PathVariable(value = "dataset") Dataset dataset, RedirectAttributes redirectAttributes) {

        String resultView;

        this.authorizationUtils.authorizeForWrite(dataset);

        resultView = this.getSuccessView(dataset);

        this.shareDataService.shareDataset(dataset.getIdentifier());

        String shareLink = createShareResultPageLink(shareUrl);

        redirectAttributes.addFlashAttribute("successMessage", "Dataset pushed to SHARE.  View it at the " + shareLink + ".");

        return new ModelAndView(resultView);
    }

    protected String getSuccessView(Dataset dataset) {

        return "redirect:" + "/dataset/" + dataset.getShortName() + ".html";
    }

    private String createShareResultPageLink(String path) {

        String shareLink = "<a href=\"" + path + "\" />SHARE site</a>";
        return shareLink;
    }

}
