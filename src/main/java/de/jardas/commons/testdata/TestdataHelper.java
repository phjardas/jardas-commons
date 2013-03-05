package de.jardas.commons.testdata;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;

public class TestdataHelper {
	public static final char[] ALPHABET_MINUSCLES = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	public static final char[] ALPHABET_MAJUSCLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	public static final char[] ALPHABET_DIGITS = "0123456789".toCharArray();
	public static final char[] ALPHABET_DEFAULT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
			.toCharArray();
	protected final Random random = new Random();

	public String string(final int minLength, final int maxLength) {
		return string(ALPHABET_DEFAULT, minLength, maxLength);
	}

	public String string(final char[] alphabet, final int minLength, final int maxLength) {
		final int length = rand(minLength, maxLength);
		final int alphabetLength = alphabet.length;
		final char[] chars = new char[length];

		for (int i = 0; i < length; i++) {
			chars[i] = alphabet[random.nextInt(alphabetLength)];
		}

		return new String(chars);
	}

	public String text(final int minLength, final int maxLength) {
		return text(ALPHABET_DEFAULT, minLength, maxLength);
	}

	public String text(final char[] alphabet, final int minLength, final int maxLength) {
		final int length = rand(minLength, maxLength);
		final StringBuilder text = new StringBuilder();
		int remaining = length;

		while (remaining > 0) {
			final int wordMin = Math.min(2, remaining);
			final int wordMax = Math.min(10, remaining);
			final String word = string(alphabet, wordMin, wordMax);

			text.append(word);
			remaining -= word.length();

			if (remaining > 1) {
				text.append(" ");
				remaining--;
			}
		}

		return text.toString();
	}

	public boolean bool(final double probability) {
		return random.nextFloat() < probability;
	}

	public String email() {
		return string(ALPHABET_MINUSCLES, 10, 10) + "@mailinator.com";
	}

	public int rand(final int min, final int max) {
		if (min > max) {
			throw new IllegalArgumentException("min (" + min + ") must be <= max (" + max + ")");
		}

		if (min == max) {
			return min;
		}

		return min + random.nextInt(max - min);
	}

	public double randDouble(final double min, final double max) {
		if (min > max) {
			throw new IllegalArgumentException("min (" + min + ") must be <= max (" + max + ")");
		}

		if (min == max) {
			return min;
		}

		return min + random.nextDouble() * (max - min);
	}

	public BigDecimal decimal(final double min, final double max, final int scale) {
		return BigDecimal.valueOf(randDouble(min, max)).setScale(scale, RoundingMode.HALF_UP);
	}

	public BigDecimal decimal(final BigDecimal min, final BigDecimal max, final int scale) {
		return decimal(min.doubleValue(), max.doubleValue(), scale);
	}

	public <T> List<T> list(final List<T> values, final int maxCount) {
		final List<T> shuffled = new ArrayList<T>(values);
		Collections.shuffle(shuffled, random);

		return values.subList(0, Math.min(maxCount, shuffled.size()));
	}

	public <T> T rand(final Collection<T> values) {
		if (values.isEmpty()) {
			return null;
		}

		final List<T> items = values instanceof List ? (List<T>) values : new ArrayList<T>(values);
		return items.get(random.nextInt(items.size()));
	}

	public <T> T rand(@SuppressWarnings("unchecked") final T... values) {
		if (values.length == 0) {
			return null;
		}

		return values[random.nextInt(values.length)];
	}

	public LocalDate date(final LocalDate min, final LocalDate max) {
		return date(min.toDateTimeAtStartOfDay(), max.toDateTimeAtStartOfDay()).toLocalDate();
	}

	public DateTime date(final ReadableInstant min, final ReadableInstant max) {
		final long minMs = min.getMillis();
		final long maxMs = max.getMillis();
		final long diff = maxMs - minMs;
		final long ms = minMs + (long) (diff * random.nextDouble());

		return new DateTime(ms);
	}
}
