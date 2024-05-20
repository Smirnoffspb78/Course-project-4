package com.smirnov.climbers.beans;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;


/**
 * Запись о восхождении на гору
 */
@Getter
@Setter
@Entity
@Table(name = "tb_records_climbing")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RecordClimbing {
    /**
     * Идентификатор записи.
     */
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    /**
     * Идентификатор группы.
     */
    @NotNull(message = "groupClimbers не должен быть null")
    @ManyToOne
    @JoinColumn(name = "group_climbers_id", nullable = false)
    private GroupClimbers groupClimbers;
    /**
     * Дата начала восхождения.
     */
    @NotNull(message = "start не должен быть null")
    @Column(name = "start", nullable = false)
    @Past(message = "start должен быть в прошлом")
    private LocalDate start;
    /**
     * Дата окончания восхождения.
     */
    @NotNull(message = "finish не должен быть null")
    @Column(name = "finish", nullable = false)
    @Past(message = "finish должен быть в прошлом")
    private LocalDate finish;

    /**
     * Количество альпинистов, покоривших гору
     */
    @Positive
    @Column(name = "count_climbers", nullable = false)
    private int countClimbers;

}
