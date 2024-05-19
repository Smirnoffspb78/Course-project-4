package com.smirnov.climbers.beans;

import com.smirnov.climbers.daobean.*;
import jakarta.persistence.Temporal;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jakarta.persistence.Persistence.createEntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        //Создание страны, Добавление, извлечение
        Country germany = new Country();
        germany.setNameCountry("Германия");
        CountriesDao countriesDao=new CountriesDao("climbers");
        /*String keyG= countriesDao.insert(germany);
        System.out.println(countriesDao.selectById("Германия").getNameCountry());*/


        //Добавление альпиниста
        Climber climber = new Climber();
        climber.setFirstName("Василий");
        climber.setLastName("Васильев");
        climber.setEmail("vasilev@mail.ru");
        climber.setNumberPhone("80981235903");
        ClimbersDao climbersDao = new ClimbersDao("climbers");
        //climbersDao.insert(climber);

        //Извлечение альпиниста по ID

        //Climber climber1get = climbersDao.selectById(3L);
        /*System.out.println(climber1get.getId());
        System.out.println(climber1get.getFirstName());
        System.out.println(climber1get.getLastName());
        System.out.println(climber1get.getEmail());
        System.out.println(climber1get.getNumberPhone());*/

//Создание записи о совершенном походе
        RecordClimbing record= new RecordClimbing();
        RecordsDao recordsDao = new RecordsDao("climbers");
        /*GroupClimbers groupClimbers=new GroupClimbersDao("climbers").selectById(3);
        record.setGroupClimbers(groupClimbers);
        record.setFinish(groupClimbers.getFinish());
        record.setStart(groupClimbers.getStart());
        record.setCountClimbers(groupClimbers.getClimbers().size());
        recordsDao.insert(record);*/
        //Извлечение записи по ключу
        //System.out.println(recordsDao.selectById(4));


        ////////////////////////////РЕЗУЛЬТАТЫ ТЕСТОВЫХ ЗАПРОСОВ///////////////////////////////
        System.out.println("Список групп, которые открыты");
        GroupClimbersDao groupClimbersDao = new GroupClimbersDao("climbers");
        System.out.println(groupClimbersDao.getGroupClimbersIsOpen());



        System.out.println("Список названий гор, где количество покоривших ее больше заданного значения");
        MountainsDao mountainsDao=new MountainsDao("climbers");
        System.out.println(mountainsDao.mountainsWithClimber(4));

        System.out.println("Отсортированный список альпинистов, которые не осуществляли походы за последний год");
        System.out.println(climbersDao.climbersSortSecondNameNotClimbingInLastYear(20));


        System.out.println("Идентификаторы групп по ФИО руководителя, где количество покоривших гору больше определенного значени");
        SupervisorDao supervisorDao=new SupervisorDao("climbers");
        System.out.println(supervisorDao.getGroupByFSS("Алексей", "Соболев", "Игоревич", 1));

        System.out.println("Походы, которые осуществлялись в заданный период времени");
        System.out.println( recordsDao.recordsClimbingPeriod(10, LocalDate.now().minusMonths(1), LocalDate.now()));




    }

}
