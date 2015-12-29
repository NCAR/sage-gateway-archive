package sgf.gateway.service.security.impl.acegi;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;

public class OpenidAuthenticationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public OpenidAuthenticationUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException, DataAccessException {

        User user = this.userRepository.findUserByOpenid(openid);

        UserDetails userDetails;

        if (user != null) {

            userDetails = new AcegiUserDetails(user);

        } else {

            userDetails = new OpenidNewUserDetails();
        }

        return userDetails;
    }

}
