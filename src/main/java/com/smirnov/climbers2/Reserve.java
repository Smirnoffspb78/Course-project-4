package com.smirnov.climbers2;

import jakarta.persistence.*;
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
//@IdClass(CompositeKey.class)
@Table(name = "tb_reserve")
@EqualsAndHashCode
public class Reserve {

    @EmbeddedId
    ReserveId reserveId;
}
