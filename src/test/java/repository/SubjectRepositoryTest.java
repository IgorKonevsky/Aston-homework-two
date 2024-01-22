package repository;

import com.example.restApiCrudApp.configuration.DatabaseConnectionInitializer;
import com.example.restApiCrudApp.entities.Subject;
import com.example.restApiCrudApp.repositories.SubjectRepository;
import com.example.restApiCrudApp.repositories.impl.SubjectRepositoryImpl;
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
class SubjectRepositoryTest {
    @Mock
    private DatabaseConnectionInitializer databaseConnectionInitializer;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private SubjectRepository subjectRepository;


    @BeforeEach
    void setUp() throws SQLException {
        subjectRepository = new SubjectRepositoryImpl(databaseConnectionInitializer);


    }

    @Test
    void testGetSubjectNameByTeacherId() throws SQLException {
        Long teacherId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString(1)).thenReturn("Math");

        String subjectName = subjectRepository.getSubjectNameByTeacherId(teacherId);

        assertEquals("Math", subjectName);
        verify(preparedStatement).setLong(1, teacherId);
    }

    @Test
    void testFindAll() throws SQLException {
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(resultSet.getString(2)).thenReturn("Math");

        List<Subject> subjectList = subjectRepository.findAll();

        assertNotNull(subjectList);
        assertEquals(1, subjectList.size());
        Subject subject = subjectList.get(0);
        assertEquals(1L, subject.getId());
        assertEquals("Math", subject.getName());
    }

    @Test
    void testFindById() throws SQLException {
        Long subjectId = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(subjectId);
        when(resultSet.getString(2)).thenReturn("Math");

        Subject subject = subjectRepository.findById(subjectId);

        assertNotNull(subject);
        assertEquals(subjectId, subject.getId());
        assertEquals("Math", subject.getName());
        verify(preparedStatement).setLong(1, subjectId);
    }

    @Test
    void testCreate() throws SQLException {
        Subject subjectToCreate = Subject.builder()
                .name("History").build();
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Subject createdSubject = subjectRepository.create(subjectToCreate);

        assertNotNull(createdSubject);
        assertEquals(1L, createdSubject.getId());
        assertEquals("History", createdSubject.getName());
        verify(preparedStatement).setString(1, "History");
    }

    @Test
    void testUpdate() throws SQLException {
        Subject subjectToUpdate = new Subject(1L, "UpdatedMath");
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Subject updatedSubject = subjectRepository.update(subjectToUpdate);

        assertNotNull(updatedSubject);
        assertEquals(subjectToUpdate.getId(), updatedSubject.getId());
        assertEquals(subjectToUpdate.getName(), updatedSubject.getName());
        verify(preparedStatement).setString(1, "UpdatedMath");
        verify(preparedStatement).setLong(2, 1L);
    }

    @Test
    void testDelete() throws SQLException {
        Long subjectIdToDelete = 1L;
        when(databaseConnectionInitializer.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        subjectRepository.delete(subjectIdToDelete);

        verify(preparedStatement).setLong(1, subjectIdToDelete);
    }
}