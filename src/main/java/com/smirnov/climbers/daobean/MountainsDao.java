package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Mountain;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_MOUNTAIN_NAME_AND_COUNT_CLIMBER;
import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class MountainsDao extends Dao<Integer, Mountain> {

    public MountainsDao(String nameEntityManager) {
        super(nameEntityManager);
    }

    /**
     * Извлекает гору из базы данных.
     *
     * @param id идентификатор горы
     * @return Альпинист
     */
    @Override
    public Mountain findById(@NotNull Integer id) {
        validate(id);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(Mountain.class, id);
            }
        }
    }

    /**
     * Добавляет гору в базу данных.
     *
     * @param mountain гора
     * @return идентификатор из базы данных
     */
    @Override
    public Integer insert(@NotNull Mountain mountain) {
        validate(mountain);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(mountain);
                manager.getTransaction().commit();
                return mountain.getId();
            }
        }
    }

    /**
     * Возвращает список названий гор, где количество покоривших гору больше заданного значения.
     */
    public List<String> mountainsWithClimber(long countClimbers) {
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                return manager.createNativeQuery(GET_MOUNTAIN_NAME_AND_COUNT_CLIMBER.getQuerySQL(), String.class)
                        .setParameter(1, countClimbers)
                        .getResultList();
            }
        }
    }
}
