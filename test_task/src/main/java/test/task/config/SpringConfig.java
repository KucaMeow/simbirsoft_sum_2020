package test.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
@ComponentScan(basePackages = "test.task")
public class SpringConfig {

    @Bean
    JdbcTemplate jdbcTemplate () {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:sqlite:wordscount.sqlite");
            return new JdbcTemplate(dataSource);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
