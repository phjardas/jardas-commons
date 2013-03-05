package de.jardas.commons.persistence.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBatisMapperBuilder {
	@Autowired
	private SqlSessionFactory sessionFactory;

	public <M> M createMapper(final Class<M> mapperType) {
		final Configuration config = sessionFactory.getConfiguration();

		if (!config.hasMapper(mapperType)) {
			config.addMapper(mapperType);
		}

		return new SqlSessionTemplate(sessionFactory).getMapper(mapperType);
	}
}
