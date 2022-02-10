package com.kousenit.sakila.dao;

import com.kousenit.sakila.entities.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CityRepositoryTest {
    @Autowired
    private CityRepository repository;

    @Test
    void getCities() {
        assertEquals(600, repository.count());
    }

    @Test
    void getCityAndCountry() {
        List<City> cities = repository.findByName("London");
        cities.forEach(city -> {
            System.out.println(city);
            System.out.println(city.getCountry());
        });
    }

    @Test
    void findByCountryName() {
        List<City> cities = repository.findByCountryName("Germany");
        cities.forEach(System.out::println);
    }

    @Test
    void checkPagination() {
        PageRequest page = PageRequest.of(1, 5);
        Page<City> cities = repository.findAll(page);
        assertEquals(1, cities.getNumber());
        assertEquals(5, cities.getSize());
        assertEquals(5, cities.getNumberOfElements());
        assertEquals(120, cities.getTotalPages());
        assertEquals(600, cities.getTotalElements());
    }
}