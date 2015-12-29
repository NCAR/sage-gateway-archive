package sgf.gateway.model.security.factory;

import sgf.gateway.model.security.User;

public interface UserFactory {

    User createUser(String username, String openId, String firstName, String lastName, String email);

    User createRemoteUser(String openId, String firstName, String lastName, String email);

    User createRemoteUser(String openId);
}
