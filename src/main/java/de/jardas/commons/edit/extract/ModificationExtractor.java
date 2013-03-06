package de.jardas.commons.edit.extract;

import org.springframework.core.Ordered;

import de.jardas.commons.edit.ModificationContext;

public interface ModificationExtractor<T> extends Ordered {
	boolean accepts(Object oldValue, Object newValue);

	void collectModifications(String path, T oldValue, T newValue, ModificationContext context);
}
