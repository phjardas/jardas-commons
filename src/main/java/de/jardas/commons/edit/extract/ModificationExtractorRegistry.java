package de.jardas.commons.edit.extract;

import static java.lang.String.format;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.jardas.commons.spring.OrderedComparator;

public class ModificationExtractorRegistry {
	private final List<ModificationExtractor<?>> extractors = new LinkedList<ModificationExtractor<?>>();

	public ModificationExtractorRegistry(final Collection<ModificationExtractor<?>> extractors) {
		if (extractors != null) {
			this.extractors.addAll(extractors);
			OrderedComparator.sort(this.extractors);
		}
	}

	public <T> ModificationExtractor<T> findExtractor(final T oldValue, final T newValue) {
		for (final ModificationExtractor<?> extractor : extractors) {
			if (extractor.accepts(oldValue, newValue)) {
				@SuppressWarnings("unchecked")
				final ModificationExtractor<T> ex = (ModificationExtractor<T>) extractor;
				return ex;
			}
		}

		throw new IllegalArgumentException(format("No modification extractor found for old value %s and new value %s",
				oldValue, newValue));
	}
}
