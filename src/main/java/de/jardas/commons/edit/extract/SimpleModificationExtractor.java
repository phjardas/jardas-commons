package de.jardas.commons.edit.extract;

import com.google.common.base.Objects;

import de.jardas.commons.edit.Modification;
import de.jardas.commons.edit.ModificationContext;

public abstract class SimpleModificationExtractor<T> extends ModificationExtractorSupport<T> {
	public SimpleModificationExtractor(final Class<? super T> requiredType, final boolean acceptBothNull) {
		super(requiredType, acceptBothNull);
	}

	@Override
	public void collectModifications(final String path, final T oldValue, final T newValue,
			final ModificationContext context) {
		if (!isEqual(oldValue, newValue)) {
			final String oldValueString = toString(oldValue);
			final String newValueString = toString(newValue);
			final Modification mod = new Modification(path, oldValueString, newValueString);
			context.collect(mod);
		}
	}

	protected boolean isEqual(final T oldValue, final T newValue) {
		return Objects.equal(oldValue, newValue);
	}

	protected String toString(final T value) {
		return value != null ? value.toString() : null;
	}
}
