package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;

import java.util.List;

public class UserOpenidReminderMailMessage extends FreeMarkerMailMessage {

    public UserOpenidReminderMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    // User can have multiple openIds
    public void setUsers(List<User> userAccounts) {

        super.addObject("userAccounts", userAccounts);
    }
}
