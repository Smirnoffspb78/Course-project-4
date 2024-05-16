package com.smirnov.climbers2;


import com.smirnov.climbers2.dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import static com.smirnov.climbers2.ValidateObjects.validate;
import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class Main {
    public static void main(String[] args) {
       /* SessionFactory sessionFactory2 = new Configuration()
                .configure()
                .addAnnotatedClass(com.smirnov.climbers2.Climber.class)
                .buildSessionFactory();*/

        /*ClimbersDao climbersDao = new ClimbersDao();*/

        //Создание стран


        EntityManagerFactory factory = createEntityManagerFactory("climbers");
        try (EntityManager manager = factory.createEntityManager()) {
            manager.getTransaction().begin();


            Country switzerland = new Country();
            switzerland.setNameCountry("Швейцария");
            Country italy = new Country();
            italy.setNameCountry("Италия");
            CountriesDao countriesDao = new CountriesDao();
            //manager.persist(switzerland);//добавляет информацию об объекте в таблицу
            //manager.persist(italy);//добавляет информацию об объекте в таблицу
            //countriesDao.createCountry(italy);
            //countriesDao.createCountry(switzerland);

//Создание горы
            Mountain alps = new Mountain();
            alps.setMountainName("Альпы");
            alps.setHeight(4500);
            alps.getCountrySet().add(switzerland);
            alps.getCountrySet().add(italy);
            validate(alps);
            //manager.persist(alps);//добавляет информацию об объекте в таблицу



//Создание Альпиниста
            Climber ivanov = new Climber();
            ivanov.setFirstName("Иван");
            ivanov.setSecondName("Иванов");
            ivanov.setEmail("ffefefe@mail.ru");
            ivanov.setNumberPhone(87838756789L);
            validate(ivanov);
            //manager.persist(ivanov);

            manager.getTransaction().commit();

        }
        /*climbersDao.climbersSortSecondNameNotClimbingInLastYear(10);
        System.out.println(climbersDao.climbersSortSecondNameNotClimbingInLastYear(10));

        RecordsDao recordsDao = new RecordsDao();
        System.out.println(recordsDao.recordsClimbingPeriod(100, LocalDate.now().minusMonths(3), LocalDate.now()));
*/

        /*GroupClimbersDao groupClimbersDao = new GroupClimbersDao();
        GroupClimbers groupClimbers = groupClimbersDao.getGroupsById(1);
        System.out.println(groupClimbers);

        System.out.println(new SupervisorDao().getSupervisorById(1));
        System.out.println(new CountriesDao().getCountryByName("Швейцария"));
        System.out.println(new MountainsDao().getMountainById(1));

        System.out.println(climbersDao.getClimbersByIdGroup(1));

        System.out.println("Альпинисты после рефлексии"+groupClimbers.getClimbers());


        System.out.println(groupClimbersDao.getGroupClimbersIsOpen());*/
        /*Set<Country> countrySet =new HashSet<>(); countrySet.add(null);*/
               /* Mountain mountain = new Mountain(1, "Эльбрус", 100, countrySet);

        System.out.println(mountain);
        System.out.println(mountain);*/


        /*Supervisor supervisor = Supervisor.builder()
                .firstName("Иван")
                .secondName("Иванов")
                .surName("Иванович")
                .id(100)
                .build();*/

    }

}
