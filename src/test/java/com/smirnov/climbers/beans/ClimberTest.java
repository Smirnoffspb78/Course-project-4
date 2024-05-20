package com.smirnov.climbers.beans;

import com.smirnov.climbers.ValidateObjects;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static com.smirnov.climbers.ValidateObjects.validate;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClimberTest {

    Climber createClimber() {
        Climber climber = new Climber();
        climber.setId(1);
        climber.setNumberPhone("12345678901");
        climber.setFirstName("Иван");
        climber.setLastName("Иванов");
        climber.setEmail("dwmdpw@mail.ru");
        validate(climber);
        return climber;
    }

    @Test
    void setNumberPhoneValidateException() {
        Climber climber = createClimber();
        climber.setNumberPhone(" 12345678901");
        assertThrows(ValidationException.class, () -> validate(climber));
    }

    @Test
    void setFirstName() {

    }

    @Test
    void setLastName() {
    }

    @Test
    void setEmail() {
    }
}