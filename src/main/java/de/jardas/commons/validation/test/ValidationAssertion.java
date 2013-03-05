package de.jardas.commons.validation.test;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ValidationAssertion {
	private final String field;
	private final String code;

	public ValidationAssertion(final String field, final String code) {
		this.field = field;
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public String getCode() {
		return code;
	}

	public boolean isMatch(final ObjectError error) {
		if (!(error instanceof FieldError)) {
			return false;
		}

		final FieldError fe = (FieldError) error;

		return field.equals(fe.getField()) && ArrayUtils.contains(fe.getCodes(), code);
	}

	@Override
	public String toString() {
		return field + " - " + code;
	}
}