package de.jardas.commons.spring;

import org.springframework.format.datetime.joda.JodaTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;

public final class ConversionServiceBuilder {
	private ConversionServiceBuilder() {
	}

	public static DefaultFormattingConversionService buildConversionService() {
		final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

		// Joda
		final JodaTimeFormatterRegistrar joda = new JodaTimeFormatterRegistrar();
		joda.setUseIsoFormat(true);
		joda.registerFormatters(conversionService);

		return conversionService;
	}
}
