package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Supervisor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_ID_GROUP_BY_SUPERVISOR;
import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class SupervisorDao extends Dao<Long, Supervisor> {

    public SupervisorDao(String nameEntityManager) {
        super(nameEntityManager);
    }

    /**
     * Возвращает руководителя из БД по идентификатору.
     *
     * @param id идентификатор из БД.
     * @return Руководитель
     */
    @Override
    Supervisor findById(@NotNull Long id) {
        validate(id);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(Supervisor.class, id);
            }
        }
    }

    @Override
    public Long insert(@NotNull Supervisor supervisor) {
        validate(supervisor);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(supervisor);
                manager.getTransaction().commit();
                return supervisor.getId();
            }
        }
    }

    /**
     * Возвращает список идентификаторов групп по ФИО руководителя,
     * где количество совершивших восхождение больше заданного.
     *
     * @param name     Имя руководителя
     * @param lastName Фамилия руководителя
     * @param surname  Отчество руководителя
     * @param count    количество совершивших восхождение
     * @return Список групп.
     */
    public List<Integer> getGroupByFSS(String name, String lastName, String surname, int count) {
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                return manager.createQuery(GET_ID_GROUP_BY_SUPERVISOR, Integer.class)
                        .setParameter(1, name)
                        .setParameter(2, lastName)
                        .setParameter(3, surname)
                        .setParameter(4, count)
                        .getResultList();
            }
        }
    }
}
