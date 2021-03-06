package de.jardas.commons.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import de.jardas.commons.Preconditions;

public class UserContext {
	private final UserDao userDao;

	public UserContext(final UserDao userDao) {
		this.userDao = Preconditions.notNull(userDao, "userDao");
	}

	public PersistentUser getUser() {
		return userDao.findById(getUserId());
	}

	public static String getUserId() {
		return SecurityUtils.userId(getAuthentication());
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static boolean hasRole(final String role) {
		return hasRole(getAuthentication(), role);
	}

	public static boolean hasRole(final Authentication authentication, final String role) {
		if (authentication == null) {
			return false;
		}

		return authentication.getAuthorities().contains(SecurityUtils.toAuthority(role));
	}

	public <T> Predicate<T> rolePredicate(final String role) {
		if (hasRole(role)) {
			return Predicates.alwaysTrue();
		}

		return Predicates.alwaysFalse();
	}
}
