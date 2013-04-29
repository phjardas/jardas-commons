package de.jardas.commons.security;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.provisioning.UserDetailsManager;

import de.jardas.commons.Preconditions;
import de.jardas.commons.init.Initializer;

public class UsersInitializer implements Initializer {
	private static final Logger LOG = LoggerFactory.getLogger(UsersInitializer.class);
	private final List<UserSpec> users = new LinkedList<UserSpec>();
	private final UserDetailsManager userDetailsManager;
	private final PasswordGenerator passwordGenerator;

	public UsersInitializer(final UserDetailsManager userDetailsManager, final PasswordGenerator passwordGenerator) {
		this.userDetailsManager = Preconditions.notNull(userDetailsManager, "userDetailsManager");
		this.passwordGenerator = Preconditions.notNull(passwordGenerator, "passwordGenerator");
	}

	@Override
	public void initialize() {
		for (final UserSpec user : users) {
			if (!userDetailsManager.userExists(user.getEmail())) {
				final String password = getPassword(user);
				final SecurityUser u = new SecurityUser(null, user.getEmail(), user.getName(), password, true, true,
						true, true, SecurityUtils.toAuthorities(user.getRoles()));
				userDetailsManager.createUser(u);
			}

			userDetailsManager.loadUserByUsername(user.getEmail());
		}
	}

	private String getPassword(final UserSpec user) {
		if (user.getPassword() == null) {
			final String password = passwordGenerator.createRandomPassword();
			LOG.info("Generated password for user {}: {}", user.getEmail(), password);
			user.setPassword(password);
		}

		return user.getPassword();
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	public List<UserSpec> getUsers() {
		return users;
	}

	public UsersInitializer add(final UserSpec user) {
		users.add(user);
		return this;
	}
}
