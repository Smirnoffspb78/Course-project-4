package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Climber;
import com.smirnov.climbers.beans.GroupClimbers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.smirnov.climbers.ValidateObjects.validate;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_CLIMBING_CLIMBER_FOR_PERIOD;
import static com.smirnov.climbers.daobean.QueriesClimberClub.GET_GROUP_OPEN_RECORD;
import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static java.time.LocalDate.now;
import static java.util.logging.Logger.getLogger;
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
    public GroupClimbers findById(@NotNull Integer id) {
        validate(id);
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
    public Integer insert(@NotNull GroupClimbers groupClimbers) {
        validate(groupClimbers);
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
        if (!groupClimbers.isOpen()) {
            logger.info("Набор закрыт.");
            ReserveDao reserveDao = new ReserveDao(nameEntityManagerReserve);
            reserveDao.insert(reserveDao.createReserveByGroupAndClimber(groupClimbers, climber));
            logger.info("Альпинист с" + climber.getId() + "добавлен в резерв");
            return false;
        }
        if (groupClimbers.getClimbers().contains(climber)) {
            logger.info("Вы уже находитесь в этой группе");
            return false;
        }
        try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
            try (EntityManager manager = factory.createEntityManager()) {
                List<String> query = manager.createNativeQuery(GET_CLIMBING_CLIMBER_FOR_PERIOD, String.class)
                        .setParameter(1, idClimber)
                        .setParameter(2, groupClimbers.getStart())
                        .setParameter(3, groupClimbers.getFinish())
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

    public void updateStatus() {
        List<GroupClimbers> currentList = getGroupClimbersIsOpen();
        if (!currentList.isEmpty()) {
            List<GroupClimbers> updateList = new ArrayList<>();
            currentList.stream().filter(groupClimbers -> !groupClimbers.getStart().isBefore(now()))
                    .forEach(groupClimbers -> {
                        groupClimbers.setOpen(false);
                        updateList.add(groupClimbers);
                    });
            try (EntityManagerFactory factory = createEntityManagerFactory(getNameEntityManager())) {
                try (EntityManager manager = factory.createEntityManager()) {
                    manager.getTransaction().begin();
                    updateList.forEach(manager::merge);
                    manager.getTransaction().commit();
                }
            }
        }
    }
}
