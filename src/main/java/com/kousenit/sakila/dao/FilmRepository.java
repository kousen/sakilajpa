package com.kousenit.sakila.dao;

import com.kousenit.sakila.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface FilmRepository extends JpaRepository<Film, Integer> {

    @Procedure(procedureName = "film_in_stock")
    Integer numberOfFilmsInStock(Integer p_film_id, Integer p_store_id);

    @Procedure(name = "Film.inStock")
    Integer filmsInStockAtStore(Integer p_film_id, Integer p_store_id);
}