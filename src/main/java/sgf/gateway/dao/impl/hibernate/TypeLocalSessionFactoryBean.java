package sgf.gateway.dao.impl.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import sgf.gateway.utils.hibernate.URIUserType;
import sgf.gateway.utils.hibernate.UUIDUserType;

public class TypeLocalSessionFactoryBean extends LocalSessionFactoryBean {

    @Override
    protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {

        sfb.registerTypeOverride(new URIUserType(), URIUserType.getRegistrationKeys());
        sfb.registerTypeOverride(new UUIDUserType(), UUIDUserType.getRegistrationKeys());

        return super.buildSessionFactory(sfb);
    }
}
