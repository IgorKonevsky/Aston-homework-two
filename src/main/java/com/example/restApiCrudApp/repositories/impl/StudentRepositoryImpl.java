package com.example.restApiCrudApp.repositories.impl;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Student;
import com.example.restApiCrudApp.repositories.StudentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {


    private static final String SQL_SELECT_ALL = "SELECT * FROM student";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO student(firstname, lastname, group_id) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE student SET firstname = ?, lastname = ?, group_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM student WHERE id = ?";

    private final DatabaseConnectionInitializer databaseConnectionInitializer;

    public StudentRepositoryImpl() {
        this.databaseConnectionInitializer = new DatabaseConnectionInitializer();
    }

    public StudentRepositoryImpl(DatabaseConnectionInitializer databaseConnectionInitializer) {
        this.databaseConnectionInitializer = databaseConnectionInitializer;
    }

    @Override
    public List<Student> findAll() {

        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);

            List<Student> studentList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Student student = Student.builder()
                        .id(resultSet.getLong(1))
                        .firstName(resultSet.getString(2))
                        .lastName(resultSet.getString(3))
                        .groupId(resultSet.getLong(4))
                        .build();
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Student findById(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);


            ResultSet resultSet = statement.executeQuery();

            Student student = null;
            if (resultSet.next()) {
                student = Student.builder()
                        .id(resultSet.getLong(1))
                        .firstName(resultSet.getString(2))
                        .lastName(resultSet.getString(3))
                        .groupId(resultSet.getLong(4))
                        .build();
            }

            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public Student create(Student student) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setLong(3, student.getGroupId());

            statement.executeUpdate();

            try {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    student.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return student;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Student update(Student student) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setLong(3, student.getGroupId());
            statement.setLong(4, student.getId());

            statement.executeUpdate();

            return student;

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
