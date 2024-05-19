package com.smirnov.climbers.beans;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Составной первичный ключ для Reserve.
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class ReserveId {
    /**
     * Группа альпинистов.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_climbers_id", nullable = false)
    private GroupClimbers groupClimbers;
    /**
     * Альпинист.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "climber_id", nullable = false)
    private Climber climber;
}
