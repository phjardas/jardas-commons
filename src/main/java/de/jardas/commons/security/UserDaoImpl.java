package de.jardas.commons.security;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements UserDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public PersistentUser findById(final String id) {
		return id != null ? entityManager.find(PersistentUser.class, id) : null;
	}

	@Override
	@Transactional(readOnly = true)
	public PersistentUser findByUsername(final String username) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<PersistentUser> crit = cb.createQuery(PersistentUser.class);
		final Root<PersistentUser> user = crit.from(PersistentUser.class);
		crit.where(usernameEqual(cb, user, username));

		try {
			return entityManager.createQuery(crit).getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getId(final String username) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<String> crit = cb.createQuery(String.class);
		final Root<PersistentUser> user = crit.from(PersistentUser.class);
		crit.select(user.get(PersistentUser_.id));
		crit.where(usernameEqual(cb, user, username));

		return entityManager.createQuery(crit).getSingleResult();
	}

	private static Predicate usernameEqual(final CriteriaBuilder cb, final Path<? extends PersistentUser> user,
			final String username) {
		final Expression<String> uname = cb.lower(cb.literal(username));

		return cb.or(cb.equal(cb.lower(user.get(PersistentUser_.email)), uname),
				cb.equal(cb.lower(user.get(PersistentUser_.id)), uname));
	}

	@Override
	@Transactional
	public void save(final PersistentUser user) {
		entityManager.merge(user);
	}

	@Override
	@Transactional
	public void remove(final PersistentUser user) {
		entityManager.remove(user);
	}
}
