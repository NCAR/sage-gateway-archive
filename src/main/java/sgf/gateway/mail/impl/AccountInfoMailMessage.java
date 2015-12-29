package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;

/**
 * General mail message containing user account info.
 * Fields are used by email templates.
 */
public class AccountInfoMailMessage extends FreeMarkerMailMessage {

    public AccountInfoMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void setUser(User user) {

        super.addObject("user", user);
    }

    public void setPassword(String newPassword) {

        super.addObject("password", newPassword);
    }

}
