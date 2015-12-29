package sgf.gateway.web.controllers.metrics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metrics.impl.hibernate.SummaryStatisticsRepository;
import sgf.gateway.model.metrics.SummaryStatistics;

@Controller
public class SummaryStatisticsController {

    private String viewName = "metrics/summaryStatistics";

    private SummaryStatisticsRepository summaryStatisticsRepository;

    public SummaryStatisticsController(SummaryStatisticsRepository summaryStatisticsRepository) {

        this.summaryStatisticsRepository = summaryStatisticsRepository;
    }

    @RequestMapping(value = {"/metrics/summaryStatistics.htm", "/metrics/summaryStatistics.html"}, method = RequestMethod.GET)
    public ModelAndView showPage() {

        ModelAndView modelAndView = new ModelAndView(this.viewName);

        SummaryStatistics summaryStatistics = this.summaryStatisticsRepository.getLatestStatistics();

        modelAndView.addObject("statistics", summaryStatistics);

        return modelAndView;
    }


}
