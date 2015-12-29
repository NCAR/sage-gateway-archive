package sgf.gateway.web.controllers.security.command;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.ChangeEmailRequest;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, ChangeEmailCommand.class})
public class ChangeEmailCommand implements ChangeEmailRequest {

    private User user;

    @NotBlank(groups = Required.class, message = "New Email Address is required.")
    @Email(groups = Type.class, message = "New Email Address is not a valid email address.")
    private String email;

    public ChangeEmailCommand(User user) {

        this.user = user;
    }

    public UUID getUserId() {

        return this.user.getIdentifier();
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEmail() {

        return this.email;
    }
}
