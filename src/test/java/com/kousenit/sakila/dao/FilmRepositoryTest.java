package com.kousenit.sakila.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FilmRepositoryTest {
    @Autowired
    private FilmRepository repository;

    @Test
    void totalCount() {
        assertEquals(1000, repository.count());
    }

    @Test @Disabled("Probable bug calling stored procedure")
    void invokeFilmInStockStoredProcedureUsingProcName() {
        Integer stock = repository.numberOfFilmsInStock(1, 1);
        System.out.println("There is/are " + stock + " copies of film 1 at store 1");
        assertThat(stock).isEqualTo(4);
    }

    @Test @Disabled("Probable bug calling stored procedure")
    void invokeFilmInStockStoredProcedureUsingName() {
        Integer stock = repository.filmsInStockAtStore(1, 1);
        System.out.println("There is/are " + stock + " copies of film 1 at store 1");
        assertThat(stock).isEqualTo(4);
    }
}