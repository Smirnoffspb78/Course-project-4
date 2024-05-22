package com.smirnov.climbers.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Руководитель похода.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_supervisors")
public class Supervisor {
    /**
     * Идентификатор руководителя похода.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    /**
     * Имя.
     */
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{0,199}$|^[А-Я][а-я]{0,199}$|",
            message = "Для добавления в БД имя должно начинаться с заглавной латинской или русской буквы, " +
                    "остальные символы должны быть подстрочные буквы")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    /**
     * Фамилия.
     */
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{0,199}$|^[А-Я][а-я]{0,199}$|",
            message = "Для добавления в БД имя должно начинаться с заглавной латинской или русской буквы, " +
                    "остальные символы должны быть подстрочные буквы")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    /**
     * Отчество.
     */
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]{0,199}$|^[А-Я][а-я]{0,199}$|",
            message = "Для добавления в БД имя должно начинаться с заглавной латинской или русской буквы, " +
                    "остальные символы должны быть подстрочные буквы")
    @Column(name = "surname", nullable = false, length = 100)
    private String surName;
}
