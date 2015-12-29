package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;

public class AdminRegistrationMailMessage extends FreeMarkerMailMessage {

    public AdminRegistrationMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void setUser(User user) {

        super.addObject("user", user);
    }

    public void setPortalUrl(String portalUrl) {

        super.addObject("portalUrl", portalUrl);
    }

}
