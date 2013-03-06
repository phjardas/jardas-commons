package de.jardas.commons.notification.transport;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;

final class NoopMessageCustomizer implements MessageCustomizer {
	private static final NoopMessageCustomizer INSTANCE = new NoopMessageCustomizer();

	private NoopMessageCustomizer() {
	}

	public static NoopMessageCustomizer getInstance() {
		return INSTANCE;
	}

	public static MessageCustomizer getDefault(final MessageCustomizer customizer) {
		return customizer != null ? customizer : getInstance();
	}

	@Override
	public void customize(final MimeMessageHelper message) throws IOException, MessagingException {
		// noop
	}
}
