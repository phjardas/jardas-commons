package de.jardas.commons.i18n;

import java.util.Locale;

public interface InternationalizationService {
	String getLabel(Internationalizable value, Locale locale);
}
