package com.smirnov.climbers;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;


import static com.smirnov.climbers.ValidateObjects.validate;


public record Country(@NotBlank String nameCountry) {
    public Country(@NonNull String nameCountry) {
       /* if (nameCountry.isBlank()) {
            throw new IllegalArgumentException("nameCountry is empty");
        }*/
        this.nameCountry = nameCountry;
        validate(this);
    }

    @Override
    public String nameCountry() {
        return nameCountry;
    }

}
