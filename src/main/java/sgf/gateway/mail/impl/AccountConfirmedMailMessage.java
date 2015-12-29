package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;

public class AccountConfirmedMailMessage extends FreeMarkerMailMessage {

    public AccountConfirmedMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void setUser(User user) {

        super.addObject("user", user);
    }
}
