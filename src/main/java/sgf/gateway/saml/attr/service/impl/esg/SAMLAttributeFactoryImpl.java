package sgf.gateway.saml.attr.service.impl.esg;

import esg.saml.attr.service.api.SAMLAttributeFactory;
import esg.saml.attr.service.api.SAMLAttributes;
import esg.saml.attr.service.impl.SAMLAttributesImpl;
import esg.saml.common.SAMLUnknownPrincipalException;
import sgf.gateway.dao.security.UserRepository;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.Membership;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.impl.acegi.AcegiUtils;

/**
 * Implementation of {@link SAMLAttributesFactory} specific to ESG domain model.
 * Note that this implementation will release personal information only for users that registered at this Gateway,
 * and will release access control attributes only for groups that are controlled by this Gateway.
 */
public class SAMLAttributeFactoryImpl implements SAMLAttributeFactory {

    private final UserRepository userRepository;

    /**
     * Authority that issues the attributes.
     */
    private final Gateway gateway;

    public SAMLAttributeFactoryImpl(UserRepository userRepository, Gateway gateway) {

        this.userRepository = userRepository;
        this.gateway = gateway;
    }

    /**
     * {@inheritDoc}
     * Note that this implementation uses the OpenID identifier to look up the user.
     */
    public SAMLAttributes newInstance(String openid) throws SAMLUnknownPrincipalException {

        User user = this.userRepository.findUserByOpenid(openid);

        if (user == null) {

            throw new SAMLUnknownPrincipalException("Unknown user with identifier= " + openid);

        } else {

            SAMLAttributes samlAttributes = new SAMLAttributesImpl();

            // user information - only if user registered at this gateway

            // FIXME we shouldn't send a remote User's information?
            samlAttributes.setFirstName(user.getFirstName());
            samlAttributes.setLastName(user.getLastName());
            samlAttributes.setOpenid(user.getOpenid());
            samlAttributes.setEmail(user.getEmail());

            // FIXME GATEWAY how will we determine which memberships to send?
            // access control attributes - only for groups controlled by this gateway
            for (Membership membership : user.getMemberships()) {

                samlAttributes.getAttributes().add(AcegiUtils.getAuthority(membership.getGroup().getName(), membership.getRole().getName()));
            }

            // authority
            samlAttributes.setIssuer(this.getIssuer());

            return samlAttributes;
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getIssuer() {

        return this.gateway.getIdentity();
    }
}
