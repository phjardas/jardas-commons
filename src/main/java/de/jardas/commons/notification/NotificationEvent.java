package de.jardas.commons.notification;

import com.google.common.base.Objects;

import de.jardas.commons.Preconditions;
import de.jardas.commons.event.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private final Email email;

	public NotificationEvent(final Email email) {
		super(Preconditions.notNull(email, "email").getCreatedAt());
		this.email = email;
	}

	public Email getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("timestamp", getTimestamp()).add("email", email).toString();
	}
}
