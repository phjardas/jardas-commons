package de.jardas.commons.i18n;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class LocaleType implements UserType {
	private static final int[] SQL_TYPES = new int[] { Types.VARCHAR, };

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class<?> returnedClass() {
		return Locale.class;
	}

	@Override
	public boolean equals(final Object x, final Object y) throws HibernateException {
		return x instanceof Locale && y instanceof Locale && x.equals(y);
	}

	@Override
	public int hashCode(final Object x) throws HibernateException {
		return x instanceof Locale ? x.hashCode() : 0;
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session,
			final Object owner) throws HibernateException, SQLException {
		final String value = rs.getString(names[0]);

		return StringUtils.isNotBlank(value) ? LocaleUtils.toLocale(value) : Locale.ROOT;
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
			final SessionImplementor session) throws HibernateException, SQLException {
		st.setString(index, value instanceof Locale ? ((Locale) value).toString() : null);
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return value instanceof Locale ? (Locale) value : null;
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
		return cached instanceof Locale ? cached : null;
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}
}
