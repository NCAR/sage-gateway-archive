package sgf.gateway.web.controllers.workspace;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.service.workspace.DataTransferService;

@Controller
public class SummaryRequestController {

    private final DataTransferService dataTransferService;
    private final RuntimeUserService runtimeUserService;

    private String view;

    public SummaryRequestController(DataTransferService dataTransferService, RuntimeUserService runtimeUserService) {
        super();

        this.dataTransferService = dataTransferService;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = {"/workspace/user/summaryRequest.htm", "/workspace/user/summaryRequest.html"}, method = RequestMethod.GET)
    public ModelAndView handleRequest() throws Exception {

        User user = this.runtimeUserService.getUser();

        DataTransferCommand dataTransferCommand = new DataTransferCommand();
        dataTransferCommand.setUserRequests(this.dataTransferService.getRequests(user));

        return new ModelAndView(this.getView(), "dataTransferCommand", dataTransferCommand);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
