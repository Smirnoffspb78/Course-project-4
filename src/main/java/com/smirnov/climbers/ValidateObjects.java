package com.smirnov.climbers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.logging.Logger;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
import static java.util.logging.Logger.getLogger;

/**
 * Валидатор для объектов клуба альпинистов.
 */
public class ValidateObjects {
    private static final Logger LOGGER = getLogger(ValidateObjects.class.getName());

    private ValidateObjects() {
    }

    public static <T> void validate(T t) {
        try (ValidatorFactory factory = buildDefaultValidatorFactory()) {
            Set<ConstraintViolation<T>> violations = factory.getValidator().validate(t);
            if (!violations.isEmpty()) {
                violations.forEach(violation -> LOGGER.warning(violation.getMessage()));
                throw new ValidationException("Объект имеет невалидные значения.");
            }
        }
    }
}
