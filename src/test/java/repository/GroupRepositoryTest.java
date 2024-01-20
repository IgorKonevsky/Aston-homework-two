package repository;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Group;
import com.example.restApiCrudApp.repositories.GroupRepository;
import com.example.restApiCrudApp.repositories.impl.GroupRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupRepositoryTest {

    @Mock
    private DatabaseConnectionInitializer databaseConnectionInitializer;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;


    private GroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        groupRepository = new GroupRepositoryImpl(databaseConnectionInitializer);
    }

    @Test
    void testFindGroupsByTeacherId() throws SQLException {
        Long teacherId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(resultSet.getLong(2)).thenReturn(101L);

        List<Group> groupList = groupRepository.findGroupsByTeacherId(teacherId);

        assertNotNull(groupList);
        assertEquals(1, groupList.size());
        Group group = groupList.get(0);
        assertEquals(1L, group.getId());
        assertEquals(101L, group.getNumber());
        verify(preparedStatement).setLong(1, teacherId);
    }

    @Test
    void testFindAll() throws SQLException {
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(resultSet.getLong(2)).thenReturn(101L);

        List<Group> groupList = groupRepository.findAll();

        assertNotNull(groupList);
        assertEquals(1, groupList.size());
        Group group = groupList.get(0);
        assertEquals(1L, group.getId());
        assertEquals(101L, group.getNumber());
    }

    @Test
    void testFindById() throws SQLException {
        Long groupId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(groupId);
        when(resultSet.getLong(2)).thenReturn(101L);

        Group group = groupRepository.findById(groupId);

        assertNotNull(group);
        assertEquals(groupId, group.getId());
        assertEquals(101L, group.getNumber());
        verify(preparedStatement).setLong(1, groupId);
    }

    @Test
    void testCreate() throws SQLException {
        Group groupToCreate = Group.builder()
                .number(101L).build();
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Group createdGroup = groupRepository.create(groupToCreate);

        assertNotNull(createdGroup);
        assertEquals(1L, createdGroup.getId());
        assertEquals(101L, createdGroup.getNumber());
        verify(preparedStatement).setLong(1, 101L);
    }

    @Test
    void testUpdate() throws SQLException {
        Group groupToUpdate = Group.builder()
                .id(1L)
                .number(102L)
                .build();
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Group updatedGroup = groupRepository.update(groupToUpdate);

        assertNotNull(updatedGroup);
        assertEquals(groupToUpdate.getId(), updatedGroup.getId());
        assertEquals(groupToUpdate.getNumber(), updatedGroup.getNumber());
        verify(preparedStatement).setLong(1, 102L);
        verify(preparedStatement).setLong(2, 1L);
    }

    @Test
    void testDelete() throws SQLException {
        Long groupIdToDelete = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        groupRepository.delete(groupIdToDelete);

        verify(preparedStatement).setLong(1, groupIdToDelete);
    }
}
