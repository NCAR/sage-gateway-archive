package sgf.gateway.web.controllers.security;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.PasswordCorrect;
import sgf.gateway.validation.type.FieldMatch;

import javax.validation.GroupSequence;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, ChangePasswordCommand.class})
@FieldMatch(groups = Data.class, first = "password", second = "confirmNewPassword", message = "The New Password and Confirm New Password fields must match")
public class ChangePasswordCommand {

    @NotBlank(groups = Required.class, message = "Current Password is required.")
    @PasswordCorrect(groups = Persistence.class, message = "Incorrect Current Password")
    private String oldPassword;

    @NotBlank(groups = Required.class, message = "New Password is required.")
    @Length(groups = Data.class, min = 6, message = "New Password must be at least 6 characters in length")
    private String password;

    @NotBlank(groups = Required.class, message = "Confirm New Password is required.")
    private String confirmNewPassword;

    public void setOldPassword(String oldPassword) {

        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {

        return this.oldPassword;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getPassword() {

        return this.password;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {

        this.confirmNewPassword = confirmNewPassword;
    }

    public String getConfirmNewPassword() {

        return this.confirmNewPassword;
    }
}
