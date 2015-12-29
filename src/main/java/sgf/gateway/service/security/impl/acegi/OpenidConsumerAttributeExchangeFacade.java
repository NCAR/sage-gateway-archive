package sgf.gateway.service.security.impl.acegi;

import org.springframework.security.openid.OpenIDAttribute;

import java.util.List;

public class OpenidConsumerAttributeExchangeFacade {

    private final List<OpenIDAttribute> attributes;

    public OpenidConsumerAttributeExchangeFacade(final List<OpenIDAttribute> attributes) {

        this.attributes = attributes;
    }

    public String getUsername() {

        String username = null;

        for (OpenIDAttribute openidAttribute : this.attributes) {

            String type = openidAttribute.getType();

            if ("http://axschema.org/namePerson/friendly".equals(type) || "http://openid.net/schema/namePerson/friendly".equals(type) || "http://schema.openid.net/namePerson/friendly".equals(type)) {

                username = openidAttribute.getValues().get(0);
                break;
            }
        }

        return username;
    }

    public String getFirstName() {

        String firstName = null;

        for (OpenIDAttribute openidAttribute : this.attributes) {

            String type = openidAttribute.getType();

            if ("http://axschema.org/namePerson/first".equals(type) || "http://openid.net/schema/namePerson/first".equals(type) || "http://schema.openid.net/contact/firstname".equals(type)) {

                firstName = openidAttribute.getValues().get(0);
                break;
            }
        }

        return firstName;
    }

    public String getLastName() {

        String lastName = null;

        for (OpenIDAttribute openidAttribute : this.attributes) {

            String type = openidAttribute.getType();

            if ("http://axschema.org/namePerson/last".equals(type) || "http://openid.net/schema/namePerson/last".equals(type) || "http://schema.openid.net/contact/lastname".equals(type)) {

                lastName = openidAttribute.getValues().get(0);
                break;
            }
        }

        return lastName;
    }

    public String getEmail() {

        String email = null;

        for (OpenIDAttribute openidAttribute : this.attributes) {

            String type = openidAttribute.getType();

            if ("http://axschema.org/contact/email".equals(type) || "http://openid.net/schema/contact/email".equals(type) || "http://schema.openid.net/contact/email".equals(type)) {

                email = openidAttribute.getValues().get(0);
                break;
            }
        }

        return email;
    }
}
