package com.smirnov.climbers;

import com.smirnov.climbers.beans.Climber;
import com.smirnov.climbers.beans.Country;
import com.smirnov.climbers.beans.RecordClimbing;
import com.smirnov.climbers.beans.ReserveId;
import com.smirnov.climbers.daobean.ClimbersDao;
import com.smirnov.climbers.daobean.CountriesDao;
import com.smirnov.climbers.daobean.GroupClimbersDao;
import com.smirnov.climbers.daobean.RecordsDao;
import com.smirnov.climbers.daobean.ReserveDao;

public class Main {
    public static void main(String[] args) {
        //Обновление статуса в начале каждого запуска
        GroupClimbersDao groupClimbersDao=new GroupClimbersDao("climbers");
        groupClimbersDao.updateStatus();


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


        //Добавление альпиниста в резерв через закрытую группу
        groupClimbersDao.addClimberInGroup("climbers", "climbers", 5, 4);
//Создание записи о совершенном походе
        RecordClimbing record= new RecordClimbing();
        RecordsDao recordsDao = new RecordsDao("climbers");
       /* GroupClimbers groupClimbers=new GroupClimbersDao("climbers").findById(1);
        record.setGroupClimbers(groupClimbers);
        record.setFinish(groupClimbers.getFinish());
        record.setStart(groupClimbers.getStart());
        record.setCountClimbers(groupClimbers.getClimbers().size());
        recordsDao.insert(record);*/
        //Извлечение записи по ключу
        //System.out.println(recordsDao.selectById(4));
        //Извлечение резерва по ключу
        ReserveDao reserveDao=new ReserveDao("climbers");
        ReserveId reserveId=new ReserveId();
        reserveId.setClimber(climbersDao.findById(1L));
        reserveId.setGroupClimbers(groupClimbersDao.findById(4));
        System.out.println("Резерв по Id \n"+ reserveDao.findById(reserveId));
    }

}
