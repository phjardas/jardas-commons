package de.jardas.commons.notification.template;

import de.jardas.commons.Preconditions;

public class RenderedMessage {
	private final String subject;
	private final String html;
	private final String plain;

	public RenderedMessage(final String subject, final String html, final String plain) {
		this.subject = Preconditions.notNull(subject, "subject");
		this.html = Preconditions.notNull(html, "html");
		this.plain = Preconditions.notNull(plain, "plain");
	}

	public String getSubject() {
		return subject;
	}

	public String getHTML() {
		return html;
	}

	public String getPlain() {
		return plain;
	}
}
