package sgf.gateway.web.controllers.administration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.search.service.SearchReindexer;

@Controller
public class ReindexSearchController {

    private SearchReindexer searchReindexer;
    private String adminUri;

    public ReindexSearchController(SearchReindexer searchReindexer, String adminUri) {
        super();
        this.searchReindexer = searchReindexer;
        this.adminUri = adminUri;
    }

    @RequestMapping(value = "/root/search/reindex.html", method = RequestMethod.GET)
    public String reindex(RedirectAttributes redirectAttributes) {

        this.searchReindexer.reindex();
        redirectAttributes.addFlashAttribute("successMessage", "Reindex started.  It can take several minutes for a full reindex of search to complete.");

        return this.adminUri;
    }
}
