package de.jardas.commons.notification.template;

import java.util.Locale;

public interface TemplateLoader {
	String loadTemplate(String templateName, TemplateType type, Locale locale);
}
