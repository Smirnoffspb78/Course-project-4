package com.smirnov.climbers2;

import lombok.Data;

import java.io.Serializable;


@Data
public class CompositeKey implements Serializable {
    private GroupClimbers groupClimbers;
    private Climber climber;
}
