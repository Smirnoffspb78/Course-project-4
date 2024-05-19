package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Climber;
import com.smirnov.climbers.beans.GroupClimbers;
import com.smirnov.climbers.beans.Reserve;
import com.smirnov.climbers.beans.ReserveId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.constraints.NotNull;

import java.util.logging.Logger;

import static com.smirnov.climbers.ValidateObjects.validate;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.util.logging.Logger.getLogger;

public class ReserveDao extends Dao<ReserveId, Reserve> {
    private final Logger logger = getLogger(ReserveDao.class.getName());

    public ReserveDao(String nameEntityManager) {
        super(nameEntityManager);
    }

    /**
     * Извлекает запись по Id.
     *
     * @param reserveId Составной первичный ключ
     * @return Запись о совершенном восхождении
     */
    @Override
    Reserve findById(ReserveId reserveId) {
        validate(reserveId);
        Reserve reserve = new Reserve();
        reserve.setReserveId(reserveId);
        return reserve;
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

    /**
     * Возвращает готовый объект Резерв по группе и альпинисту.
     *
     * @param groupClimbers Группа альпинистов
     * @param climber       Альпинист
     * @return резерв
     */
    public Reserve createReserveByGroupAndClimber(@NotNull GroupClimbers groupClimbers, @NotNull Climber climber) {
        validate(groupClimbers);
        validate(climber);
        ReserveId reserveId = new ReserveId();
        reserveId.setGroupClimbers(groupClimbers);
        reserveId.setClimber(climber);
        return findById(reserveId);
    }
}
