package sgf.gateway.web.controllers.search;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.api.SearchQuery;
import sgf.gateway.search.api.SearchResult;
import sgf.gateway.search.core.CriteriaImpl;

import javax.validation.Valid;

@Controller
public class AnalyticalSearchController {

    private final SearchQuery searchQuery;

    public AnalyticalSearchController(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }

    @ModelAttribute("command")
    public AnalyticalSearchCommand setupAnalyticalSearchCommand() {
        return new AnalyticalSearchCommand();
    }

    @RequestMapping(value = "/root/search/analytic.html", method = RequestMethod.GET)
    public ModelAndView search() throws SolrServerException {

        ModelAndView modelAndView = new ModelAndView("/search/search-analytical");

        return modelAndView;
    }

    @RequestMapping(value = "/root/search/analyticResult.html", method = RequestMethod.GET)
    public ModelAndView submit(@ModelAttribute("command") @Valid AnalyticalSearchCommand searchCommand, BindingResult result) throws SolrServerException {

        ModelAndView modelAndView = new ModelAndView("/search/search-analytical");

        if (!result.hasErrors()) {
            SearchResult searchResult = this.executeSearchQuery(searchCommand);
            modelAndView.addObject("searchResult", searchResult);
        }

        return modelAndView;
    }

    private SearchResult executeSearchQuery(AnalyticalSearchCommand searchCommand) {

        Criteria criteria = this.createSearchCriteria(searchCommand);
        SearchResult searchResult = this.searchQuery.execute(criteria);

        return searchResult;
    }

    private Criteria createSearchCriteria(AnalyticalSearchCommand searchCommand) {

        CriteriaImpl criteria = new CriteriaImpl();

        criteria.setFreeText(searchCommand.getQueryText());

        Integer bigEnoughToReturnAll = 20000;
        criteria.setResultSize(bigEnoughToReturnAll);

        return criteria;
    }
}
