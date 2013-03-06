package de.jardas.commons.notification.transport;

import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jardas.commons.notification.template.RenderedMessage;

public class MockNotificationTransport implements NotificationTransport {
	private static final Logger LOG = LoggerFactory.getLogger(MockNotificationTransport.class);

	@Override
	public void sendMessage(final InternetAddress sender, final InternetAddress recipient,
			final RenderedMessage message, final MessageCustomizer customizer) {
		LOG.info("Skipping email from {} to {} with subject '{}'\n{}",
				new Object[] { sender, recipient, message.getSubject(), message.getPlain(), });
	}
}
