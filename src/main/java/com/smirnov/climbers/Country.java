package com.smirnov.climbers;

import lombok.NonNull;

public record Country(String nameCountry) {
    public Country(@NonNull String nameCountry) {
        if (nameCountry.isBlank()) {
            throw new IllegalArgumentException("nameCountry is empty");
        }
        this.nameCountry = nameCountry;
    }

    @Override
    public String nameCountry() {
        return nameCountry;
    }

}
