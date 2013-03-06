package de.jardas.commons.edit;

import java.io.Serializable;

import org.joda.time.ReadableInstant;

import com.google.common.base.Objects;

import de.jardas.commons.Preconditions;
import de.jardas.commons.event.ApplicationEvent;
import de.jardas.commons.persistence.Identifiable;

public class EditEvent<I extends Serializable, E extends Identifiable<I>> extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private final String editId;
	private final Class<E> targetType;
	private final I targetId;
	private final String targetName;
	private final String userId;
	private final String userName;

	public EditEvent(final ReadableInstant timestamp, final String editId, final Class<E> targetType, final I targetId,
			final String targetName, final String userId, final String userName) {
		super(timestamp);
		this.editId = Preconditions.notNull(editId, "editId");
		this.targetType = Preconditions.notNull(targetType, "targetType");
		this.targetId = Preconditions.notNull(targetId, "targetId");
		this.targetName = Preconditions.notNull(targetName, "targetName");
		this.userId = Preconditions.notNull(userId, "userId");
		this.userName = Preconditions.notNull(userName, "userName");
	}

	public String getEditId() {
		return editId;
	}

	public Class<E> getTargetType() {
		return targetType;
	}

	public I getTargetId() {
		return targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("timestamp", getTimestamp()).add("targetType", targetType.getName())
				.add("targetId", targetId).add("targetName", targetName).toString();
	}
}
