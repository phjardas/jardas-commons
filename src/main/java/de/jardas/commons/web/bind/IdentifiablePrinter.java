package de.jardas.commons.web.bind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import de.jardas.commons.persistence.Identifiable;

public class IdentifiablePrinter implements Converter<Identifiable<?>, String> {
	private static final Logger LOG = LoggerFactory.getLogger(IdentifiablePrinter.class);

	@Override
	public String convert(final Identifiable<?> source) {
		LOG.trace("Converting {} to {}", source, String.class.getName());

		return source != null ? String.valueOf(source.getId()) : null;
	}
}
