package de.jardas.commons.phase;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

@Entity
public class PhaseEntity implements Serializable, Phase {
	private static final long serialVersionUID = 1L;

	@Id
	private final String id;

	@Version
	private long version;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private ReadableInstant start;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private ReadableInstant end;

	PhaseEntity() {
		id = null;
	}

	public PhaseEntity(final String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ReadableInstant getStart() {
		return start;
	}

	public void setStart(final ReadableInstant start) {
		this.start = start;
	}

	@Override
	public ReadableInstant getEnd() {
		return end;
	}

	public void setEnd(final ReadableInstant end) {
		this.end = end;
	}

	public boolean isActive() {
		return isActive(null);
	}

	@Override
	public boolean isActive(final DateTime instant) {
		final DateTime now = instant != null ? instant : new DateTime();

		return (start == null || !start.isAfter(now)) && (end == null || end.isAfter(now));
	}

	@Override
	public String toString() {
		return getClass() + "<id=" + id + ", start=" + start + ", end=" + end + ">";
	}
}
