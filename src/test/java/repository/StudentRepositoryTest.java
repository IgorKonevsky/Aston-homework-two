package repository;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Student;
import com.example.restApiCrudApp.repositories.StudentRepository;
import com.example.restApiCrudApp.repositories.impl.StudentRepositoryImpl;
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
class StudentRepositoryTest {
    @Mock
    private DatabaseConnectionInitializer databaseConnectionInitializer;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new StudentRepositoryImpl(databaseConnectionInitializer);
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

        List<Student> studentList = studentRepository.findAll();

        assertNotNull(studentList);
        assertEquals(1, studentList.size());
        Student student = studentList.get(0);
        assertEquals(1L, student.getId());
        assertEquals("Firstname", student.getFirstName());
        assertEquals("Lastname", student.getLastName());
        assertEquals(101L, student.getGroupId());
    }

    @Test
    void testFindById() throws SQLException {
        Long studentId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(studentId);
        when(resultSet.getString(2)).thenReturn("Firstname");
        when(resultSet.getString(3)).thenReturn("Lastname");
        when(resultSet.getLong(4)).thenReturn(101L);

        Student student = studentRepository.findById(studentId);

        assertNotNull(student);
        assertEquals(studentId, student.getId());
        assertEquals("Firstname", student.getFirstName());
        assertEquals("Lastname", student.getLastName());
        assertEquals(101L, student.getGroupId());
        verify(preparedStatement).setLong(1, studentId);
    }

    @Test
    void testCreate() throws SQLException {

        Student studentToCreate = Student.builder()
                .firstName("Firstname")
                .lastName("Lastname")
                .groupId(102L).
                build();
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Student createdStudent = studentRepository.create(studentToCreate);

        assertNotNull(createdStudent);
        assertEquals(1L, createdStudent.getId());
        assertEquals("Firstname", createdStudent.getFirstName());
        assertEquals("Lastname", createdStudent.getLastName());
        assertEquals(102L, createdStudent.getGroupId());
        verify(preparedStatement).setString(1, "Firstname");
        verify(preparedStatement).setString(2, "Lastname");
        verify(preparedStatement).setLong(3, 102L);
    }

    @Test
    void testUpdate() throws SQLException {
        Student studentToUpdate = new Student(1L, "UpdatedFirstname", "UpdatedLastname", 103L);
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Student updatedStudent = studentRepository.update(studentToUpdate);

        assertNotNull(updatedStudent);
        assertEquals(studentToUpdate.getId(), updatedStudent.getId());
        assertEquals(studentToUpdate.getFirstName(), updatedStudent.getFirstName());
        assertEquals(studentToUpdate.getLastName(), updatedStudent.getLastName());
        assertEquals(studentToUpdate.getGroupId(), updatedStudent.getGroupId());
        verify(preparedStatement).setString(1, "UpdatedFirstname");
        verify(preparedStatement).setString(2, "UpdatedLastname");
        verify(preparedStatement).setLong(3, 103L);
        verify(preparedStatement).setLong(4, 1L);
    }

    @Test
    void testDelete() throws SQLException {
        Long studentIdToDelete = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        studentRepository.delete(studentIdToDelete);

        verify(preparedStatement).setLong(1, studentIdToDelete);
    }
}
