package de.jardas.commons.validation.test;

import static java.lang.String.format;
import static org.junit.Assert.fail;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.Constraint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ValidationAssertions {
	private static final Logger LOG = LoggerFactory.getLogger(ValidationAssertions.class);
	private final List<ValidationAssertion> assertions = new LinkedList<ValidationAssertion>();

	public ValidationAssertions add(final String field, final String code) {
		assertions.add(new ValidationAssertion(field, code));
		return this;
	}

	public ValidationAssertions add(final String field, final Class<?> annotation) {
		if (AnnotationUtils.findAnnotation(annotation, Constraint.class) == null) {
			throw new IllegalArgumentException(format("Annotation is not a @Constraint: %s", annotation));
		}

		return add(field, annotation.getSimpleName());
	}

	public ValidationAssertions required(final String field) {
		return add(field, "required");
	}

	public void runAssertions(final Errors errors) {
		runAssertions(errors, null);
	}

	public void runAssertions(final Errors errors, final MessageSource messageSource) {
		final Set<ValidationAssertion> missingAssertions = new LinkedHashSet<ValidationAssertion>(assertions);
		final List<ObjectError> unexpectedErrors = new LinkedList<ObjectError>();
		final List<ObjectError> unresolvedMessages = new LinkedList<ObjectError>();

		for (final ObjectError error : errors.getAllErrors()) {
			boolean ok = false;

			for (final ValidationAssertion assertion : assertions) {
				if (assertion.isMatch(error)) {
					missingAssertions.remove(assertion);
					LOG.debug("Found expected {}", error);
					ok = true;
					break;
				}
			}

			if (!ok) {
				unexpectedErrors.add(error);
			}

			if (messageSource != null) {
				try {
					final String message = messageSource.getMessage(error, Locale.getDefault());
					LOG.debug("Resolved error '{}' on '{}' to '{}'", error.getCode(),
							error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName(),
							message);

					if (error instanceof FieldError && message.contains(((FieldError) error).getField())) {
						throw new NoSuchMessageException(error.getCode());
					}
				} catch (final NoSuchMessageException e) {
					unresolvedMessages.add(error);
				}
			}
		}

		if (missingAssertions.isEmpty() && unexpectedErrors.isEmpty() && unresolvedMessages.isEmpty()) {
			// everything's ok!
			return;
		}

		final StringBuilder str = new StringBuilder();
		str.append("The validation did not produce the expected results:");

		if (!missingAssertions.isEmpty()) {
			str.append('\n');
			str.append("Missing validation errors:");
			for (final ValidationAssertion assertion : missingAssertions) {
				str.append('\n');
				str.append(String.format(" - %s", assertion));
			}
		}

		if (!unexpectedErrors.isEmpty()) {
			str.append('\n');
			str.append("Unexpected validation errors:");
			for (final ObjectError error : unexpectedErrors) {
				str.append('\n');
				str.append(String.format(" - %s", error));
			}
		}

		if (!unresolvedMessages.isEmpty()) {
			str.append('\n');
			str.append("Unresolved error messages:");
			for (final ObjectError error : unresolvedMessages) {
				str.append('\n');
				str.append(String.format(" - %s", error));
			}
		}

		fail(str.toString());
	}
}