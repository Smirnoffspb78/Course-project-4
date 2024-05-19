package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.RecordClimbing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_RECORD_CLIMBING_BY_INTERVAL;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.time.LocalDate.now;


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
    public RecordClimbing findById(Integer id) {
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
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                List<RecordClimbing> recordsClimbings = new ArrayList<>();
                TypedQuery<RecordClimbing> query
                        = manager.createQuery(GET_RECORD_CLIMBING_BY_INTERVAL.getQuerySQL(), RecordClimbing.class);
                query.setParameter(1, finish);
                query.setParameter(2, start);
                query.setParameter(3, finish);
                query.setParameter(4, start);
                return query.getResultList();
            }
        }
    }
}
