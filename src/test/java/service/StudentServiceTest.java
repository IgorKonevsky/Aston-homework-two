package service;

import com.example.restApiCrudApp.dto.StudentDTO;
import com.example.restApiCrudApp.entities.Student;
import com.example.restApiCrudApp.mappers.StudentMapper;
import com.example.restApiCrudApp.repositories.StudentRepository;
import com.example.restApiCrudApp.services.StudentService;
import com.example.restApiCrudApp.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    private StudentService studentService;

    private Student expectedStudent;

    private StudentDTO expectedStudentDTO;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(studentRepository, studentMapper);

        expectedStudent = Student.builder()
                .id(1L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .groupId(101L)
                .build();
        expectedStudentDTO = StudentDTO.builder()
                .id(1L)
                .firstName("Firstname1")
                .lastName("Lastname1")
                .groupId(101L)
                .build();

    }


    @Test
    void testFindById() {
        Long studentId = 1L;


        when(studentRepository.findById(studentId)).thenReturn(expectedStudent);
        when(studentMapper.toDTO(any())).thenReturn(expectedStudentDTO);

        StudentDTO studentDTO = studentService.findById(studentId);

        assertEquals(1L, studentDTO.getId());
        assertEquals("Firstname1", studentDTO.getFirstName());
    }

    @Test
    void testCreate() {
        Student studentToCreate = Student.builder()
                .firstName("Firstname1")
                .lastName("Lastname1")
                .groupId(101L)
                .build();
        StudentDTO studentDTOToCreate = StudentDTO.builder()
                .firstName("Firstname1")
                .lastName("Lastname1")
                .groupId(101L)
                .build();


        when(studentMapper.toEntity(studentDTOToCreate)).thenReturn(studentToCreate);
        when(studentRepository.create(studentToCreate)).thenReturn(expectedStudent);
        when(studentMapper.toDTO(expectedStudent)).thenReturn(expectedStudentDTO);

        StudentDTO createdStudentDTO = studentService.create(studentDTOToCreate);

        assertEquals(1L, createdStudentDTO.getId());
        assertEquals("Firstname1", createdStudentDTO.getFirstName());
    }

    @Test
    void testUpdate() {


        Student studentToUpdate = Student.builder()
                .firstName("Firstname2")
                .lastName("Lastname2")
                .groupId(102L)
                .build();
        StudentDTO studentDTOToUpdate = StudentDTO.builder()
                .firstName("Firstname2")
                .lastName("Lastname2")
                .groupId(102L)
                .build();

        when(studentMapper.toEntity(studentDTOToUpdate)).thenReturn(studentToUpdate);
        when(studentRepository.update(studentToUpdate)).thenReturn(expectedStudent);
        when(studentMapper.toDTO(expectedStudent)).thenReturn(expectedStudentDTO);

        StudentDTO updatedStudentDTO = studentService.update(studentDTOToUpdate);

        assertEquals(1L, updatedStudentDTO.getId());
        assertEquals("Firstname1", updatedStudentDTO.getFirstName());
    }


}
