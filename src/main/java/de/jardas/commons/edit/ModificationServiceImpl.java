package de.jardas.commons.edit;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import de.jardas.commons.Preconditions;
import de.jardas.commons.edit.extract.ModificationExtractor;
import de.jardas.commons.edit.extract.ModificationExtractorRegistry;

public class ModificationServiceImpl implements ModificationService {
	private static final Logger LOG = LoggerFactory.getLogger(ModificationServiceImpl.class);
	private final ModificationExtractorRegistry modificationExtractorRegistry;

	public ModificationServiceImpl(final ModificationExtractorRegistry modificationExtractorRegistry) {
		this.modificationExtractorRegistry = Preconditions.notNull(modificationExtractorRegistry,
				"modificationExtractorRegistry");
	}

	@Override
	public <T> Collection<Modification> collectModifications(final T oldValue, final T newValue) {
		final ModificationContextImpl context = new ModificationContextImpl();
		context.collect(null, oldValue, newValue);
		final Collection<Modification> modifications = context.getModifications();

		LOG.debug("Modifications found between old value {} and new value {}: {}", new Object[] { oldValue, newValue,
				modifications, });

		return modifications;
	}

	private final class ModificationContextImpl implements ModificationContext {
		private final List<Modification> modifications = new LinkedList<Modification>();

		@Override
		public void collect(final Modification modification) {
			Preconditions.notNull(modification, "modification");
			LOG.debug("Registering modification: {}", modification);
			modifications.add(modification);
		}

		@Override
		public <T> void collect(final String path, final T oldValue, final T newValue) {
			final ModificationExtractor<T> extractor = modificationExtractorRegistry.findExtractor(oldValue, newValue);

			LOG.trace("Collecting modifications for old value {} and new value {} with {}", new Object[] { oldValue,
					newValue, extractor, });
			extractor.collectModifications(path, oldValue, newValue, this);
		}

		@Override
		public <T> void collect(final String parentPath, final String subPath, final T oldValue, final T newValue) {
			final String path = (parentPath != null ? parentPath + "." : "") + subPath;
			collect(path, oldValue, newValue);
		}

		@Override
		public Collection<Modification> getModifications() {
			return ImmutableList.copyOf(modifications);
		}
	}
}
