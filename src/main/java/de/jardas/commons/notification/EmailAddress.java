package de.jardas.commons.notification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.jardas.commons.Preconditions;

@Embeddable
public class EmailAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, updatable = false)
	private final String email;

	@Column(nullable = true, updatable = false)
	private final String name;

	@SuppressWarnings("unused")
	private EmailAddress() {
		email = null;
		name = null;
	}

	public EmailAddress(final String email, final String name) {
		this.email = Preconditions.notBlank(email, "email");
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name != null ? name + " <" + email + ">" : email;
	}
}
