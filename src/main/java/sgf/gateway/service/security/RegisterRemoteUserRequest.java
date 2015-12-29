package sgf.gateway.service.security;

public interface RegisterRemoteUserRequest {

    String getOpenid();

    String getFirstName();

    String getLastName();

    String getEmail();
}
