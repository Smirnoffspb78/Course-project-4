package com.smirnov.climbers2;


import com.smirnov.climbers2.dao.CountriesDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

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
           // manager.persist(switzerland);//добавляет информацию об объекте в таблицу
           // manager.persist(italy);//добавляет информацию об объекте в таблицу
            /*countriesDao.createCountry(italy);
            countriesDao.createCountry(switzerland);*/

            Country russia = new Country();
            russia.setNameCountry("Россия");
            Country georgia = new Country();
            georgia.setNameCountry("Грузия");
            //manager.persist(russia);//добавляет информацию об объекте в таблицу
            //manager.persist(georgia);//добавляет информацию об объекте в таблицу

//Создание горы
            Mountain alps = new Mountain();
            alps.setMountainName("Альпы");
            alps.setHeight(4500);
            alps.getCountrySet().add(switzerland);
            alps.getCountrySet().add(italy);
            validate(alps);

            Mountain tushet = new Mountain();
            tushet.setMountainName("Тушетский хребет");
            tushet.setHeight(3500);
            tushet.getCountrySet().add(russia);
            tushet.getCountrySet().add(georgia);
            validate(tushet);
            //manager.persist(tushet);//добавляет информацию об объекте в таблицу



//Создание Альпиниста
            Climber ivanov = new Climber();
            ivanov.setFirstName("Иван");
            ivanov.setSecondName("Иванов");
            ivanov.setEmail("ivanov@mail.ru");
            ivanov.setNumberPhone("87838756789");
            validate(ivanov);
            //manager.persist(ivanov);

            Climber petrov = new Climber();
            petrov.setFirstName("Петр");
            petrov.setSecondName("Петров");
            petrov.setEmail("ffefefу@mail.ru");
            petrov.setNumberPhone("87838756788");
            validate(petrov);
            //manager.persist(petrov);

            Climber vladimir = new Climber();
            vladimir.setFirstName("Владимир");
            vladimir.setSecondName("Владимиров");
            vladimir.setEmail("petrov@mail.ru");
            vladimir.setNumberPhone("87838756787");
            validate(vladimir);
            //manager.persist(vladimir);

            Climber nikolai = new Climber();
            nikolai.setFirstName("Николаев");
            nikolai.setSecondName("Николай");
            nikolai.setEmail("nikolai@mail.ru");
            nikolai.setNumberPhone("87838756786");
            validate(nikolai);
            //manager.persist(nikolai);

            Climber aleksandr = new Climber();
            aleksandr.setFirstName("Александров");
            aleksandr.setSecondName("Александр");
            aleksandr.setEmail("aleksandr@mail.ru");
            aleksandr.setNumberPhone("87838756785");
            validate(aleksandr);
            //manager.persist(aleksandr);

            //Создание руководителей
            Supervisor aleksey= new Supervisor();
            aleksey.setFirstName("Алексей");
            aleksey.setSecondName("Соболев");
            aleksey.setSurName("Игоревич");
            validate(aleksey);
            //manager.persist(aleksey);

            Supervisor vladimirT= new Supervisor();
            vladimirT.setFirstName("Владимир");
            vladimirT.setSecondName("Молодежин");
            vladimirT.setSurName("Владимирович");
            validate(vladimirT);
            //manager.persist(vladimirT);

            Supervisor arina= new Supervisor();
            arina.setFirstName("Арина");
            arina.setSecondName("Матвеева");
            arina.setSurName("Радионовна");
            validate(arina);
            //manager.persist(arina);

//Создание групп
            //Получение данных из БД
            Mountain alpsDB = manager.find(Mountain.class, 1);
            Supervisor alekseyDB=manager.find(Supervisor.class, 1);

            GroupClimbers groupClimbers=new GroupClimbers();
            groupClimbers.setMountain(alpsDB);
            groupClimbers.setMaxClimber(2);
            groupClimbers.setSupervisor(alekseyDB);
            groupClimbers.setStart(LocalDate.now().plusDays(1));
            groupClimbers.setFinish(LocalDate.now().plusDays(1));
            groupClimbers.setPrice(2500);
            validate(groupClimbers);
            //manager.persist(groupClimbers);


            /*GroupClimbers groupClimbersDB=manager.find(GroupClimbers.class, 1);
            Climber aleksandrDB=manager.find(Climber.class, 5);
            Climber ivanDB=manager.find(Climber.class, 1);
            groupClimbersDB.getClimbers().add(aleksandrDB);
            groupClimbersDB.getClimbers().add(ivanDB);
            groupClimbersDB.setRecruitOpened(false);
            manager.merge(groupClimbersDB);*/

            GroupClimbers groupClimbersDB=manager.find(GroupClimbers.class, 4);
            Climber aleksandrDB=manager.find(Climber.class, 3);
            Climber ivanDB=manager.find(Climber.class, 4);
            groupClimbersDB.getClimbers().add(aleksandrDB);
            groupClimbersDB.getClimbers().add(ivanDB);
            groupClimbersDB.setRecruitOpened(false);
            //manager.merge(groupClimbersDB);


            //manager.getTransaction().commit();
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
