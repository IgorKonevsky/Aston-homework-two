package com.example.restApiCrudApp.repositories.impl;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Subject;
import com.example.restApiCrudApp.repositories.SubjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepositoryImpl implements SubjectRepository {

    private static final String SQL_SELECT_ALL = "SELECT * FROM subject";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM subject WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO subject(name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE subject SET name = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM subject WHERE id = ?";

    private final DatabaseConnectionInitializer databaseConnectionInitializer;
    private static final String SQL_GET_NAME_BY_TEACHER_ID = "SELECT \n" +
            "    subject.name AS subject_name\n" +
            "FROM \n" +
            "    teacher\n" +
            "JOIN \n" +
            "    subject ON teacher.subject_id = subject.id\n" +
            "WHERE \n" +
            "    teacher.id = ?;\n";

    public SubjectRepositoryImpl() {
        this.databaseConnectionInitializer = new DatabaseConnectionInitializer();
    }

    public SubjectRepositoryImpl(DatabaseConnectionInitializer databaseConnectionInitializer) {
        this.databaseConnectionInitializer = databaseConnectionInitializer;
    }

    @Override
    public String getSubjectNameByTeacherId(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_GET_NAME_BY_TEACHER_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            String name = null;
            if (resultSet.next()) {
                name = resultSet.getString(1);
            }

            return name;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Subject> findAll() {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);

            List<Subject> subjectList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = Subject.builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .build();
                subjectList.add(subject);

            }
            return subjectList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Subject findById(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            Subject subject = null;
            if (resultSet.next()) {
                subject = Subject.builder()
                        .id(resultSet.getLong(1))
                        .name(resultSet.getString(2))
                        .build();
            }

            return subject;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Subject create(Subject subject) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, subject.getName());

            statement.executeUpdate();

            try {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    subject.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return subject;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Subject update(Subject subject) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, subject.getName());
            statement.setLong(2, subject.getId());

            statement.executeUpdate();

            return subject;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);

            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
