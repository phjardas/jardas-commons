package de.jardas.commons.phase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JPAPhasesImpl implements Phases {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean isActive(final String id) {
		return isActive(id, null);
	}

	@Override
	public boolean isActive(final String id, final DateTime instant) {
		return getPhase(id).isActive(instant);
	}

	@Override
	public void assertActive(final String id) {
		if (!isActive(id)) {
			throw new PhaseNotActiveException(id);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Phase getPhase(final String id) {
		final Phase phase = entityManager.find(Phase.class, id);

		if (phase == null) {
			throw new IllegalArgumentException("Unknown phase: " + id);
		}

		return phase;
	}
}
