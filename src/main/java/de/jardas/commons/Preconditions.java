package de.jardas.commons;

import static java.lang.String.format;

import java.util.Collection;

/**
 * Helper for contract validation.
 * 
 * @author Philipp Jardas
 */
public final class Preconditions {
	private static final String NULL_PATTERN = "%s can not be null";
	private static final String EMPTY_PATTERN = "%s can not be empty";
	private static final String BLANK_PATTERN = "%s can not be blank";
	private static final String POSITIVE_PATTERN = "%s must be positive";
	private static final String NOT_NEGATIVE_PATTERN = "%s can not be negative";

	private Preconditions() {
		// utility class
	}

	/**
	 * Assert that the given value is not <code>null</code>.
	 * 
	 * @param value
	 *            the value.
	 * @param name
	 *            the name of the value.
	 */
	public static <T> T notNull(final T value, final String name) {
		if (value == null) {
			throw new ContractViolationException(NULL_PATTERN, name);
		}

		return value;
	}

	/**
	 * Assert that the given collection is neither <code>null</code> nor {@link Collection#isEmpty() empty}.
	 * 
	 * @param value
	 *            the collection.
	 * @param name
	 *            the name of the collection.
	 */
	public static <T> Collection<T> notEmpty(final Collection<T> values, final String name) {
		notNull(values, name);

		if (values.isEmpty()) {
			throw new ContractViolationException(EMPTY_PATTERN, name);
		}

		return values;
	}

	/**
	 * Assert that the given array is neither <code>null</code> nor empty.
	 */
	public static Object[] notEmpty(final Object[] values, final String name) {
		notNull(values, name);

		if (values.length == 0) {
			throw new ContractViolationException(EMPTY_PATTERN, name);
		}

		return values;
	}

	/**
	 * Assert that the given array is neither <code>null</code> nor empty.
	 */
	public static long[] notEmpty(final long[] values, final String name) {
		notNull(values, name);

		if (values.length == 0) {
			throw new ContractViolationException(EMPTY_PATTERN, name);
		}

		return values;
	}

	/**
	 * Assert that the given array is neither <code>null</code> nor empty.
	 */
	public static byte[] notEmpty(final byte[] values, final String name) {
		notNull(values, name);

		if (values.length == 0) {
			throw new ContractViolationException(EMPTY_PATTERN, name);
		}

		return values;
	}

	/**
	 * Assert that the given string is neither <code>null</code> nor blank (only white-space characters).
	 * 
	 * @param value
	 *            the value.
	 * @param name
	 *            the name of the value.
	 */
	public static String notBlank(final String value, final String name) {
		notNull(value, name);

		if (value.trim().length() == 0) {
			throw new ContractViolationException(BLANK_PATTERN, name);
		}

		return value;
	}

	/**
	 * Assert that the given value is non-null and positive <strong>excluding</strong> zero.
	 */
	public static <N extends Number> N positive(final N value, final String name) {
		if (Preconditions.notNull(value, name).intValue() <= 0) {
			throw new ContractViolationException(POSITIVE_PATTERN, name);
		}

		return value;
	}

	/**
	 * Assert that the given value is non-null and positive <strong>including</strong> zero.
	 */
	public static <N extends Number> N notNegative(final N value, final String name) {
		if (Preconditions.notNull(value, name).intValue() < 0) {
			throw new ContractViolationException(NOT_NEGATIVE_PATTERN, name);
		}

		return value;
	}

	public static final class ContractViolationException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		public ContractViolationException(final String pattern, final String name) {
			super(format(pattern, name));
		}
	}
}
