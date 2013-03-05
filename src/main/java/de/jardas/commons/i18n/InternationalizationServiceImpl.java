package de.jardas.commons.i18n;

import java.lang.reflect.Array;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import de.jardas.commons.Preconditions;

public class InternationalizationServiceImpl implements InternationalizationService {
	private static final Logger LOG = LoggerFactory.getLogger(InternationalizationServiceImpl.class);
	private final MessageSource messageSource;

	public InternationalizationServiceImpl(final MessageSource messageSource) {
		this.messageSource = Preconditions.notNull(messageSource, "messageSource");
	}

	@Override
	public String getLabel(final Internationalizable item, final Locale locale) {
		if (item == null) {
			return null;
		}

		final String key = item.getMessageCode();
		LOG.debug("Internationalizing {} with key {} in locale {}", new Object[] { item, key, locale, });

		return messageSource.getMessage(key, null, locale);
	}

	public <I extends Internationalizable> List<I> sort(final Collection<I> items, final Locale locale) {
		final List<I> ret = new ArrayList<I>(items);
		Collections.sort(ret, new LocalComparator<I>(locale));

		return ret;
	}

	public <I extends Internationalizable> I[] sort(final I[] items, final Locale locale) {
		final List<I> sorted = sort(Arrays.asList(items), locale);

		@SuppressWarnings("unchecked")
		final Class<I> componentType = (Class<I>) items.getClass().getComponentType();

		@SuppressWarnings("unchecked")
		final I[] ret = (I[]) Array.newInstance(componentType, items.length);

		return sorted.toArray(ret);
	}

	private class LocalComparator<I extends Internationalizable> implements Comparator<I> {
		private final Map<I, String> cache = new HashMap<I, String>();
		private final Locale locale;
		private final Collator collator;

		public LocalComparator(final Locale locale) {
			this.locale = locale;
			this.collator = Collator.getInstance(locale);
		}

		@Override
		public int compare(final I o1, final I o2) {
			final String label1 = getLabel(o1);
			final String label2 = getLabel(o2);

			return collator.compare(label1, label2);
		}

		private String getLabel(final I o) {
			if (cache.containsKey(o)) {
				return cache.get(o);
			}

			final String label = InternationalizationServiceImpl.this.getLabel(o, locale);
			cache.put(o, label);
			return label;
		}
	}
}
