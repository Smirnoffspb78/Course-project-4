package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Mountain;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.constraints.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.smirnov.climbers.C3P0pool.getConnection;
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
    public Mountain selectById(@NotNull Integer id) {
        validate(id);
        try (EntityManagerFactory factory = createEntityManagerFactory("climbers")) {
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
        try (EntityManagerFactory factory = createEntityManagerFactory("climbers")) {
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
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_MOUNTAIN_NAME_AND_COUNT_CLIMBER.getQuerySQL())) {
                statement.setLong(1, countClimbers);
                ResultSet resultSet = statement.executeQuery();
                List<String> mountains = new ArrayList<>();
                while (resultSet.next()) {
                    mountains.add(resultSet.getString("mountain_name"));
                }
                return mountains;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
