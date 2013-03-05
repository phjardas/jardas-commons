package de.jardas.commons.phase;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import de.jardas.commons.persistence.Identifiable;

public interface Phase extends Identifiable<String> {
	ReadableInstant getStart();

	ReadableInstant getEnd();

	boolean isActive(final DateTime instant);
}
