package de.jardas.commons.edit;

import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import de.jardas.commons.Preconditions;
import de.jardas.commons.persistence.ID;
import de.jardas.commons.persistence.Identifiable;
import de.jardas.commons.persistence.Identifiables;
import de.jardas.commons.security.PersistentUser;

@Entity
public class Edit implements Identifiable<String> {
	@Id
	private final String id = ID.createId();

	@Column(nullable = false, updatable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private final DateTime timestamp;

	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id", updatable = false)
	private final PersistentUser user;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "edit_id", nullable = false)
	private final Collection<Modification> modifications = new LinkedList<Modification>();

	@SuppressWarnings("unused")
	private Edit() {
		timestamp = null;
		user = null;
	}

	public Edit(final DateTime timestamp, final PersistentUser user, final Collection<Modification> modifications) {
		this.timestamp = timestamp != null ? timestamp : new DateTime();
		this.user = Preconditions.notNull(user, "user");

		if (modifications != null) {
			this.modifications.addAll(modifications);
		}
	}

	@Override
	public String getId() {
		return id;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public PersistentUser getUser() {
		return user;
	}

	public Collection<Modification> getModifications() {
		return modifications;
	}

	@Override
	public String toString() {
		return Identifiables.toStringHelper(this).add("timestamp", timestamp).add("user", user).toString();
	}
}
