package com.smirnov.climbers.daobean;

import com.smirnov.climbers.NullPointerOrIllegalArgumentException;
import com.smirnov.climbers.beans.Climber;
import com.smirnov.climbers.beans.GroupClimbers;
import com.smirnov.climbers.beans.ReserveId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_CLIMBING_CLIMBER_FOR_PERIOD;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_GROUP_OPEN_RECORD;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.time.LocalDate.now;
import static java.util.Objects.isNull;
import static java.util.logging.Logger.getLogger;

/**
 * Класс содержит методы для взаимодействия группы альпинистов с базой данных
 */
public class GroupClimbersDao extends Dao<Integer, GroupClimbers> {
    private final Logger logger = getLogger(GroupClimbersDao.class.getName());

    public GroupClimbersDao(String nameEntityManager) {
        super(nameEntityManager);
    }

    /**
     * Извлекает группу альпинистов из базы данных.
     *
     * @param id идентификатор альпиниста
     * @return Альпинист
     */
    @Override
    public GroupClimbers findById(Integer id) {
        if (isNull(id) || id < 1) {
            throw new NullPointerOrIllegalArgumentException("id не должен быть null и должен быть положительным");
        }
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.find(GroupClimbers.class, id);
            }
        }
    }

    /**
     * Добавляет группу альпинистов в базу данных.
     *
     * @param groupClimbers группа альпинистов
     * @return идентификатор из базы данных
     */
    @Override
    public Integer insert(GroupClimbers groupClimbers) {
        validate(groupClimbers);
        if (groupClimbers.getFinish().isBefore(groupClimbers.getStart())) {
            throw new IllegalArgumentException("Начало похода должно быть раньше или совпадать с датой финиша");
        }
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                manager.persist(groupClimbers);
                manager.getTransaction().commit();
                return groupClimbers.getId();
            }
        }
    }

    /**
     * Возвращает список групп, открытых для записи.
     *
     * @return Список групп
     */
    public List<GroupClimbers> getGroupClimbersIsOpen() {
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                manager.getTransaction().begin();
                return manager.createQuery(GET_GROUP_OPEN_RECORD, GroupClimbers.class).getResultList();
            }
        }
    }

    /**
     * Метод добавляет альпиниста в группу.
     *
     * @param idClimber Альпинист
     * @return true/false если набор открыт/закрыт
     */
    public boolean addClimberInGroup(String nameEntityManagerClimber, String nameEntityManagerReserve, long idClimber, int idGroup) {
        if (idClimber < 1 || idGroup < 1) {
            throw new IllegalArgumentException("idClimber и idGroup должны быть положительными");
        }
        ClimbersDao climbersDao = new ClimbersDao(nameEntityManagerClimber);
        Climber climber = climbersDao.findById(idClimber);
        GroupClimbers groupClimbers = findById(idGroup);
        if (groupClimbers.getClimbers().contains(climber)) {
            logger.info("Вы уже находитесь в этой группе");
            return false;
        }
        if (!groupClimbers.isOpen()) {
            logger.info("Набор закрыт.");
            if (groupClimbers.getStart().isAfter(now())) {
                ReserveDao reserveDao = new ReserveDao(nameEntityManagerReserve);
                ReserveId reserveId = new ReserveId();
                reserveId.setClimber(climber);
                reserveId.setGroupClimbers(groupClimbers);
                if (!reserveDao.checkReserve(reserveId)) {
                    reserveDao.insert(reserveDao.createReserveByGroupAndClimber(groupClimbers, climber));
                    logger.info("Альпинист с" + climber.getId() + "добавлен в резерв");
                }
            }
            return false;
        }
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                List<String> query = manager.createNativeQuery(GET_CLIMBING_CLIMBER_FOR_PERIOD, String.class)
                        .setParameter(1, idClimber)
                        .setParameter(2, groupClimbers.getStart())
                        .setParameter(3, groupClimbers.getFinish())
                        .setParameter(4, groupClimbers.getStart())
                        .setParameter(5, groupClimbers.getFinish())
                        .getResultList();
                if (!query.isEmpty()) {
                    logger.info("У вас уже есть походы в эти даты");
                    return false;
                }
                groupClimbers.getClimbers().add(climber);
                if (groupClimbers.getClimbers().size() == groupClimbers.getMaxClimber()) {
                    groupClimbers.setOpen(false);
                }
                manager.getTransaction().begin();
                manager.merge(groupClimbers);
                manager.getTransaction().commit();
                return true;
            }
        }
    }

    /**
     * Обновляет статус группы открыта/закрыта
     */
    public void updateStatus() {
        List<GroupClimbers> currentList = getGroupClimbersIsOpen();
        if (!currentList.isEmpty()) {
            try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
                try (EntityManager manager = factory.createEntityManager()) {
                    manager.getTransaction().begin();
                    currentList.stream()
                            .filter(groupClimbers -> !groupClimbers.getStart().isAfter(now()))
                            .collect(Collectors.toList())
                            .forEach(manager::merge);
                    manager.getTransaction().commit();
                }
            }
        }
    }
}
