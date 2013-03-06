package de.jardas.commons.edit;

import java.util.Collection;

public interface ModificationContext {
	void collect(Modification modification);

	<T> void collect(String path, T oldValue, T newValue);

	<T> void collect(String parentPath, String subPath, T oldValue, T newValue);

	Collection<Modification> getModifications();
}
