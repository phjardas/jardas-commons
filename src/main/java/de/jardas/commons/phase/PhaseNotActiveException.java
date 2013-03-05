package de.jardas.commons.phase;

import static java.lang.String.format;

public class PhaseNotActiveException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PhaseNotActiveException(final String id) {
		super(format("Phase '%s' is not active", id));
	}
}
