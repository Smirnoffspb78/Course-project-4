package com.smirnov.climbers.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Группа для восхождения на гору.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_groups_climbers")
@ToString
public class GroupClimbers {
    /**
     * Идентификатор группы.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    /**
     * Гора.
     */
    @NotNull(message = "mountain не должно быть null")
    @ManyToOne
    @JoinColumn(name = "mountain_id", updatable = false)
    private Mountain mountain;
    /**
     * Дата следующего похода.
     */
    @NotNull (message = "start mountain не должно быть null")
    @Column(name = "start_date")
    @FutureOrPresent(message = "Дата старта восхождения не может быть в прошлом")
    private LocalDate start;
    /**
     * Дата окончания восхождения.
     */
    @NotNull(message = "finish не должно быть null")
    @Column(name = "finish_date", nullable = false)
    private LocalDate finish;
    /**
     * Максимальное количество человек в группе.
     */
    @Positive(message = "max_climbers должно быть положительным")
    @Column(name = "max_climbers")
    private int maxClimber;
    /**
     * Указывает открыт ли набор в группу.
     */
    @Column(name = "is_open", nullable = false)
    private boolean isOpen;
    /**
     * Стоимость.
     */
    @Positive (message = "price должно быть положительным")
    @Column(name = "price", nullable = false)
    private double price;
    /**
     * Руководитель похода.
     */
    @NotNull(message = "supervisor не должно быть null")
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;
    /**
     * Массив для записи альпинистов.
     */
    @NotNull (message = "climbers не должно быть null")
    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "tb_climber_group_climbers",
            joinColumns = @JoinColumn(name = "group_climbers_id"),
            inverseJoinColumns = @JoinColumn(name = "climber_id")
    )
    private Set<@NotNull Climber> climbers = new HashSet<>();

}
