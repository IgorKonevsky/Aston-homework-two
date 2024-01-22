package repository;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Teacher;
import com.example.restApiCrudApp.repositories.TeacherRepository;
import com.example.restApiCrudApp.repositories.impl.TeacherRepositoryImpl;
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
class TeacherRepositoryTest {
    @Mock
    private DatabaseConnectionInitializer databaseConnectionInitializer;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        teacherRepository = new TeacherRepositoryImpl(databaseConnectionInitializer);
    }

    @Test
    void testFindAll() throws SQLException {
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(resultSet.getString(2)).thenReturn("Firstname");
        when(resultSet.getString(3)).thenReturn("Lastname");
        when(resultSet.getLong(4)).thenReturn(101L);

        List<Teacher> teacherList = teacherRepository.findAll();

        assertNotNull(teacherList);
        assertEquals(1, teacherList.size());
        Teacher teacher = teacherList.get(0);
        assertEquals(1L, teacher.getId());
        assertEquals("Firstname", teacher.getFirstName());
        assertEquals("Lastname", teacher.getLastName());
        assertEquals(101L, teacher.getSubjectId());
    }

    @Test
    void testFindById() throws SQLException {
        Long teacherId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(teacherId);
        when(resultSet.getString(2)).thenReturn("Firstname");
        when(resultSet.getString(3)).thenReturn("Lastname");
        when(resultSet.getLong(4)).thenReturn(101L);

        Teacher teacher = teacherRepository.findById(teacherId);

        assertNotNull(teacher);
        assertEquals(teacherId, teacher.getId());
        assertEquals("Firstname", teacher.getFirstName());
        assertEquals("Lastname", teacher.getLastName());
        assertEquals(101L, teacher.getSubjectId());
        verify(preparedStatement).setLong(1, teacherId);
    }

    @Test
    void testFindTeachersByGroupId() throws SQLException {
        Long groupId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(resultSet.getString(2)).thenReturn("Firstname");
        when(resultSet.getString(3)).thenReturn("Lastname");
        when(resultSet.getLong(4)).thenReturn(101L);


        List<Teacher> teacherList = teacherRepository.findTeachersByGroupId(groupId);

        assertNotNull(teacherList);
        assertEquals(1, teacherList.size());
        Teacher teacher = teacherList.get(0);
        assertEquals(1L, teacher.getId());
        assertEquals("Firstname", teacher.getFirstName());
        assertEquals("Lastname", teacher.getLastName());
        assertEquals(101L, teacher.getSubjectId());
        verify(preparedStatement).setLong(1, groupId);
    }

    @Test
    void testLinkTeacherToGroup() throws SQLException {
        Long teacherId = 1L;
        Long groupId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);


        teacherRepository.linkTeacherToGroup(teacherId, groupId);

        verify(preparedStatement).setLong(1, teacherId);
        verify(preparedStatement).setLong(2, groupId);
    }

    @Test
    void testCreate() throws SQLException {
        Teacher teacherToCreate = Teacher.builder()
                .firstName("Firstname")
                .lastName("Lastname")
                .subjectId(102L)
                .build();

        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Teacher createdTeacher = teacherRepository.create(teacherToCreate);

        assertNotNull(createdTeacher);
        assertEquals(1L, createdTeacher.getId());
        assertEquals("Firstname", createdTeacher.getFirstName());
        assertEquals("Lastname", createdTeacher.getLastName());
        assertEquals(102L, createdTeacher.getSubjectId());
        verify(preparedStatement).setString(1, "Firstname");
        verify(preparedStatement).setString(2, "Lastname");
        verify(preparedStatement).setLong(3, 102L);
    }

    @Test
    void testUpdate() throws SQLException {
        Teacher teacherToUpdate = Teacher.builder()
                .id(1L)
                .firstName("UpdatedFirstname")
                .lastName("UpdatedLastname")
                .subjectId(103L)
                .build();

        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Teacher updatedTeacher = teacherRepository.update(teacherToUpdate);

        assertNotNull(updatedTeacher);
        assertEquals(teacherToUpdate.getId(), updatedTeacher.getId());
        assertEquals(teacherToUpdate.getFirstName(), updatedTeacher.getFirstName());
        assertEquals(teacherToUpdate.getLastName(), updatedTeacher.getLastName());
        assertEquals(teacherToUpdate.getSubjectId(), updatedTeacher.getSubjectId());
        verify(preparedStatement).setString(1, "UpdatedFirstname");
        verify(preparedStatement).setString(2, "UpdatedLastname");
        verify(preparedStatement).setLong(3, 103L);
        verify(preparedStatement).setLong(4, 1L);
    }

    @Test
    void testDelete() throws SQLException {
        Long teacherIdToDelete = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        teacherRepository.delete(teacherIdToDelete);

        verify(preparedStatement).setLong(1, teacherIdToDelete);
    }
}
