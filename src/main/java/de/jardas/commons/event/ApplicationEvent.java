package de.jardas.commons.event;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import com.google.common.base.Objects;

public abstract class ApplicationEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ReadableInstant timestamp;

	public ApplicationEvent(final ReadableInstant timestamp) {
		this.timestamp = timestamp != null ? timestamp : new DateTime();
	}

	public ReadableInstant getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("timestamp", getTimestamp()).toString();
	}
}
