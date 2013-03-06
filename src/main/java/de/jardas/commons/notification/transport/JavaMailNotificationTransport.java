package de.jardas.commons.notification.transport;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import de.jardas.commons.Preconditions;
import de.jardas.commons.notification.template.RenderedMessage;

public class JavaMailNotificationTransport implements NotificationTransport {
	private static final Logger LOG = LoggerFactory.getLogger(JavaMailNotificationTransport.class);
	private final JavaMailSender mailSender;

	public JavaMailNotificationTransport(final JavaMailSender mailSender) {
		this.mailSender = Preconditions.notNull(mailSender, "mailSender");
	}

	@Override
	public void sendMessage(final InternetAddress sender, final InternetAddress recipient,
			final RenderedMessage message, final MessageCustomizer customizer) {
		Preconditions.notNull(message, "message");

		final String subject = message.getSubject();
		final String html = message.getHTML();
		final String plain = message.getPlain();

		sendMessage(sender, recipient, subject, html, plain, NoopMessageCustomizer.getDefault(customizer));
	}

	private void sendMessage(final InternetAddress sender, final InternetAddress recipient, final String subject,
			final String html, final String plain, final MessageCustomizer customizer) {
		Preconditions.notNull(sender, "sender");
		Preconditions.notNull(recipient, "recipient");
		Preconditions.notNull(subject, "subject");
		Preconditions.notNull(html, "html");
		Preconditions.notNull(plain, "plain");
		Preconditions.notNull(customizer, "customizer");

		try {
			final MimeMessage message = mailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.getMimeMessage().addHeader("Precedence", "bulk");
			helper.getMimeMessage().addHeader("Auto-Submitted", "auto-generated");
			helper.setFrom(sender);
			helper.setTo(recipient);
			helper.setSubject(subject);
			helper.setText(plain, html);

			if (customizer != null) {
				customizer.customize(helper);
			}

			LOG.info("Sending email from {} to {} with subject '{}':\n{}", new Object[] { sender, recipient, subject,
					html, });
			mailSender.send(message);
		} catch (final MessagingException e) {
			throw new RuntimeException("Error sending email: " + e, e);
		} catch (final IOException e) {
			throw new RuntimeException("Error sending email: " + e, e);
		}
	}
}
