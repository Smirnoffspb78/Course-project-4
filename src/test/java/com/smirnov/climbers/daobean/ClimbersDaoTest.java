package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Climber;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ClimbersDaoTest {

    ClimbersDao climbersDao = new ClimbersDao("climbers");

    @Test
    void findById() {
    }

    @Test
    void insert() {
    }

    @Test
    void climbersSortSecondNameNotClimbingInLastYear_Two_Person() {
        Climber[] climbers = new Climber[3];
        climbers[0] = climbersDao.findById(9L);
        climbers[1] = climbersDao.findById(4L);
        climbers[2] = climbersDao.findById(8L);
        List<Map<String, String>> climersList = Arrays.stream(climbers)
                .map(climber -> Map.of(climber.getLastName(), climber.getEmail()))
                .toList();
        assertEquals(climersList, climbersDao.climbersSortSecondNameNotClimbingInLastYear(10));
    }
}