package de.jardas.commons.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import de.jardas.commons.persistence.ID;
import de.jardas.commons.persistence.Identifiable;

@Entity
@Table(name = "users")
public class PersistentUser implements Serializable, Identifiable<String> {
	private static final long serialVersionUID = 1L;

	@Id
	private final String id = ID.createId();

	@Length(min = 3, max = 100)
	@Column(length = 100, unique = true)
	private String email;

	@Version
	private long version;

	@Column(nullable = false)
	private String name;

	@NotNull
	@Column(nullable = false)
	private String password;

	@Column(nullable = false, updatable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private final ReadableInstant createdAt = new DateTime();

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false)
	private boolean locked;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private final Set<String> roles = new HashSet<String>();

	@SuppressWarnings("unused")
	private PersistentUser() {
	}

	public PersistentUser(final String email, final String name) {
		this.email = email;
		this.name = name;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public ReadableInstant getCreatedAt() {
		return createdAt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(final boolean locked) {
		this.locked = locked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(final Collection<String> roles) {
		this.roles.clear();
		this.roles.addAll(roles);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + id + "<email=" + email + ", name=" + name + ">";
	}
}
