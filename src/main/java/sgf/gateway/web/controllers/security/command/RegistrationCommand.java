package sgf.gateway.web.controllers.security.command;

import sgf.gateway.service.security.RegisterUserRequest;

public class RegistrationCommand implements RegisterUserRequest {

    private String firstName;

    private String lastName;
    private String email;
    private String organization;

    private String username;
    private String password;
    private String confirmationPassword;

    public void setFirstName(final String firstName) {

        this.firstName = firstName;
    }

    @Override
    public String getFirstName() {

        return this.firstName;
    }

    public void setLastName(final String lastName) {

        this.lastName = lastName;
    }

    @Override
    public String getLastName() {

        return this.lastName;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    @Override
    public String getEmail() {

        return this.email;
    }


    public void setOrganization(final String organization) {

        this.organization = organization;
    }

    @Override
    public String getOrganization() {

        return this.organization;
    }

    public void setUsername(final String username) {

        this.username = username;
    }

    @Override
    public String getUsername() {

        return this.username;
    }

    public void setPassword(final String password) {

        this.password = password;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    public void setConfirmationPassword(final String confirmationPassword) {

        this.confirmationPassword = confirmationPassword;
    }

    public String getConfirmationPassword() {

        return this.confirmationPassword;
    }
}