package com.smirnov.climbers.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    /**
     * Фамилия.
     */
    @NotBlank
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    /**
     * Отчество.
     */
    @NotBlank
    @Column(name = "surname", nullable = false, length = 100)
    private String surName;
}
