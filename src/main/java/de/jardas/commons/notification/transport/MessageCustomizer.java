package de.jardas.commons.notification.transport;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;

public interface MessageCustomizer {
	void customize(MimeMessageHelper message) throws IOException, MessagingException;
}
