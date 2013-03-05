package de.jardas.commons.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtils {
	private SecurityUtils() {
		// utility class
	}

	public static String getUsername() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			return null;
		}

		return auth.getPrincipal().toString();
	}

	public static PersistentUser getUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null) {
			return null;
		}

		return (PersistentUser) auth.getPrincipal();
	}

	public static Collection<? extends GrantedAuthority> toAuthorities(final Role... roles) {
		return toAuthorities(Arrays.asList(roles));
	}

	public static Collection<? extends GrantedAuthority> toAuthorities(final Collection<Role> roles) {
		final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(roles.size());

		for (final Role role : roles) {
			authorities.add(toAuthority(role));
		}

		return authorities;
	}

	public static GrantedAuthority toAuthority(final Role role) {
		return new SimpleGrantedAuthority(role.getRoleName());
	}

	public static Collection<Role> toRoles(final Collection<? extends GrantedAuthority> authorities) {
		final Set<Role> roles = new HashSet<Role>(authorities.size());

		for (final GrantedAuthority authority : authorities) {
			roles.add(Role.forAuthority(authority.getAuthority()));
		}

		return roles;
	}

	public static String userId(final UserDetails user) {
		return user.getUsername();
	}

	public static String userId(final Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			return userId((UserDetails) authentication.getPrincipal());
		}

		return null;
	}
}
