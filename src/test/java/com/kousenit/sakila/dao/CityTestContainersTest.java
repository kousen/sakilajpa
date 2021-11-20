package com.kousenit.sakila.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class CityTestContainersTest {
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest")
            .withUsername("test")
            .withPassword("test")
            .withEnv("MYSQL_ROOT_PASSWORD", "test")
            // .withInitScript("sakila-schema.sql") // java.sql.SQLException: Failed to open the referenced table 'city'
            .withDatabaseName("sakila");

    @Autowired
    private CityRepository repository;

    @Test
    // @Sql(scripts = {"classpath:sakila-schema.sql", "classpath:sakila-data.sql"})
    // same exception as above
    public void test() {
        long count = repository.count();
        System.out.println("There are " + count + " cities in the db");
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
}
