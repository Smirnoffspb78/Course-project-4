package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.RecordClimbing;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecordsDaoTest {
    RecordsDao recordsDao = new RecordsDao("climbers");

    @Test
    void recordsClimbingPeriod_OneClimb() {
        List<RecordClimbing> recordClimbings = List.of(recordsDao.findById(1));
        assertEquals(recordClimbings, recordsDao.recordsClimbingPeriod(20, LocalDate.of(2024, 3, 16), LocalDate.of(2024, 3, 17)));
    }

    @Test
    void recordsClimbingPeriod_ZeroClimb() {
        List<RecordClimbing> recordClimbings = List.of();
        assertEquals(recordClimbings, recordsDao.recordsClimbingPeriod(20, LocalDate.of(2024, 3, 16), LocalDate.of(2024, 3, 16)));
    }

    @Test
    void recordsClimbingPeriod_TwoClimb() {
        List<RecordClimbing> recordClimbings = List.of(recordsDao.findById(1),
                recordsDao.findById(3));
        assertEquals(recordClimbings, recordsDao.recordsClimbingPeriod(20, LocalDate.of(2024, 2, 25), LocalDate.of(2024, 3, 17)));
    }

    @Test
    void recordsClimbingPeriod_ThrowIAE_by_time() {
        assertThrows(IllegalArgumentException.class, () -> recordsDao.recordsClimbingPeriod(20, LocalDate.of(2024, 3, 17), LocalDate.of(2024, 2, 25)));
    }

}