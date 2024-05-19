package com.smirnov.climbers.daobean;

import com.smirnov.climbers.C3P0pool;
import com.smirnov.climbers.beans.Supervisor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.constraints.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
     * @param id идентификатор из БД.
     * @return Руководитель
     */
    @Override
    Supervisor selectById(@NotNull Long id) {
        validate(id);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(Supervisor.class, id);
            }
        }
    }

    @Override
    public Long insert(Supervisor supervisor) {
        validate(supervisor); //todo привести к нормальному виду для сохранения в БД
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(supervisor);
                manager.getTransaction().commit();
            }
        }
        return supervisor.getId();
    }

    /**
     * Возвращает список идентификаторов групп по ФИО руководителя,
     * где количество совершивших восхождение больше заданного.
     * @param name Имя руководителя
     * @param lastName Фамилия руководителя
     * @param surname Отчество руководителя
     * @param count количество совершивших восхождение
     * @return Список групп.
     */
    public List<Integer> getGroupByFSS(String name, String lastName, String surname, int count) {
        try (Connection connection = C3P0pool.getConnection()) {
            List<Integer> idGroups=new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(GET_ID_GROUP_BY_SUPERVISOR.getQuerySQL())) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setString(3, surname);
                statement.setInt(4, count);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    idGroups.add(resultSet.getInt("id"));
                }
                return idGroups;
            } catch (SQLException e) {
                throw new IllegalArgumentException("Некорректно переданы аргументы в запрос БД");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
