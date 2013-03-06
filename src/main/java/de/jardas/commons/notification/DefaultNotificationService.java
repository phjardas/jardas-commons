package de.jardas.commons.notification;

import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;

import de.jardas.commons.Preconditions;
import de.jardas.commons.notification.template.NotificationRenderer;
import de.jardas.commons.notification.template.RenderedMessage;
import de.jardas.commons.notification.transport.MessageCustomizer;
import de.jardas.commons.notification.transport.NotificationTransport;

public class DefaultNotificationService implements NotificationService {
	private final NotificationRenderer notificationRenderer;
	private final NotificationTransport notificationTransport;
	private final Map<String, ?> defaultModel;

	@Deprecated
	protected DefaultNotificationService() {
		notificationRenderer = null;
		notificationTransport = null;
		defaultModel = null;
	}

	public DefaultNotificationService(final NotificationRenderer notificationRenderer,
			final NotificationTransport notificationTransport, final Map<String, ?> defaultModel) {
		this.notificationRenderer = Preconditions.notNull(notificationRenderer, "notificationRenderer");
		this.notificationTransport = Preconditions.notNull(notificationTransport, "notificationTransport");

		final Builder<String, Object> defaultModelBuilder = ImmutableMap.<String, Object> builder();

		if (defaultModel != null) {
			defaultModelBuilder.putAll(defaultModel);
		}

		this.defaultModel = defaultModelBuilder.build();
	}

	@Override
	@Transactional
	public Email sendNotification(final InternetAddress sender, final InternetAddress recipient,
			final String templateName, final Map<String, ?> model, final Locale locale,
			final MessageCustomizer customizer) {
		final Map<String, ?> theModel = createModel(model);
		final RenderedMessage message = notificationRenderer.renderMessage(templateName, theModel, locale);
		notificationTransport.sendMessage(sender, recipient, message, customizer);

		return createEmail(sender, recipient, message);
	}

	private Email createEmail(final InternetAddress sender, final InternetAddress recipient,
			final RenderedMessage message) {
		final EmailType type = EmailType.OUT;
		final EmailAddress from = new EmailAddress(sender.getAddress(), sender.getPersonal());
		final EmailAddress to = new EmailAddress(recipient.getAddress(), recipient.getPersonal());

		return new Email(type, from, to, message.getSubject(), message.getHTML(), message.getPlain());
	}

	private Map<String, ?> createModel(final Map<String, ?> model) {
		final Map<String, Object> theModel = Maps.newHashMap(defaultModel);

		if (model != null) {
			theModel.putAll(model);
		}

		return theModel;
	}
}
