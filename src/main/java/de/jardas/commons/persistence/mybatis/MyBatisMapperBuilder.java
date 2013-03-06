package de.jardas.commons.persistence.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import de.jardas.commons.Preconditions;

public class MyBatisMapperBuilder {
	private final SqlSessionFactory sessionFactory;

	public MyBatisMapperBuilder(final SqlSessionFactory sessionFactory) {
		this.sessionFactory = Preconditions.notNull(sessionFactory, "sessionFactory");
	}

	public <M> M createMapper(final Class<M> mapperType) {
		final Configuration config = sessionFactory.getConfiguration();

		if (!config.hasMapper(mapperType)) {
			config.addMapper(mapperType);
		}

		return new SqlSessionTemplate(sessionFactory).getMapper(mapperType);
	}
}
