package sgf.gateway.service.security;

public interface RegisterUserRequest {

    String getFirstName();

    String getLastName();

    String getEmail();

    String getOrganization();

    String getUsername();

    String getPassword();
}
