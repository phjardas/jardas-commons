package de.jardas.commons.notification.template;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import de.jardas.commons.Preconditions;

public class VelocityTemplateEngine implements TemplateEngine {
	private final VelocityEngine velocity;
	private final TemplateLoader templateLoader;

	public VelocityTemplateEngine(final VelocityEngine velocity, final TemplateLoader templateLoader) {
		this.velocity = Preconditions.notNull(velocity, "velocity");
		this.templateLoader = Preconditions.notNull(templateLoader, "templateLoader");
	}

	@Override
	public String render(final String templateName, final TemplateType type, final Map<String, ?> model,
			final Locale locale) {
		final Context context = new VelocityContext(model);
		final StringWriter out = new StringWriter();
		final String logTag = templateName + "." + type;
		final String template = templateLoader.loadTemplate(templateName, type, locale);
		velocity.evaluate(context, out, logTag, template);

		return out.toString();
	}
}
