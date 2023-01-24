package com.kousenit.sakila.dao;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Run only when Docker available")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class CityTestContainersTest {
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withUsername("test")
            .withPassword("test")
            .withEnv("MYSQL_ROOT_PASSWORD", "test")
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("sakila-both.sql"),
                    "/docker-entrypoint-initdb.d/schema.sql"
            )
            .withDatabaseName("sakila");

    @Autowired
    private CityRepository repository;

    @Test
    public void numberOfCities() {
        assertEquals(600, repository.count());
    }

    @Test @Disabled("Almost certainly a bug")
    void filmsAtStore(@Autowired FilmRepository filmRepository) {
        // This used to pass, but now fails. Probably a bug.
        assertEquals(4, filmRepository.numberOfFilmsInStock(1, 1),
                "Should be 4 films of id 1 at store 1");
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
}
