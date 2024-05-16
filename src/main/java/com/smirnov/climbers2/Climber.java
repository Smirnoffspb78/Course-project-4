package com.smirnov.climbers2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Альпинист.
 */
@Getter
@Setter
@Entity
@Table(name = "tb_climbers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Climber {
    /**
     * Идентификатор альпиниста.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    /**
     * Номер телефона.
     */
    @Range(min = 10_000_000_000L, max = 99_999_999_999L, message = "Длина должна быть 11 символов")
    @Column(name = "number_phone", unique = true, nullable = false)
    private long numberPhone;
    /**
     * Имя.
     */
    @NotBlank(message = "firstName не должно быть null и иметь хотя бы один не пробельный символ")
    @Column(name = "first_name", nullable = false, length = 200)
    private String firstName;
    /**
     * Фамилия.
     */
    @NotBlank(message = "secondName не должно быть null и иметь хотя бы один не пробельный символ")
    @Column(name = "second_name", nullable = false, length = 200)
    private String secondName;
    /**
     * email.
     */
    @NotBlank(message = "email не должно быть null и содержать хотя бы один не пробельный символ")
    @Column(name = "email", nullable = false, length = 200)
    private String email;
}
