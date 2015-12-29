package sgf.gateway.service.security.impl.acegi;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import java.util.Collection;
import java.util.List;

public class NewUserOpenIDAuthenticationToken extends OpenIDAuthenticationToken {

    private static final long serialVersionUID = 1L;

    public NewUserOpenIDAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities, String identityUrl, List<OpenIDAttribute> attributes) {

        super(principal, authorities, identityUrl, attributes);
    }

}
