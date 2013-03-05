package de.jardas.commons.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
	private static final long serialVersionUID = 1L;
	private final String id;
	private final String name;
	private final String email;
	private final String salt;

	public SecurityUser(final String id, final String email, final String name, final String password,
			final String salt, final boolean enabled, final boolean accountNonExpired,
			final boolean credentialsNonExpired, final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities) {
		super(String.valueOf(id), password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
				authorities);
		this.id = id;
		this.name = name;
		this.email = email;
		this.salt = salt;
	}

	public String getId() {
		return id;
	}

	public String getSalt() {
		return salt;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
