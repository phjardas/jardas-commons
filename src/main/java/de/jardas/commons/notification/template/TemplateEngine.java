package de.jardas.commons.notification.template;

import java.util.Locale;
import java.util.Map;

public interface TemplateEngine {
	String render(String templateName, TemplateType type, Map<String, ?> model, Locale locale);
}
