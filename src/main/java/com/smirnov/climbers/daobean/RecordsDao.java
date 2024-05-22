package com.smirnov.climbers.daobean;

import com.smirnov.climbers.NullPointerOrIllegalArgumentException;
import com.smirnov.climbers.beans.RecordClimbing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_RECORD_CLIMBING_BY_INTERVAL;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.time.LocalDate.now;
import static java.util.Objects.isNull;


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
        if (isNull(id) || id < 1) {
            throw new NullPointerOrIllegalArgumentException("id не должен быть null и должен быть положительным");
        }
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
    public Integer insert(RecordClimbing recordClimbing) {
        validate(recordClimbing);
        if (!recordClimbing.getGroupClimbers().getFinish().equals(recordClimbing.getFinish())
                || !recordClimbing.getGroupClimbers().getStart().equals(recordClimbing.getStart())
                || recordClimbing.getGroupClimbers().getClimbers().size() != recordClimbing.getCountClimbers()) {
            throw new IllegalArgumentException("Данные в записи должны совпадать с данными в Группе. Запись вносится после завершения похода");
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
                int offset = 0;
                boolean checkResult = true;
                List<RecordClimbing> returnResult = new ArrayList<>();
                while (checkResult) {
                    List<RecordClimbing> resultList = manager.createQuery(GET_RECORD_CLIMBING_BY_INTERVAL, RecordClimbing.class)
                            .setParameter(1, finish)
                            .setParameter(2, start)
                            .setParameter(3, finish)
                            .setParameter(4, start)
                            .setFirstResult(offset)
                            .setMaxResults(limit)
                            .getResultList();
                    returnResult.addAll(resultList);
                    if (resultList.size() < limit) {
                        checkResult = false;
                    } else {
                        offset += limit;
                    }
                }
                return returnResult;
            }
        }
    }
}
