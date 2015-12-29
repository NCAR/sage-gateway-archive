package sgf.gateway.web.controllers.administration;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ScheduledTaskSummaryController {

    private String summaryView = "administration/schedulers";

    private Scheduler scheduler;

    protected ScheduledTaskSummaryController(StdScheduler schedulerFactoryBean) {

        this.scheduler = schedulerFactoryBean;
    }

    @RequestMapping(value = "root/scheduled-tasks.html", method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal() throws Exception {

        List<Trigger> triggerList = new ArrayList<>();

        for (String triggerGroupName : scheduler.getTriggerGroupNames()) {

            for (TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupName))) {

                Trigger trigger = scheduler.getTrigger(triggerKey);

                triggerList.add(trigger);
            }
        }

        List<JobDetail> jobDetails = new ArrayList<>();

        for (String jobGroupName : scheduler.getJobGroupNames()) {

            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroupName));

            for (JobKey jobKey : jobKeys) {

                JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                jobDetails.add(jobDetail);
            }
        }

        ModelAndView modelAndView = new ModelAndView(this.summaryView);

        modelAndView.addObject("scheduler", scheduler);
        modelAndView.addObject("triggers", triggerList);
        modelAndView.addObject("jobDetailis", jobDetails);

        return modelAndView;
    }
}
