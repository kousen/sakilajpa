package com.kousenit.sakila.dao;

import com.kousenit.sakila.entities.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CountryRepositoryTest {
    @Autowired
    private CountryRepository repository;

    @Test
    void getCountries() {
        assertEquals(109, repository.count());
    }

    @Test
    void checkLastUpdatedColumn() {
        System.out.println(repository.findById(1));
    }

    @Test @Transactional
    void getCountryAndCities() {
        Optional<Country> germany = repository.findById(38);
        assertTrue(germany.isPresent());
        assertEquals("Germany", germany.get().getName());
    }

    @Test
    void findFirst10ByName() {
        List<Country> countries = repository.findFirst10ByNameContaining("an");
        countries.forEach(country -> {
            System.out.println(country);
            assertTrue(country.getName().toLowerCase(Locale.ROOT).contains("an"));
        });
        assertEquals(10, countries.size());
    }

    @Test
    void getCountryWithCities() {
        Country india = repository.findByNameWithCities("India");
        System.out.println(india);
        india.getCities().forEach(System.out::println);
    }

    @Test
    void eagerFetchWithEntityGraphs() {
        List<Country> countries = repository.findAllByNameContains("un");
        countries.forEach(country -> {
            System.out.println(country.getName() +
                    " has " + country.getCities().size() + " cities");
        });
    }
}