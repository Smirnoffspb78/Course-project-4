package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.logging.Logger;

import static com.smirnov.climbers.ValidateObjects.validate;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.util.logging.Logger.getLogger;

public class ReserveDao extends Dao<ReserveId, Reserve> {
    private final Logger logger = getLogger(ReserveDao.class.getName());

    public ReserveDao(String nameEntityManager) {
        super(nameEntityManager);
    }

    public boolean checkReserve(ReserveId reserveId) {
        try (EntityManagerFactory factory = createEntityManagerFactory("climbers")) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                List<Reserve> reserves = manager.createNativeQuery("SELECT * FROM tb_reserve", Reserve.class).getResultList();
                Reserve reserve = new Reserve();
                reserve.setReserveId(reserveId);
                return reserves.contains(reserve);
            }
        }
    }

    /**
     * Возвращает готовый объект Резерв по группе и альпинисту.
     *
     * @param groupClimbers Группа альпинистов
     * @param climber       Альпинист
     * @return резерв
     */
    public Reserve createReserveByGroupAndClimber(GroupClimbers groupClimbers, Climber climber) {
        validate(groupClimbers);
        validate(climber);
        Reserve reserve = new Reserve();
        reserve.setReserveId(new ReserveId(groupClimbers, climber));
        return reserve;
    }

    /**
     * Извлекает запись по Id.
     *
     * @param reserveId Составной первичный ключ
     * @return Запись о совершенном восхождении
     */
    @Override
    public Reserve findById(ReserveId reserveId) {
        validate(reserveId);
        try (EntityManagerFactory factory = createEntityManagerFactory("climbers")) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(Reserve.class, reserveId);
            }
        }
    }

    /**
     * Добавляет запись резерва в резерв.
     *
     * @param reserve Резерв
     * @return составной первичный ключ
     */
    @Override
    public ReserveId insert(Reserve reserve) {
        validate(reserve);
        try (EntityManagerFactory factory = createEntityManagerFactory("climbers")) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(reserve);
                manager.getTransaction().commit();
                logger.info("вы добавлены в резерв");
                return reserve.getReserveId();
            }
        }
    }
}
