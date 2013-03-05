package de.jardas.commons.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import de.jardas.commons.Preconditions;
import de.jardas.commons.persistence.ID;

@Entity
@Table(name = "user_invitation")
public class UserInvitation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	private final String token;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private final Reason reason;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", updatable = false)
	private final PersistentUser user;

	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private final ReadableInstant invitedAt;

	@ManyToOne
	@JoinColumn(name = "invited_by_id", updatable = false)
	private final PersistentUser invitedBy;

	@Column
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private ReadableInstant acceptedAt;

	@SuppressWarnings("unused")
	private UserInvitation() {
		token = null;
		reason = null;
		user = null;
		invitedAt = null;
		invitedBy = null;
	}

	public UserInvitation(final Reason reason, final PersistentUser user, final PersistentUser invitedBy) {
		token = ID.createId();
		this.reason = Preconditions.notNull(reason, "reason");
		this.user = Preconditions.notNull(user, "user");
		invitedAt = new DateTime();
		this.invitedBy = invitedBy;
	}

	public Reason getReason() {
		return reason;
	}

	public ReadableInstant getAcceptedAt() {
		return acceptedAt;
	}

	public void setAcceptedAt(final ReadableInstant acceptedAt) {
		this.acceptedAt = acceptedAt;
	}

	public String getToken() {
		return token;
	}

	public PersistentUser getUser() {
		return user;
	}

	public ReadableInstant getInvitedAt() {
		return invitedAt;
	}

	public PersistentUser getInvitedBy() {
		return invitedBy;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "<token=" + token + ", user=" + user + ", reason=" + reason
				+ ", invitedAt=" + invitedAt + ", invitedBy=" + invitedBy + ", acceptedAt=" + acceptedAt + ">";
	}

	public static enum Reason {
		REGISTRATION, INVITATION, PASSWORD_RESET
	}
}
