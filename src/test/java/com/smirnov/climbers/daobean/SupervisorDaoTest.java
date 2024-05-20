package com.smirnov.climbers.daobean;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupervisorDaoTest {
    SupervisorDao supervisorDao=new SupervisorDao("climbers");

    @Test
    void getGroupByFSS_Two_group() {
        List<Integer> listId=List.of(1, 2);
        assertEquals(listId, supervisorDao.getGroupByFSS("Алексей", "Соболев", "Игоревич", 1));
    }

    @Test
    void getGroupByFSS_One_group() {
        List<Integer> listId=List.of(2);
        assertEquals(listId, supervisorDao.getGroupByFSS("Алексей", "Соболев", "Игоревич", 2));
    }
}