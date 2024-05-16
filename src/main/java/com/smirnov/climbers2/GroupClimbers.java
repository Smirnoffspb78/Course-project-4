package com.smirnov.climbers2;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Группа для восхождения на гору.
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name= "tb_groups_climbers")
public class GroupClimbers {
    /**
     * Идентификатор группы.
     */
    @Id
    @EqualsAndHashCode.Include
    private long id;
    /**
     * Гора.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "mountain_id", updatable = false)
    private Mountain mountain;
    /**
     * Дата следующего похода.
     */
    @NotNull
    @Column(name = "start_date")
    private LocalDate startClimbing;
    /**
     * Максимальное количество человек в группе.
     */
    @Positive
    @Column(name = "max_climbers")
    private int maxClimber;
    /**
     * Указывает открыт ли набор в группу.
     */
    @Column(name = "is_open", nullable = false)
    private boolean isRecruitOpened;
    /**
     * Стоимость.
     */
    @Positive
    @Column(name = "price", nullable = false)
    private double price;
    /**
     * Руководитель похода.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;
    /**
     * Массив для записи альпинистов.
     */
    @NotNull
    @ManyToMany
    @JoinTable(name = "tb_climber_group_climbers",
            joinColumns = @JoinColumn(name = "group_climbers_id"),
            inverseJoinColumns = @JoinColumn(name = "climber_id")
    )
    private Set<Climber> climbers = new HashSet<>();
    /**
     * Дата окончания восхождения.
     */
    @NotNull
    @Column(name = "finish_date", nullable = false)
    private LocalDate finish;
}
