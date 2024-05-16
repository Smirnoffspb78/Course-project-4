package com.smirnov.climbers2;

import lombok.Data;
import java.io.Serializable;


@Data
public class CompositeKey implements Serializable {
    private transient Mountain mountain;
    private transient Climber climber;
}
