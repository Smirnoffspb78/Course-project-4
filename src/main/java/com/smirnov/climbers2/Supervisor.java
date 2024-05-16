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
    @Column(name = "second_name", nullable = false, length = 100)
    private String secondName;
    /**
     * Отчество.
     */
    @NotBlank
    @Column(name = "surname", nullable = false, length = 100)
    private String surName;
}
