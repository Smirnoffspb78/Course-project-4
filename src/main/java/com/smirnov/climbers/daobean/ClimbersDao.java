package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Climber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Tuple;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_SECOND_NAME_AND_EMAIL_CLIMBER;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.time.LocalDate.now;


public class ClimbersDao extends Dao<Long, Climber> {

    public ClimbersDao(String nameEntityManager) {
        super(nameEntityManager);
    }

    /**
     * Извлекает альпиниста из базы данных.
     *
     * @param id идентификатор альпиниста
     * @return Альпинист
     */
    @Override
    public Climber findById(@NotNull Long id) {
        validate(id);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(Climber.class, id);
            }
        }
    }

    /**
     * Добавляет альпиниста в базу данных.
     *
     * @param climber альпинист
     * @return Идентификатор из базы данных
     */
    public Long insert(@NotNull Climber climber) {
        validate(climber);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(climber);
                manager.getTransaction().commit();
                return climber.getId();
            }
        }
    }

    /**
     * Возвращает список фамилий и email-ов в отсортированном по фамилии виде,
     * которые не ходили в поход в последний год
     *
     * @param limit Количество альпинистов, выгружаемых из базы за один раз
     * @return Список Мап, где ключ - фамилия, значение - email
     */
    public List<Map<String, String>> climbersSortSecondNameNotClimbingInLastYear(int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("limit должен быть положительным");
        }
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                List<Tuple> nativeQuery = manager.createNativeQuery(GET_SECOND_NAME_AND_EMAIL_CLIMBER, Tuple.class)
                        .setParameter(1, now().minusYears(1))
                        .getResultList();
                List<Map<String, String>> secondNamesAndEmails = new ArrayList<>();
                nativeQuery.forEach(tuple -> secondNamesAndEmails.add(Map.of((String)tuple.get("last_name"), (String) tuple.get("email"))));
                return secondNamesAndEmails;
            }
        }
    }
}
