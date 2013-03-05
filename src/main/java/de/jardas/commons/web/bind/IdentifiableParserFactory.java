package de.jardas.commons.web.bind;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import de.jardas.commons.persistence.Identifiable;

public class IdentifiableParserFactory implements ConverterFactory<String, Identifiable<?>> {
	private static final Logger LOG = LoggerFactory.getLogger(IdentifiableParserFactory.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <T extends Identifiable<?>> Converter<String, T> getConverter(final Class<T> targetType) {
		LOG.trace("Creating IdentifiableParserFactory for target {}", targetType);
		return new IdentifiableParser<T>(targetType);
	}

	private class IdentifiableParser<T extends Identifiable<?>> implements Converter<String, T> {
		private final Class<T> entityType;

		public IdentifiableParser(final Class<T> entityType) {
			this.entityType = entityType;
		}

		@Override
		public T convert(final String source) {
			LOG.debug("Converting {} to {}", source, entityType.getName());

			final T value = doConvert(source);
			LOG.debug("Converted {} to {}", source, value);

			return value;
		}

		private T doConvert(final String source) {
			if (StringUtils.isBlank(source)) {
				return null;
			}

			final T entity = entityManager.find(entityType, source);

			if (entity == null) {
				LOG.info("Could not resolve entity of type {} with ID {}", entityType.getName(), source);
			}

			return entity;
		}
	}
}
