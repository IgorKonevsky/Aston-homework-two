package com.example.restApiCrudApp.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DatabaseConnectionInitializer {
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/crud_db";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.setDriverClassName(DB_DRIVER);
        config.setMaximumPoolSize(30);
        config.setConnectionTimeout(300000);
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
