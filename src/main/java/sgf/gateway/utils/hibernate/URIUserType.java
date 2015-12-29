package sgf.gateway.utils.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * The Class URIUserType.
 *
 * @author wilhelmi
 */
public class URIUserType implements UserType {

    public static String[] getRegistrationKeys() {
        return new String[]{URI.class.getName()};
    }

    /**
     * Sets the parameter values.
     *
     * @param parameters the new parameter values
     */
    public void setParameterValues(Properties parameters) {

    }

    /**
     * {@inheritDoc}
     */
    public Object assemble(Serializable cached, Object owner) {

        return cached;
    }

    /**
     * {@inheritDoc}
     */
    public Object deepCopy(Object value) {

        return value;
    }

    /**
     * {@inheritDoc}
     */
    public Serializable disassemble(Object value) {

        return (Serializable) value;
    }

    /**
     * {@inheritDoc}
     *
     * @return <code>true</code> means that the value has not changed.<br>
     * <code>false</code> means that the value has changed and is dirty.
     */
    public boolean equals(Object x, Object y) {

        boolean test = false;

        if ((x == null) && (y == null)) {

            test = true;

        } else {

            if ((x != null) && (y != null)) {

                if ((x instanceof URI) && (y instanceof URI)) {

                    test = x.equals(y);
                }
            }
        }

        return test;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode(Object x) {

        return x.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isMutable() {

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public Object replace(Object original, Object target, Object owner) {

        return original;
    }

    /**
     * {@inheritDoc}
     */
    public int[] sqlTypes() {

        return new int[]{Types.VARCHAR};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object owner) throws HibernateException, SQLException {

        String value = (String) StringType.INSTANCE.get(resultSet, names[0], sessionImplementor);
        URI result;

        try {

            if ((null != value) && (value.length() != 0)) {

                result = new URI(value);

            } else {

                result = new URI("");

            }

        } catch (URISyntaxException e) {

            throw new HibernateException("Retrieved an invalid URI reference value: " + value, e);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {

        if (value == null) {

            statement.setNull(index, Types.VARCHAR);

        } else {

            statement.setString(index, value.toString());
        }
    }

    /**
     * {@inheritDoc}
     */
    public Class<URI> returnedClass() {

        return URI.class;
    }

}
