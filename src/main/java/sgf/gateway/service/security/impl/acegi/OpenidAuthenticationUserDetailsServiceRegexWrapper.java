package sgf.gateway.service.security.impl.acegi;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenidAuthenticationUserDetailsServiceRegexWrapper implements UserDetailsService, MessageSourceAware {

    private final Pattern commonNamePattern;

    private final UserDetailsService userDetailsService;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public OpenidAuthenticationUserDetailsServiceRegexWrapper(String commonNameRegex, UserDetailsService userDetailsService) {

        this.commonNamePattern = Pattern.compile(commonNameRegex, Pattern.CASE_INSENSITIVE);
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String distinguishedName) throws UsernameNotFoundException, DataAccessException {

        String commonName = this.getCommonName(distinguishedName);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(commonName);

        return userDetails;
    }

    String getCommonName(String distinguishedName) {

        Matcher matcher = commonNamePattern.matcher(distinguishedName);

        // The following code was copied from what is found in Spring's SubjectDnX509PrincipalExtractor class.
        if (!matcher.find()) {

            throw new BadCredentialsException(messages.getMessage("DaoX509AuthoritiesPopulator.noMatching",
                    new Object[]{distinguishedName}, "No matching pattern was found in subject DN: {0}"));
        }

        if (matcher.groupCount() != 1) {

            throw new IllegalArgumentException("Regular expression must contain a single group ");
        }

        String commonName = matcher.group(1);

        return commonName;
    }

    public void setMessageSource(MessageSource messageSource) {

        this.messages = new MessageSourceAccessor(messageSource);
    }
}
