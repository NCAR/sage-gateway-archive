package sgf.gateway.web.controllers.security.openid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.service.security.RegisterRemoteUserRequest;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;

import javax.validation.GroupSequence;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, OpenidRegistrationCommand.class})
public class OpenidRegistrationCommand implements RegisterRemoteUserRequest {

    @NotBlank(groups = Required.class, message = "OpenID is required.")
    private String openid;

    @NotBlank(groups = Required.class, message = "First Name is required.")
    private String firstName;

    @NotBlank(groups = Required.class, message = "Last Name is required.")
    private String lastName;

    @NotBlank(groups = Required.class, message = "Email is required.")
    @Email(groups = Type.class, message = "Email is not a valid email address")
    private String email;

    public void setOpenid(String openid) {

        this.openid = openid;
    }

    public String getOpenid() {

        return this.openid;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getFirstName() {

        return this.firstName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getLastName() {

        return this.lastName;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getEmail() {

        return this.email;
    }
}
