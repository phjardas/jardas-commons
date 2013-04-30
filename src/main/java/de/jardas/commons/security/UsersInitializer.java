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
	private final PasswordChanger passwordChanger;

	public UsersInitializer(final UserDetailsManager userDetailsManager, final PasswordGenerator passwordGenerator) {
		this(userDetailsManager, passwordGenerator, null);
	}

	public UsersInitializer(final UserDetailsManager userDetailsManager, final PasswordGenerator passwordGenerator,
			final PasswordChanger passwordChanger) {
		this.userDetailsManager = Preconditions.notNull(userDetailsManager, "userDetailsManager");
		this.passwordGenerator = Preconditions.notNull(passwordGenerator, "passwordGenerator");
		this.passwordChanger = passwordChanger;
	}

	@Override
	public void initialize() {
		for (final UserSpec user : users) {
			if (!userDetailsManager.userExists(user.getEmail())) {
				createPassword(user);
				userDetailsManager.createUser(createUser(user));
			} else if (user.isOverridePassword() && passwordChanger != null) {
				createPassword(user);
				passwordChanger.renewPassword(user.getEmail(), user.getPassword());
			}

			userDetailsManager.loadUserByUsername(user.getEmail());
		}
	}

	private void createPassword(final UserSpec user) {
		if (user.getPassword() == null) {
			final String password = passwordGenerator.createRandomPassword();
			user.setPassword(password);
			LOG.info("Password for user {}: {}", user.getEmail(), password);
		}
	}

	private SecurityUser createUser(final UserSpec user) {
		return new SecurityUser(null, user.getEmail(), user.getName(), user.getPassword(), true, true, true, true,
				SecurityUtils.toAuthorities(user.getRoles()));
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
