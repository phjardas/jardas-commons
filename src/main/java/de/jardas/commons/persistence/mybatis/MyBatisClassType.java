package de.jardas.commons.persistence.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(Class.class)
public class MyBatisClassType extends BaseTypeHandler<Class<?>> {
	@Override
	public void setNonNullParameter(final PreparedStatement ps, final int i, final Class<?> parameter,
			final JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public Class<?> getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
		final String className = rs.getString(columnName);
		return toClass(className);
	}

	@Override
	public Class<?> getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
		final String className = cs.getString(columnIndex);
		return toClass(className);
	}

	@Override
	public Class<?> getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
		final String className = rs.getString(columnIndex);
		return toClass(className);
	}

	private Class<?> toClass(final String className) throws SQLException {
		if (className != null) {
			try {
				return Class.forName(className);
			} catch (final ClassNotFoundException e) {
				throw new SQLException("Error loading class '" + className + "': " + e, e);
			}
		}

		return null;
	}
}
