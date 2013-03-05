package de.jardas.commons.persistence.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

@MappedTypes({ ReadableInstant.class, ReadableInstant.class })
public class MyBatisJodaDateTimeType extends BaseTypeHandler<ReadableInstant> {
	@Override
	public void setNonNullParameter(final PreparedStatement ps, final int i, final ReadableInstant parameter,
			final JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, new java.sql.Timestamp(parameter.getMillis()));
	}

	@Override
	public ReadableInstant getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
		final java.sql.Timestamp sqlTimestamp = rs.getTimestamp(columnName);
		return createInstant(sqlTimestamp);
	}

	@Override
	public ReadableInstant getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
		final java.sql.Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
		return createInstant(sqlTimestamp);
	}

	@Override
	public ReadableInstant getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
		final java.sql.Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
		return createInstant(sqlTimestamp);
	}

	private ReadableInstant createInstant(final java.sql.Timestamp sqlTimestamp) {
		if (sqlTimestamp != null) {
			return new DateTime(sqlTimestamp.getTime());
		}

		return null;
	}
}
