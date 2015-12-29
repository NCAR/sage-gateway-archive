package sgf.gateway.utils.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * The Class EnumUserType.
 *
 * @author wilhelmi
 */
public class EnumUserType implements EnhancedUserType, ParameterizedType {

    /**
     * The ORDINA l_ fo r_ null.
     */
    private static final int ORDINAL_FOR_NULL = -1;

    /**
     * The enum class.
     */
    private Class<Enum> enumClass;

    /**
     * The use name.
     */
    private boolean useName;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void setParameterValues(final Properties parameters) {

        String enumClassName = parameters.getProperty("enumClassName").trim();
        try {
            this.enumClass = (Class<Enum>) Class.forName(enumClassName);
        } catch (final ClassNotFoundException cnfe) {
            throw new HibernateException("Enum class not found", cnfe);
        }
        String columnType = parameters.getProperty("columnType").trim();
        this.useName = !columnType.equalsIgnoreCase("INTEGER");

    }

    /**
     * {@inheritDoc}
     */
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {

        return cached;
    }

    /**
     * {@inheritDoc}
     */
    public Object deepCopy(final Object value) throws HibernateException {

        return value;
    }

    /**
     * {@inheritDoc}
     */
    public Serializable disassemble(final Object value) throws HibernateException {

        return (Enum) value;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(final Object x, final Object y) throws HibernateException {

        return x == y;
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode(final Object x) throws HibernateException {

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
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {

        return original;
    }

    /**
     * {@inheritDoc}
     */
    public Class returnedClass() {

        return this.enumClass;
    }

    /**
     * {@inheritDoc}
     */
    public int[] sqlTypes() {

        return new int[]{this.useName ? Types.VARCHAR : Types.INTEGER};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object owner) throws HibernateException, SQLException {

        if (this.useName) {
            String name = resultSet.getString(names[0]);
            return resultSet.wasNull() ? null : Enum.valueOf(this.enumClass, name);
        } else {
            int ordinal = resultSet.getInt(names[0]);
            if (resultSet.wasNull() || (ordinal == EnumUserType.ORDINAL_FOR_NULL)) {
                return null;
            } else {
                return this.enumClass.getEnumConstants()[ordinal];
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {

        if (this.useName) {
            if (value == null) {
                statement.setNull(index, Types.VARCHAR);
            } else {

                String string = ((Enum) value).name();

                statement.setString(index, string);
            }
        } else {
            if (value == null) {
                statement.setInt(index, EnumUserType.ORDINAL_FOR_NULL);
            } else {
                statement.setInt(index, ((Enum) value).ordinal());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object fromXMLString(final String xmlValue) {

        if (this.useName) {
            return Enum.valueOf(this.enumClass, xmlValue);
        } else {
            int ordinal = Integer.parseInt(xmlValue);
            return this.enumClass.getEnumConstants()[ordinal];
        }
    }

    /**
     * {@inheritDoc}
     */
    public String objectToSQLString(final Object value) {

        if (this.useName) {
            return '\'' + ((Enum) value).name() + '\'';
        } else {
            return String.valueOf(((Enum) value).ordinal());
        }
    }

    /**
     * {@inheritDoc}
     */
    public String toXMLString(final Object value) {

        if (this.useName) {
            return ((Enum) value).name();
        } else {
            return String.valueOf(((Enum) value).ordinal());
        }
    }

}
