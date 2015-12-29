package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AdminGroupRegistrationMailMessage extends FreeMarkerMailMessage {

    private static final String ENCODING = "UTF-8";

    public AdminGroupRegistrationMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void setUser(User user) {
        super.addObject("user", user);
    }

    public void setGroupName(String groupName) {
        super.addObject("groupName", groupName);
    }

    public void setClickUrlParams(User user, String groupName) {
        // encode url
        // ac/admin/manageGroupUser.htm?identifier=${user.identifier}&groupName=${groupName}
        String clickUrlParams;
        try {
            clickUrlParams = "identifier=" + URLEncoder.encode(user.getIdentifier().toString(), ENCODING) + "&groupName=" + URLEncoder.encode(groupName, ENCODING);
        } catch (UnsupportedEncodingException e) {
            // Wrapper
            throw new RuntimeException(e.getMessage());
        }

        super.addObject("clickUrlParams", clickUrlParams);
    }
}
