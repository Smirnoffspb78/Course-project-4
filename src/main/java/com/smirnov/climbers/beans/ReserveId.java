package com.smirnov.climbers.beans;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Составной первичный ключ для Reserve.
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class ReserveId {
    /**
     * Группа альпинистов.
     */
    @NotNull(message = "groupClimbers не может быть null")
    @ManyToOne
    @JoinColumn(name = "group_climbers_id", nullable = false)
    private GroupClimbers groupClimbers;
    /**
     * Альпинист.
     */
    @NotNull(message = "climber не может быть null")
    @ManyToOne
    @JoinColumn(name = "climber_id", nullable = false)
    private Climber climber;
}
