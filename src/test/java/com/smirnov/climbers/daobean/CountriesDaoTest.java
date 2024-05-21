package com.smirnov.climbers.daobean;

import com.smirnov.climbers.beans.Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountriesDaoTest {
    CountriesDao countriesDao=new CountriesDao("climbers");

    @Test
    void findById() {
        Country country=new Country();
        country.setNameCountry("Германия");
        assertEquals(country, countriesDao.findById("Германия"));
    }

    @Test
    void insert() {
    }
}