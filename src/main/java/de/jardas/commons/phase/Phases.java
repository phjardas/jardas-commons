package de.jardas.commons.phase;

import org.joda.time.DateTime;

public interface Phases {
	Phase getPhase(String id);

	boolean isActive(String id);

	boolean isActive(String id, DateTime instant);

	void assertActive(String id);
}
