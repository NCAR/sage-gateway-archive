package sgf.gateway.web.controllers.administration;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FireJobTriggerController {

    private Scheduler scheduler;

    public FireJobTriggerController(final StdScheduler schedulerFactoryBean) {

        this.scheduler = schedulerFactoryBean;
    }

    @RequestMapping(value = "root/triggerJob.html", params = {"jobName", "jobGroupName"}, method = RequestMethod.GET)
    public ModelAndView fireTrigger(@RequestParam("jobName") String jobName, @RequestParam("jobGroupName") String jobGroupName, RedirectAttributes redirectAttributes) throws Exception {

        this.scheduler.triggerJob(JobKey.jobKey(jobName, jobGroupName));

        redirectAttributes.addFlashAttribute("successMessage", "Job triggered.");

        return new ModelAndView("redirect:/root/scheduled-tasks.html");
    }

}
