package de.jardas.commons.notification;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import de.jardas.commons.Preconditions;
import de.jardas.commons.persistence.ID;
import de.jardas.commons.persistence.Identifiable;
import de.jardas.commons.persistence.Identifiables;

@Entity
public class Email implements Serializable, Identifiable<String> {
	private static final long serialVersionUID = 1L;

	@Id
	private final String id = ID.createId();

	@Column(nullable = false, updatable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private final DateTime createdAt = new DateTime();

	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private final EmailType type;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "email", column = @Column(name = "sender_email")),
			@AttributeOverride(name = "name", column = @Column(name = "sender_name")) })
	private final EmailAddress sender;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "email", column = @Column(name = "recipient_email")),
			@AttributeOverride(name = "name", column = @Column(name = "recipient_name")) })
	private final EmailAddress recipient;

	@Column(nullable = false, updatable = false)
	private final String subject;

	@Column(nullable = false, updatable = false)
	@Lob
	private final String html;

	@Column(nullable = false, updatable = false)
	@Lob
	private final String plain;

	@SuppressWarnings("unused")
	private Email() {
		type = null;
		sender = null;
		recipient = null;
		subject = null;
		html = null;
		plain = null;
	}

	public Email(final EmailType type, final EmailAddress sender, final EmailAddress recipient, final String subject,
			final String html, final String plain) {
		this.type = Preconditions.notNull(type, "type");
		this.sender = Preconditions.notNull(sender, "sender");
		this.recipient = Preconditions.notNull(recipient, "recipient");
		this.subject = Preconditions.notNull(subject, "subject");
		this.html = Preconditions.notNull(html, "html");
		this.plain = Preconditions.notNull(plain, "plain");
	}

	@Override
	public String getId() {
		return id;
	}

	public EmailType getType() {
		return type;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public EmailAddress getSender() {
		return sender;
	}

	public EmailAddress getRecipient() {
		return recipient;
	}

	public String getSubject() {
		return subject;
	}

	public String getHtml() {
		return html;
	}

	public String getPlain() {
		return plain;
	}

	@Override
	public String toString() {
		return Identifiables.toStringHelper(this).add("createdAt", createdAt).add("from", sender).add("to", recipient)
				.add("subject", subject).toString();
	}
}
