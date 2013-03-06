package de.jardas.commons.notification.transport;

import javax.mail.internet.InternetAddress;

import de.jardas.commons.notification.template.RenderedMessage;

public interface NotificationTransport {
	void sendMessage(InternetAddress sender, InternetAddress recipient, RenderedMessage message,
			MessageCustomizer customizer);
}
