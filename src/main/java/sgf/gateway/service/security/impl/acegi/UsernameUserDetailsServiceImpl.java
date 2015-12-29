package sgf.gateway.service.security.impl.acegi;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.User;

public class UsernameUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UsernameUserDetailsServiceImpl(final UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

        AcegiUserDetails acegiUserDetails;

        User user = this.userRepository.findUserByUserName(username);

        if (user != null) {

            acegiUserDetails = new AcegiUserDetails(user);

        } else {

            throw new UsernameNotFoundException(username);
        }

        return acegiUserDetails;
    }
}
