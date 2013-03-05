package de.jardas.commons.security;

public interface UserDao {
	PersistentUser findById(String id);

	PersistentUser findByUsername(String username);

	String getId(String username);

	void save(PersistentUser user);

	void remove(PersistentUser user);
}
