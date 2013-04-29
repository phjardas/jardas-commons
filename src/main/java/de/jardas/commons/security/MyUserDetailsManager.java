package de.jardas.commons.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.jardas.commons.Preconditions;

public class MyUserDetailsManager implements UserDetailsManager, PasswordChanger {
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	@Deprecated
	protected MyUserDetailsManager() {
		userDao = null;
		passwordEncoder = null;
	}

	public MyUserDetailsManager(final UserDao userDao, final PasswordEncoder passwordEncoder) {
		this.userDao = Preconditions.notNull(userDao, "userDao");
		this.passwordEncoder = Preconditions.notNull(passwordEncoder, "passwordEncoder");
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username) {
		final PersistentUser pu = userDao.findByUsername(username);

		if (pu == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		return new SecurityUser(pu.getId(), pu.getEmail(), pu.getName(), pu.getPassword(), pu.isEnabled(), true, true,
				!pu.isLocked(), SecurityUtils.toAuthorities(pu.getRoles()));
	}

	@Override
	@Transactional
	public void createUser(final UserDetails user) {
		if (userExists(user.getUsername())) {
			throw new IllegalArgumentException("User '" + user.getUsername() + "' already exists");
		}

		if (!(user instanceof SecurityUser)) {
			throw new IllegalArgumentException("User must be a " + SecurityUser.class.getName());
		}

		final SecurityUser su = (SecurityUser) user;

		final PersistentUser pu = new PersistentUser(su.getEmail(), su.getName());
		setPassword(pu, user.getPassword());
		pu.setRoles(SecurityUtils.toRoles(user.getAuthorities()));
		pu.setEnabled(user.isEnabled());
		pu.setLocked(!user.isAccountNonLocked());
		userDao.save(pu);
	}

	@Override
	@Transactional
	public void updateUser(final UserDetails user) {
		final PersistentUser pu = userDao.findById(user.getUsername());
		pu.setEnabled(user.isEnabled());
		pu.setLocked(!user.isAccountNonLocked());
		pu.setRoles(SecurityUtils.toRoles(user.getAuthorities()));
		userDao.save(pu);
	}

	@Override
	@Transactional
	public void deleteUser(final String username) {
		final PersistentUser user = userDao.findByUsername(username);

		if (user != null) {
			userDao.remove(user);
		}
	}

	@Override
	@Transactional
	public void changePassword(final String oldPassword, final String newPassword) {
		final Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

		if (currentUser == null) {
			// This would indicate bad coding somewhere
			throw new AccessDeniedException("Can't change password as no Authentication object found in context "
					+ "for current user.");
		}

		final String username = currentUser.getName();
		renewPassword(username, newPassword);

		// update security context
		final UserDetails user = loadUserByUsername(username);
		final UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(user,
				user.getPassword(), user.getAuthorities());
		newAuthentication.setDetails(currentUser.getDetails());
		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
	}

	@Override
	@Transactional
	public void renewPassword(final String username, final String password) {
		final PersistentUser pu = userDao.findByUsername(username);
		setPassword(pu, password);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean userExists(final String username) {
		return userDao.findByUsername(username) != null;
	}

	@Override
	public void setPassword(final PersistentUser user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
	}
}
