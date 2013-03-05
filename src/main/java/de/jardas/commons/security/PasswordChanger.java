package de.jardas.commons.security;

public interface PasswordChanger {
	void renewPassword(String username, String password);

	void setPassword(PersistentUser user, String password);
}
