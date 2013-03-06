package de.jardas.commons.edit;

import java.util.Collection;

public interface ModificationService {
	<T> Collection<Modification> collectModifications(T oldValue, T newValue);
}
