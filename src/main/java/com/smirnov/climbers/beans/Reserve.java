package com.smirnov.climbers.beans;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "tb_reserve")
@EqualsAndHashCode
public class Reserve {

    @NotNull
    @EmbeddedId
    ReserveId reserveId;
}
