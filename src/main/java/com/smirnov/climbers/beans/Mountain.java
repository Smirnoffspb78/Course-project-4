package com.smirnov.climbers.beans;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Гора.
 */
@Getter
@Setter
@Entity
@Table(name = "tb_mountains")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mountain {
    /**
     * Идентификатор горы.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Наименование горы.
     */
    @NotBlank(message = "mountainName не должно быть null и иметь хотя-бы один пробельный символ")
    @Column(name = "mountain_name", nullable = false, length = 150, updatable = false)
    private String mountainName;
    /**
     * Высота горы, [м].
     */
    @Min(value = 100, message = "Высота горы не должна быть меньше 100м")
    @Column(name = "height", nullable = false, updatable = false)
    private double height;
    /**
     * Список стран, где гора расположена.
     */
    @NotNull(message = "countrySet не должен быть null")
    @NotEmpty(message = "countrySet не должен быть пустым")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_mountain_country",
            joinColumns = @JoinColumn(name = "mountain_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Set<@NotNull Country> countrySet = new HashSet<>();
}
