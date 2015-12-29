package sgf.gateway.service.security.impl.acegi;

import org.openid4java.message.AuthRequest;
import org.openid4java.message.Message;
import org.openid4java.message.MessageException;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;
import sgf.gateway.model.security.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenidProviderSRegFacade {

    private final Message responseMessage;
    private final AuthRequest authRequest;
    private final User user;

    public OpenidProviderSRegFacade(Message responseMessage, AuthRequest authRequest, User user) {

        this.responseMessage = responseMessage;
        this.authRequest = authRequest;
        this.user = user;
    }

    public void attachAttributesToResponse() throws MessageException {

        SRegRequest sregReq = (SRegRequest) authRequest.getExtension(SRegMessage.OPENID_NS_SREG);

        List required = sregReq.getAttributes(true);
        required.addAll(sregReq.getAttributes(false));

        if (required.contains("email")) {
            // data released by the user
            Map userDataSReg = new HashMap();
            //userData.put("email", "user@example.com");

            SRegResponse sregResp = SRegResponse.createSRegResponse(sregReq, userDataSReg);
            // (alternatively) manually add attribute values
            sregResp.addAttribute("email", user.getEmail());
            responseMessage.addExtension(sregResp);
        }
    }
}
