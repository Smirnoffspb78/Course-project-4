package com.smirnov.climbers.daobean;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MountainsDaoTest {
    MountainsDao mountainsDao = new MountainsDao("climbers");

    @Test
    void mountainsWithClimber_Alps() {
        List<String> mountains=List.of("Альпы");
        assertEquals(mountains, mountainsDao.mountainsWithClimber(5));
    }

    @Test
    void mountainsWithClimber_Empty() {
        List<String> mountains=List.of();
        assertEquals(mountains, mountainsDao.mountainsWithClimber(6));
    }

    @Test
    void mountainsWithClimber_ThrowIAE() {
        assertThrows(IllegalArgumentException.class, ()->mountainsDao.mountainsWithClimber(-1));
    }
}