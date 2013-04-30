package de.jardas.commons.security;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserSpec {
	private final long id;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
	@NotEmpty
	private String name;

	private String password;

	private boolean overridePassword;

	@NotEmpty
	private String[] roles;

	public UserSpec() {
		id = 0;
	}

	public UserSpec(final String email, final String name, final String password, final String... roles) {
		this(0, email, name, password, roles);
	}

	public UserSpec(final long id, final String email, final String name, final String password, final String... roles) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.roles = roles;
	}

	public long getId() {
		return id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(final String[] roles) {
		if (roles != null) {
			this.roles = new String[roles.length];
			System.arraycopy(roles, 0, this.roles, 0, roles.length);
		} else {
			this.roles = new String[0];
		}
	}

	public boolean isOverridePassword() {
		return overridePassword;
	}

	public void setOverridePassword(final boolean overridePassword) {
		this.overridePassword = overridePassword;
	}
}