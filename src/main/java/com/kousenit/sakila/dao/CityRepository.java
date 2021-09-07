package com.kousenit.sakila.dao;

import com.kousenit.sakila.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Integer> {
    List<City> findByName(String name);
    List<City> findByCountryName(String name);
}
