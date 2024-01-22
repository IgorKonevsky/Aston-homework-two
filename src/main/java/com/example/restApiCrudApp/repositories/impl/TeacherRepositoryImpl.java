package com.example.restApiCrudApp.repositories.impl;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Teacher;
import com.example.restApiCrudApp.repositories.TeacherRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryImpl implements TeacherRepository {
    private static final String SQL_SELECT_ALL = "SELECT * FROM teacher";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM teacher WHERE id = ?";
    private static final String SQL_SELECT_BY_GROUP_ID = "SELECT \n" +
            "    teacher.id AS teacher_id, \n" +
            "    teacher.firstname AS teacher_firstname, \n" +
            "    teacher.lastname AS teacher_lastname, \n" +
            "    subject.id AS subject_id\n" +
            "FROM \n" +
            "    teacher\n" +
            "JOIN \n" +
            "    teacher_group ON teacher.id = teacher_group.teacher_id\n" +
            "JOIN \n" +
            "    subject ON teacher.subject_id = subject.id\n" +
            "WHERE \n" +
            "    teacher_group.group_id = ?\n";
    private static final String SQL_INSERT = "INSERT INTO teacher(firstname, lastname, subject_id) VALUES (?,?,?)";
    private static final String SQL_INSERT_LINK = "INSERT INTO teacher_group(teacher_id, group_id) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE teacher SET firstname = ?, lastname = ?, subject_id = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM teacher WHERE id = ?";
    private final DatabaseConnectionInitializer databaseConnectionInitializer;

    public TeacherRepositoryImpl() {
        this.databaseConnectionInitializer = new DatabaseConnectionInitializer();
    }

    public TeacherRepositoryImpl(DatabaseConnectionInitializer databaseConnectionInitializer) {
        this.databaseConnectionInitializer = databaseConnectionInitializer;
    }

    @Override
    public List<Teacher> findAll() {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);

            List<Teacher> teacherList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = Teacher.builder()
                        .id(resultSet.getLong(1))
                        .firstName(resultSet.getString(2))
                        .lastName(resultSet.getString(3))
                        .subjectId(resultSet.getLong(4))
                        .build();
                teacherList.add(teacher);
            }
            return teacherList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher findById(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);


            ResultSet resultSet = statement.executeQuery();

            Teacher teacher = null;
            if (resultSet.next()) {
                teacher = Teacher.builder()
                        .id(resultSet.getLong(1))
                        .firstName(resultSet.getString(2))
                        .lastName(resultSet.getString(3))
                        .subjectId(resultSet.getLong(4))
                        .build();
            }

            return teacher;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> findTeachersByGroupId(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_GROUP_ID);
            statement.setLong(1, id);

            List<Teacher> teacherList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = Teacher.builder()
                        .id(resultSet.getLong(1))
                        .firstName(resultSet.getString(2))
                        .lastName(resultSet.getString(3))
                        .subjectId(resultSet.getLong(4))
                        .build();
                teacherList.add(teacher);
            }
            return teacherList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void linkTeacherToGroup(Long teacherId, Long groupId) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LINK);

            statement.setLong(1, teacherId);
            statement.setLong(2, groupId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher create(Teacher teacher) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setLong(3, teacher.getSubjectId());

            statement.executeUpdate();

            try {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    teacher.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return findById(teacher.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher update(Teacher teacher) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, teacher.getFirstName());
            statement.setString(2, teacher.getLastName());
            statement.setLong(3, teacher.getSubjectId());
            statement.setLong(4, teacher.getId());

            statement.executeUpdate();

            return findById(teacher.getId());

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
