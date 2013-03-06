package de.jardas.commons.notification.template;

import java.util.Locale;
import java.util.Map;

import de.jardas.commons.Preconditions;

public class TemplateEngineNotificationRenderer implements NotificationRenderer {
	private final TemplateEngine templateEngine;

	public TemplateEngineNotificationRenderer(final TemplateEngine templateEngine) {
		this.templateEngine = Preconditions.notNull(templateEngine, "templateEngine");
	}

	@Override
	public RenderedMessage renderMessage(final String templateName, final Map<String, ?> model, final Locale locale) {
		final MailDocument doc = renderTemplate(templateName, model, locale);

		return new RenderedMessage(doc.getSubject(), doc.getHtml(), doc.getPlain());
	}

	private MailDocument renderTemplate(final String templateName, final Map<String, ?> model, final Locale locale) {
		final String html = renderTemplate(templateName, TemplateType.HTML, model, locale);
		final String plain = renderTemplate(templateName, TemplateType.PLAIN, model, locale);
		final String subject = renderTemplate(templateName, TemplateType.SUBJECT, model, locale).trim();

		return new MailDocument(subject, html, plain);
	}

	private String renderTemplate(final String templateName, final TemplateType type, final Map<String, ?> model,
			final Locale locale) {
		return templateEngine.render(templateName, type, model, locale);
	}
}
