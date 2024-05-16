package com.smirnov.climbers2;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Страна.
 */
@Getter
@Setter
@Entity
@Table(name = "tb_countries")
@EqualsAndHashCode
public class Country {
    /**
     * Наименование страны.
     */
    @Id
    @NotBlank(message = "nameCountry не должно быть null и иметь хотя бы один не пробельный символ")
    @Column(name = "country", nullable = false, length = 100, updatable = false)
    private String nameCountry;
}
