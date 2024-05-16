package com.smirnov.climbers2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Резерв.
 */
@Getter
@Setter
@Entity
@IdClass(CompositeKey.class)
@Table(name = "tb_reserve")
@EqualsAndHashCode
public class Reserve {

    /**
     * Гора.
     */
    @NotNull
    @Id
    @ManyToOne
    @JoinColumn(name = "mountain_id", nullable = false)
    private Mountain mountain;
    /**
     * Альпинист.
     */
    @NotNull
    @Id
    @ManyToOne
    @JoinColumn(name = "climber_id", nullable = false)
    private Climber climber;
}
