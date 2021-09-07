package com.kousenit.sakila.dao;

import com.kousenit.sakila.entities.Country;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country,Integer> {
    Country findByName(String name);
    List<Country> findAllByNameContaining(String name);

    List<Country> findFirst10ByNameContaining(String name);

    @Query("select distinct country from Country country " +
            " join fetch country.cities cities" +
            " where country.name = :name")
    Country findByNameWithCities(String name);

//    @EntityGraph(value = "graph.Country.cities",
//            type = EntityGraph.EntityGraphType.FETCH)
    @EntityGraph(attributePaths = "cities",
            type = EntityGraph.EntityGraphType.FETCH)
    List<Country> findAllByNameContains(String like);
}
