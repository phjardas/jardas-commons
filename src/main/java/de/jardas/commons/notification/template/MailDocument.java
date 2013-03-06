package de.jardas.commons.notification.template;

import com.google.common.base.Objects;

import de.jardas.migrator.internal.Preconditions;

class MailDocument {
	private final String subject;
	private final String html;
	private final String plain;

	public MailDocument(final String subject, final String html, final String plain) {
		Preconditions.notEmpty(subject, "subject");
		Preconditions.notEmpty(html, "html");
		Preconditions.notEmpty(plain, "plain");

		this.subject = subject;
		this.html = html;
		this.plain = plain;
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
		return Objects.toStringHelper(this).add("subject", subject).toString();
	}
}
