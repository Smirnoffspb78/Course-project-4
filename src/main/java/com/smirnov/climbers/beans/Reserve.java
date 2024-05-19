package com.smirnov.climbers.beans;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Резерв.
 */
@Getter
@Setter
@Entity
@Table(name = "tb_reserve")
@EqualsAndHashCode
public class Reserve {

    @EmbeddedId
    ReserveId reserveId;
}
