package de.jardas.commons.edit.extract;

import java.beans.PropertyDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import de.jardas.commons.edit.ModificationContext;

public class PropertyModificationExtractor<T> extends ModificationExtractorSupport<T> {
	private static final Logger LOG = LoggerFactory.getLogger(PropertyModificationExtractor.class);
	private final String[] properties;

	public PropertyModificationExtractor(final Class<? super T> requiredType, final boolean acceptBothNull,
			final String... properties) {
		super(requiredType, acceptBothNull);
		this.properties = properties;
	}

	@Override
	public void collectModifications(final String path, final T oldValue, final T newValue,
			final ModificationContext context) {
		for (final String property : properties) {
			final Object oldPropertyValue = getValue(oldValue, property);
			final Object newPropertyValue = getValue(newValue, property);
			context.collect(path, property, oldPropertyValue, newPropertyValue);
		}
	}

	private Object getValue(final T value, final String property) {
		if (value == null) {
			return null;
		}

		final PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(value.getClass(), property);

		if (descriptor == null) {
			return null;
		}

		try {
			return descriptor.getReadMethod().invoke(value);
		} catch (final Exception e) {
			LOG.error("Error resolving value for property '" + property + "' of " + value + ": " + e, e);
			return null;
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
