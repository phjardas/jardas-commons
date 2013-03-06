package de.jardas.commons.notification.template;

import java.util.Locale;
import java.util.Map;

import de.jardas.commons.Preconditions;

public abstract class TemplateEngineDecorator implements TemplateEngine {
	private final TemplateEngine delegate;

	public TemplateEngineDecorator(final TemplateEngine delegate) {
		this.delegate = Preconditions.notNull(delegate, "delegate");
	}

	@Override
	public String render(final String templateName, final TemplateType type, final Map<String, ?> model,
			final Locale locale) {
		final String content = delegate.render(templateName, type, model, locale);

		return decorate(content, type);
	}

	protected abstract String decorate(final String content, final TemplateType type);
}
