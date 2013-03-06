package de.jardas.commons.edit.extract;

import java.util.Locale;

import de.jardas.commons.Preconditions;
import de.jardas.commons.i18n.Internationalizable;
import de.jardas.commons.i18n.InternationalizationService;

public class InternationalizableModificationExtractor extends SimpleModificationExtractor<Internationalizable> {
	private final InternationalizationService internationalizationService;

	public InternationalizableModificationExtractor(final InternationalizationService internationalizationService) {
		super(Internationalizable.class, false);
		this.internationalizationService = Preconditions.notNull(internationalizationService,
				"internationalizationService");
	}

	@Override
	protected String toString(final Internationalizable value) {
		if (value == null) {
			return null;
		}

		return internationalizationService.getLabel(value, Locale.getDefault());
	}

	@Override
	public int getOrder() {
		return super.getOrder() - 1;
	}
}
