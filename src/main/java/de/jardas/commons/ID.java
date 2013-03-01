package de.jardas.commons;

import java.util.UUID;

public final class ID {
	private ID() {
	}

	public static String createId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
