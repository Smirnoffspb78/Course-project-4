package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.constraints.NotNull;

import static com.smirnov.climbers.ValidateObjects.validate;
import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class CountriesDao extends Dao<String, Country> {

    public CountriesDao(String nameEntityManager) {
        super(nameEntityManager);
    }
    /**
     * Извлекает страну из базы данных.
     *
     * @param name идентификатор альпиниста
     * @return Альпинист
     */
    @Override
    public Country findById(@NotNull String name) {
        validate(name);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(Country.class, name);
            }
        }
    }
    /**
     * Добавляет страну в базу данных.
     *
     * @param country страна
     * @return идентификатор из базы данных
     */
    @Override
    public String insert(@NotNull Country country) {
        validate(country);
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(country);
                manager.getTransaction().commit();
            }
        }
        return country.getNameCountry();
    }
}
