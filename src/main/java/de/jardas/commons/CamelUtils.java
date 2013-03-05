package de.jardas.commons;

import java.util.ArrayList;
import java.util.List;

public final class CamelUtils {
	private CamelUtils() {
	}

	public static String[] tokenizeCamelCase(final String str) {
		final List<String> tokens = new ArrayList<String>();
		final StringBuilder buffer = new StringBuilder();
		final int len = str.length();
		boolean lastUpper = false;

		for (int i = 0; i < len; i++) {
			final char c = str.charAt(i);

			if (Character.isUpperCase(c)) {
				if (buffer.length() > 0 && !lastUpper) {
					tokens.add(buffer.toString());
					buffer.setLength(0);
				}

				lastUpper = true;
			} else {
				lastUpper = false;
			}

			buffer.append(Character.toLowerCase(c));
		}

		if (buffer.length() > 0) {
			tokens.add(buffer.toString());
		}

		return tokens.toArray(new String[tokens.size()]);
	}
}
