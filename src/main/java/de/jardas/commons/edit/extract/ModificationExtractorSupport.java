package de.jardas.commons.edit.extract;

import org.springframework.core.Ordered;

public abstract class ModificationExtractorSupport<T> implements ModificationExtractor<T> {
	private final boolean acceptBothNull;
	private final Class<? super T> requiredType;

	public ModificationExtractorSupport(final Class<? super T> requiredType, final boolean acceptBothNull) {
		this.requiredType = requiredType;
		this.acceptBothNull = acceptBothNull;
	}

	@Override
	public boolean accepts(final Object oldValue, final Object newValue) {
		if (!acceptBothNull && oldValue == null && newValue == null) {
			return false;
		}

		return (oldValue == null || requiredType.isInstance(oldValue))
				&& (newValue == null || requiredType.isInstance(newValue));
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
