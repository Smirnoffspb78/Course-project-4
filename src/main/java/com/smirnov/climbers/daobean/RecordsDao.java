package com.smirnov.climbers.daobean;


import com.smirnov.climbers.C3P0pool;
import com.smirnov.climbers.beans.GroupClimbers;
import com.smirnov.climbers.beans.RecordClimbing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_RECORD_CLIMBING_BY_INTERVAL;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.sql.Date.valueOf;
import static java.time.LocalDate.now;
import static java.util.logging.Logger.getLogger;


public class RecordsDao extends Dao<Integer, RecordClimbing> {

    public RecordsDao(String nameEntityManager) {
        super(nameEntityManager);
    }

    /**
     * Извлекает запись по Id
     *
     * @param id Идентификатор записи
     * @return Запись о совершенном восхождении
     */
    @Override
    public RecordClimbing selectById(Integer id) {
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(RecordClimbing.class, id);
            }
        }
    }

    /**
     * Создает запись на базе произошедшего похода группы
     *
     * @param recordClimbing Запись о группе
     * @return true/false если запись добавлена успешно/не успешно
     */
    @Override
    public Integer insert(RecordClimbing recordClimbing) { //Реализация обекта на базе ID должна лежать внутри класса
        validate(recordClimbing);
        if (!recordClimbing.getGroupClimbers().getFinish().isAfter(now())) {
            throw new IllegalArgumentException("Поход еще не закончился");
        }
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(recordClimbing);
                manager.getTransaction().commit();
                return recordClimbing.getId();
            }
        }
    }

    /**
     * Возвращает список записей восхождений, которые осуществлялись в заданный период времени
     *
     * @param limit  количество записей из базы данных, выгружаемые за один раз
     * @param start  начало периода
     * @param finish конец периода
     * @return Список восхождений, которые осуществлялись в заданный период времени
     */
    public List<RecordClimbing> recordsClimbingPeriod(int limit, LocalDate start, LocalDate finish) {
        if (limit < 1 || start.isAfter(finish)) {
            throw new IllegalArgumentException("limit<1 or start is after finish");
        }
        try (Connection connection = C3P0pool.getConnection()) {
            boolean checkDate = true;
            List<RecordClimbing> recordsClimbings = new ArrayList<>();
            int checkLimit;
            long offset = 0;
            while (checkDate) {
                try (PreparedStatement statement = connection.prepareStatement(GET_RECORD_CLIMBING_BY_INTERVAL.getQuerySQL())) {
                    statement.setObject(1, start);
                    statement.setObject(2, finish);
                    statement.setInt(3, limit);
                    statement.setLong(4, offset);
                    ResultSet resultSet = statement.executeQuery();
                    checkLimit = 0;
                    while (resultSet.next()) {
                        LocalDate dateStart = valueOf(resultSet.getObject("start").toString()).toLocalDate();
                        LocalDate dateFinish = valueOf(resultSet.getObject("finish").toString()).toLocalDate();
                        RecordClimbing recordClimbing = new RecordClimbing();
                        recordClimbing.setId(resultSet.getInt("id"));
                        recordClimbing.setGroupClimbers(new GroupClimbersDao(getNameEntityManager()).selectById(resultSet.getInt("group_climbers_id")));
                        recordClimbing.setStart(dateStart);
                        recordClimbing.setFinish(dateFinish);
                        recordClimbing.setCountClimbers(resultSet.getInt("count_climbers"));
                        recordsClimbings.add(recordClimbing);
                        checkLimit++;
                    }
                    if (checkLimit == limit) {
                        offset += limit;
                    } else {
                        checkDate = false;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return recordsClimbings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
