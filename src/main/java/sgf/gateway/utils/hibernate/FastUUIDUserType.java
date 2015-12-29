package sgf.gateway.utils.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;
import org.safehaus.uuid.UUID;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * The Class UUIDUserType.
 *
 * @author wilhelmi
 */
public class FastUUIDUserType implements UserType {

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
    public Object assemble(Serializable cached, Object owner) throws HibernateException {

        return cached;
    }

    /**
     * {@inheritDoc}
     */
    public Object deepCopy(Object value) throws HibernateException {

        return value;
    }

    /**
     * {@inheritDoc}
     */
    public Serializable disassemble(Object value) throws HibernateException {

        return (Serializable) value;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object x, Object y) throws HibernateException {

        if ((x == null) || (y == null)) {
            return false;
        }
        return x.equals(y);
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode(Object x) throws HibernateException {

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
    public Object replace(Object original, Object target, Object owner) throws HibernateException {

        return original;
    }

    /**
     * {@inheritDoc}
     */
    public int[] sqlTypes() {

        return new int[]{Types.OTHER};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object owner) throws HibernateException, SQLException {

        String value = (String) StringType.INSTANCE.get(resultSet, names[0], sessionImplementor);
        UUID result = null;

        if ((null != value) && (value.length() != 0)) {
            result = new UUID(value);

        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {

        if (value == null) {
            statement.setNull(index, Types.OTHER);
        } else {
            statement.setObject(index, new PostgresUUID(value.toString()));
        }
    }

    /**
     * {@inheritDoc}
     */
    public Class<UUID> returnedClass() {

        return UUID.class;
    }

}
