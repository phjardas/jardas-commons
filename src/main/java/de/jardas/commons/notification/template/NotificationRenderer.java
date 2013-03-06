package de.jardas.commons.notification.template;

import java.util.Locale;
import java.util.Map;

public interface NotificationRenderer {
	RenderedMessage renderMessage(String templateName, Map<String, ?> model, Locale locale);
}
