package de.jardas.commons.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.jardas.migrator.internal.Preconditions;

public final class Validation {
	public static final String REQUIRED = "required";

	public static void required(final Errors errors, final String field) {
		final Object value = errors.getFieldValue(field);

		if (value == null || StringUtils.isBlank(value.toString()) || Boolean.FALSE.equals(value)) {
			errors.rejectValue(field, REQUIRED, null, null);
		}
	}

	public static void subValidation(final Validator validator, final Object target, final String objectName,
			final Errors errors) {
		if (validator == null || target == null) {
			return;
		}

		Preconditions.notEmpty(objectName, "objectName");
		Preconditions.notNull(errors, "errors");

		final BeanPropertyBindingResult questionnaireErrors = new BeanPropertyBindingResult(target, objectName);
		ValidationUtils.invokeValidator(validator, target, questionnaireErrors);

		for (final ObjectError error : questionnaireErrors.getAllErrors()) {
			if (error instanceof FieldError) {
				final FieldError fe = (FieldError) error;
				errors.rejectValue(objectName + "." + fe.getField(), fe.getCode(), fe.getArguments(),
						fe.getDefaultMessage());
			} else {
				errors.reject(error.getCode(), error.getArguments(), error.getDefaultMessage());
			}
		}
	}
}
