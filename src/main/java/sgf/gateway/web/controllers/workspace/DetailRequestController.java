package sgf.gateway.web.controllers.workspace;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.model.security.User;
import sgf.gateway.model.workspace.DataTransferRequest;
import sgf.gateway.service.security.RuntimeUserService;
import sgf.gateway.service.workspace.DataTransferService;

@Controller
public class DetailRequestController {

    private DataTransferService dataTransferService;
    private RuntimeUserService runtimeUserService;
    private String view;

    public DetailRequestController(DataTransferService dataTransferService, RuntimeUserService runtimeUserService) {

        this.dataTransferService = dataTransferService;
        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = {"/workspace/user/transferRequestDetail.htm", "/workspace/user/transferRequestDetail.html"}, method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(value = "dataTransferRequestId") UUID requestId) throws Exception {

        User user = runtimeUserService.getUser();

        DataTransferCommand dataTransferCommand = new DataTransferCommand();

        DataTransferRequest dataTransferRequest = this.dataTransferService.getRequest(user, requestId);

        dataTransferCommand.setSelectedRequest(dataTransferRequest);

        ModelAndView modelAndView = new ModelAndView(this.getView());
        modelAndView.addObject("dataTransferCommand", dataTransferCommand);

        return modelAndView;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
