package com.smirnov.climbers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class ValidateObjects {
    private static final Logger LOGGER = getLogger(ValidateObjects.class.getName());

    private ValidateObjects() {
    }

    public static <T> void validate(T t) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate(t);
            if (!violations.isEmpty()) {
                for (ConstraintViolation<T> violation : violations) {
                    LOGGER.warning(violation.getMessage());
                }
                throw new IllegalArgumentException();
            }
        }
    }
}
