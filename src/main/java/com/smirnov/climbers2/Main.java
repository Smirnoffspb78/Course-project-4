package com.smirnov.climbers2;

import com.smirnov.climbers2.dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        //Создание стран


       /* EntityManagerFactory factory = createEntityManagerFactory("climbers");
        try (EntityManager manager = factory.createEntityManager()) {
            manager.getTransaction().begin();*/


        Country switzerland = new Country();
        switzerland.setNameCountry("Швейцария");
        String name1 = "ИВАНОВ-Мехри(бабой       ";
        String name2 = name1.replace(" ", "").toLowerCase(); //replace("\W");
        System.out.println(name2);


        String num = "   89-2143256a  ";
        System.out.println(num.replaceAll("\\W", ""));
        //Добавление альпиниста
        Climber climber = new Climber();
        climber.setFirstName("Сергей");
        climber.setLastName("Польский");
        climber.setEmail("polsky@mail.ru");
        climber.setNumberPhone("80981235907");
        ClimbersDao climbersDao = new ClimbersDao();
        //climbersDao.createClimber(climber);

        //Извлечение альпиниста по ID

        Climber climber1get = climbersDao.getClimberById(3);
        System.out.println(climber1get.getId());
        System.out.println(climber1get.getFirstName());
        System.out.println(climber1get.getLastName());
        System.out.println(climber1get.getEmail());
        System.out.println(climber1get.getNumberPhone());

        RecordsDao recordsDao = new RecordsDao();
        //recordsDao.addRecord("climbers", 1);

        //System.out.println(queries.getOpenGroups());

        //System.out.println(recordsDao.climbersSortSecondNameNotClimbingInLastYear(10));

       //Добавление климбера в группу


        ////////////////////////////РЕЗУЛЬТАТЫ ТЕСТОВЫХ ЗАПРОСОВ///////////////////////////////
        System.out.println("Список групп, которые открыты");
        GroupClimbersDao groupClimbersDao = new GroupClimbersDao();
        groupClimbersDao.getGroupClimbersIsOpen();


        System.out.println("Список названий гор, где количество покоривших ее больше заданного значения");
        MountainsDao mountainsDao=new MountainsDao();
        System.out.println(mountainsDao.mountainsWithClimber(4));

        System.out.println("Отсортированный список альпинистов, которые не осуществляли походы за последний год");
        System.out.println(climbersDao.climbersSortSecondNameNotClimbingInLastYear(20));


        System.out.println("Идентификаторы групп по ФИО руководителя, где количество покоривших гору больше определенного значени");
        SupervisorDao supervisorDao=new SupervisorDao();
        System.out.println(supervisorDao.getGroupById("Алексей", "Соболев", "Игоревич", 1));

        System.out.println("Походы, которые осуществлялись в заданный период времени");
        System.out.println( recordsDao.recordsClimbingPeriod(10, LocalDate.now().minusMonths(6), LocalDate.now()));

    }

}
