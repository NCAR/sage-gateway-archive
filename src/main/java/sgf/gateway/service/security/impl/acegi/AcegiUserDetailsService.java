package sgf.gateway.service.security.impl.acegi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.security.GuestUser;
import sgf.gateway.model.security.User;
import sun.security.x509.X509CertImpl;

/**
 * Implementation of UserDetailsService that wraps the ESKE User object into an Acegi UserDetails object. This class
 * implements the interface methods that are invoked by the Spring framework for the two cases of form-based
 * authentication (either via openid or username and password) and certificate-based authentication.
 */
public class AcegiUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService {

    /**
     * The Constant LOG.
     */
    private static final Log LOG = LogFactory.getLog(AcegiUserDetailsService.class);

    private final UserRepository userRepository;

    public AcegiUserDetailsService(final UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Method invoked to load a User object by username in the case of form-based authentication.
     *
     * @param userName : either the user's openid, or the gateway-specific username.
     */
    public UserDetails loadUserByUsername(String userName) throws AuthenticationException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Retrieving user object for userName=" + userName);
        }

        User user;
        if (userName.equals(GuestUser.GUEST_USERNAME)) {
            // retrieve Guest user
            user = GuestUser.getInstance();

        } else {

            // retrieve User object by username or openid, wrap it for Acegi
            try {

                // OpenID authentication
                if (userName.startsWith("http")) {
                    user = this.userRepository.findUserByOpenid(userName);
                    // non-OpenID authentication
                } else {
                    user = this.userRepository.findUserByUserName(userName);
                }

            } catch (final Exception e) {
                throw new BadCredentialsException("User: " + userName + " not found", e);
            }

        } // user not guest

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("Cannot find user with username=" + userName);
        }
        return new AcegiUserDetails(user);
    }

    /**
     * Method invoked to load a User object in the case of certificate-based authentication.
     *
     * @param authentication : the {@link Authentication} object that contains the X509 certificate.
     */
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {

        // NOTE: the principal is extracted from the certificate subject according to the application rules
        // (by default, it is the CN part of the certificate subject)
        String principal = authentication.getPrincipal().toString();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Retrieving user object for principal=" + principal);
        }

        // inspect X509 certificate
        X509CertImpl credentials = (X509CertImpl) authentication.getCredentials();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Credentials=" + credentials.toString());
        }

        return loadUserByUsername(principal);
    }

}
