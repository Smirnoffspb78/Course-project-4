package com.smirnov.climbers.daobean;

import com.smirnov.climbers.C3P0pool;
import com.smirnov.climbers.beans.Climber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.smirnov.climbers.C3P0pool.getConnection;
import static com.smirnov.climbers.ValidateObjects.validate;

import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_SECOND_NAME_AND_EMAIL_CLIMBER;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.time.LocalDate.now;
import static java.util.regex.Pattern.compile;


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
    public Climber selectById(@NotNull Long id) {
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
     * @return идентификатор из базы данных
     */
    public Long insert(@NotNull Climber climber) {
        validate(climber);
        Pattern patternName = compile("^[A-Z][a-z]{0,199}$|^[А-Я][а-я]{0,199}$|");
        Pattern patternPhoneNumber= Pattern.compile("^[0-9]{11}$");
        Matcher matcherName = patternName.matcher(climber.getFirstName());
        Matcher matcherSecondName= patternName.matcher(climber.getLastName());
        Matcher matcherPhoneNumber=patternPhoneNumber.matcher(climber.getNumberPhone());
        if (!matcherName.matches() || !matcherSecondName.matches() || !matcherPhoneNumber.matches()){
            throw new IllegalArgumentException("Для добавления в БД имя должно начинаться с заглавной латинской или русской буквы, " +
                    "остальные символы должны быть подстрочные буквы. Номер телефона должен состоять из 11 пробельных символов");
        }
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
        try (Connection connection = getConnection()) {
            boolean checkData = true;
            List<Map<String, String>> secondNamesAndEmails = new ArrayList<>();
            int checkLimit;
            long offset = 0;
            while (checkData) {
                try (PreparedStatement statement = connection.prepareStatement(GET_SECOND_NAME_AND_EMAIL_CLIMBER.getQuerySQL())) {
                    statement.setObject(1, now().minusYears(1));
                    statement.setInt(2, limit);
                    statement.setLong(3, offset);
                    ResultSet resultSet = statement.executeQuery();
                    checkLimit = 0;
                    while (resultSet.next()) {
                        String email = resultSet.getString("last_name");
                        String name = resultSet.getString("email");
                        Map<String, String> emailAndName = Map.of(name, email);
                        secondNamesAndEmails.add(emailAndName);
                        checkLimit++;
                    }
                    if (checkLimit == limit) {
                        offset += limit;
                    } else {
                        checkData = false;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return secondNamesAndEmails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
