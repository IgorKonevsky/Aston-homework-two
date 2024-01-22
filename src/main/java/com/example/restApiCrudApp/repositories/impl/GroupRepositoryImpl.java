package com.example.restApiCrudApp.repositories.impl;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Group;
import com.example.restApiCrudApp.repositories.GroupRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository {
    private static final String SQL_SELECT_ALL = "SELECT * FROM group_table";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM group_table WHERE id = ?";
    private static final String SQL_SELECT_BY_TEACHER_ID = "SELECT\n" +
            "    group_table.id AS group_id,\n" +
            "    group_table.number AS group_number\n" +
            "FROM\n" +
            "    teacher\n" +
            "        JOIN\n" +
            "    teacher_group ON teacher.id = teacher_group.teacher_id\n" +
            "        JOIN\n" +
            "    group_table ON teacher_group.group_id = group_table.id\n" +
            "WHERE\n" +
            "        teacher.id = ?";
    private static final String SQL_INSERT = "INSERT INTO group_table(number) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE group_table SET number = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM group_table WHERE id = ?";
    private final DatabaseConnectionInitializer databaseConnectionInitializer;

    public GroupRepositoryImpl() {
        this.databaseConnectionInitializer = new DatabaseConnectionInitializer();
    }

    public GroupRepositoryImpl(DatabaseConnectionInitializer databaseConnectionInitializer) {
        this.databaseConnectionInitializer = databaseConnectionInitializer;
    }

    @Override
    public List<Group> findGroupsByTeacherId(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_TEACHER_ID);
            statement.setLong(1, id);

            List<Group> groupList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = Group.builder()
                        .id(resultSet.getLong(1))
                        .number(resultSet.getLong(2))
                        .build();
                groupList.add(group);
            }
            return groupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Group> findAll() {

        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);

            List<Group> groupList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = Group.builder()
                        .id(resultSet.getLong(1))
                        .number(resultSet.getLong(2))
                        .build();
                groupList.add(group);

            }
            return groupList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Group findById(Long id) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setLong(1, id);


            ResultSet resultSet = statement.executeQuery();

            Group group = null;
            if (resultSet.next()) {
                group = Group.builder()
                        .id(resultSet.getLong(1))
                        .number(resultSet.getLong(2))
                        .build();
            }

            return group;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Group create(Group group) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, group.getNumber());

            statement.executeUpdate();

            try {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    group.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return findById(group.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Group update(Group group) {
        try {
            Connection connection = databaseConnectionInitializer.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, group.getNumber());
            statement.setLong(2, group.getId());


            statement.executeUpdate();


            return findById(group.getId());

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

