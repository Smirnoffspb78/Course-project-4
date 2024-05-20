package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.GroupClimbers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupClimbersDaoTest {

    GroupClimbersDao groupClimbersDao = new GroupClimbersDao("climbers");

    @Test
    void getGroupClimbersIsOpen_true() {
        groupClimbersDao.getGroupClimbersIsOpen();
        GroupClimbers groupClimbers = groupClimbersDao.findById(3);
        List<GroupClimbers> groupClimbersTrue = List.of(groupClimbers);
        assertEquals(groupClimbersTrue, groupClimbersDao.getGroupClimbersIsOpen());
    }

    @Test
    void addClimberInGroup_IAE_for_idClimbers() {
        assertThrows(IllegalArgumentException.class,
                ()->groupClimbersDao.addClimberInGroup("climbers", "climbers", -1, 1));
    }

    @Test
    void addClimberInGroup_IAE_for_idGroup() {
        assertThrows(IllegalArgumentException.class,
                ()->groupClimbersDao.addClimberInGroup("climbers", "climbers", 1, 0));
    }

    @Test
    void updateStatus() {
    }
}