package de.jardas.commons.security;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.provisioning.UserDetailsManager;

import de.jardas.commons.init.Initializer;

@Profile("testdata")
public class UsersInitializer implements Initializer {
	private final List<UserSpec> users = new LinkedList<UserSpec>();
	@Autowired
	private UserDetailsManager userDetailsManager;

	@Override
	public void initialize() {
		for (final UserSpec user : users) {
			if (!userDetailsManager.userExists(user.getEmail())) {
				final SecurityUser u = new SecurityUser(null, user.getEmail(), user.getName(), user.getPassword(),
						null, true, true, true, true, SecurityUtils.toAuthorities(user.getRoles()));
				userDetailsManager.createUser(u);
			}

			userDetailsManager.loadUserByUsername(user.getEmail());
		}
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
