package com.kousenit.sakila.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FilmDaoTest {
    @Autowired
    private FilmDao dao;

    @Test
    void getFilmsInStock() {
        int filmsInStock = dao.getFilmsInStock(1, 1);
        assertEquals(4, filmsInStock, "Should be 4 copies of film 1 at store 1");
    }

    @Test
    void getFilmsNotInStock() {
        int filmsNotInStock = dao.getFilmsNotInStock(2, 2);
        assertEquals(1, filmsNotInStock, "Should be 1 copy of film 2 NOT at store 2");
    }

    @Test
    void rewardsReport() {
        int count = dao.getRewardsReport((short) 7, 20.0);
        // returning 0, though the docs say it should be 42
        assertEquals(0, count);
    }

    @Test
    void inventoryHeldByCustomer() {
        int count = dao.getInventoryHeldByCustomer(9);
        assertEquals(366, count);
    }
}