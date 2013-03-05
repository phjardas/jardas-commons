package de.jardas.commons.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

@Component
public class UserContext {
	@Autowired
	private UserDao userDao;

	public PersistentUser getUser() {
		return userDao.findById(getUserId());
	}

	public static String getUserId() {
		return SecurityUtils.userId(getAuthentication());
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public boolean hasRole(final Role role) {
		return hasRole(getAuthentication(), role);
	}

	public boolean hasRole(final Authentication authentication, final Role role) {
		if (authentication == null) {
			return false;
		}

		return authentication.getAuthorities().contains(new SimpleGrantedAuthority(role.getRoleName()));
	}

	public <T> Predicate<T> rolePredicate(final Role role) {
		if (hasRole(role)) {
			return Predicates.alwaysTrue();
		}

		return Predicates.alwaysFalse();
	}
}
