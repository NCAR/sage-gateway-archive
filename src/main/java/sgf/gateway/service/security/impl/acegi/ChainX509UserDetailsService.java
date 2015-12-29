package sgf.gateway.service.security.impl.acegi;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ChainX509UserDetailsService implements UserDetailsService, MessageSourceAware {

    private final List<UserDetailsService> userDetailsServices;

    private boolean enforce = true;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public ChainX509UserDetailsService(List<UserDetailsService> userDetailsServices) {

        this.userDetailsServices = userDetailsServices;
    }

    @Override
    public UserDetails loadUserByUsername(String distinguishedName) throws UsernameNotFoundException, DataAccessException {

        UserDetails userDetails = null;

        if (enforce) {

            userDetails = loadUserByUsernameInternal(distinguishedName);

        }

        return userDetails;
    }

    UserDetails loadUserByUsernameInternal(String distinguishedName) throws UsernameNotFoundException, DataAccessException {

        UserDetails userDetails = null;

        for (UserDetailsService userDetailsService : this.userDetailsServices) {

            try {

                userDetails = userDetailsService.loadUserByUsername(distinguishedName);

            } catch (UsernameNotFoundException e) {

            }

            if (userDetails != null) {

                break;
            }
        }

        if (userDetails == null) {

            throw new UsernameNotFoundException(this.messages.getMessage("ChainX509UserDetailsService.badCredentials", "Untrusted x509 Certificate"));
        }

        return userDetails;
    }

    public void setEnforce(boolean enforce) {

        this.enforce = enforce;
    }

    public void setMessageSource(MessageSource messageSource) {

        this.messages = new MessageSourceAccessor(messageSource);
    }
}
