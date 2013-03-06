package de.jardas.commons.notification;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import de.jardas.commons.notification.transport.MessageCustomizer;

public interface NotificationService {
	Email sendNotification(InternetAddress sender, InternetAddress recipient, String templateName,
			Map<String, ?> model, Locale locale, MessageCustomizer customizer);
}
