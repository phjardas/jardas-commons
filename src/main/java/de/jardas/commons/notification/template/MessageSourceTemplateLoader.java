package de.jardas.commons.notification.template;

import java.text.MessageFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import de.jardas.commons.Preconditions;

public class MessageSourceTemplateLoader implements TemplateLoader {
	private static final Logger LOG = LoggerFactory.getLogger(MessageSourceTemplateLoader.class);
	private final MessageSource messageSource;
	private final MessageFormat messageCodeFormat;

	public MessageSourceTemplateLoader(final MessageSource messageSource, final String messageCodePattern) {
		this.messageSource = Preconditions.notNull(messageSource, "messageSource");
		messageCodeFormat = new MessageFormat(Preconditions.notBlank(messageCodePattern, "messageCodePattern"));
	}

	@Override
	public String loadTemplate(final String templateName, final TemplateType type, final Locale locale) {
		final String messageCode = messageCodeFormat.format(new Object[] { templateName, type, locale, });
		LOG.debug("Loading template with message code '{}'", messageCode);

		return messageSource.getMessage(messageCode, null, locale);
	}
}
