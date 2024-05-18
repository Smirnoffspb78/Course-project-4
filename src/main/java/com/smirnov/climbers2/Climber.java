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
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.lang.Long.parseLong;

/**
 * Альпинист.
 */
@Getter
@Setter
@Entity
@Table(name = "tb_climbers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
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
    @NotBlank(message = "numberPhone не должен быть null и иметь хотя бы один не пробельный символ")
    @Column(name = "number_phone", length = 11, unique = true, nullable = false)
    private String numberPhone;
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
    @Column(name = "last_name", nullable = false, length = 200)
    private String lastName;
    /**
     * email.
     */
    @NotBlank(message = "email не должно быть null и содержать хотя бы один не пробельный символ")
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    public void setNumberPhone(@NotBlank String numberPhone) {
        try {
            parseLong(numberPhone);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("Телефон должен состоять только из цифр");
        }
        if (numberPhone.replaceAll("\\W", "").length()!=11 || numberPhone.length() != 11) {
            throw new IllegalArgumentException("Длина номер телефона должна составлять 11 символов и не содержать других символов");
        }
        this.numberPhone = numberPhone;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }
}
