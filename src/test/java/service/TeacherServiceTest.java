package service;

import com.example.restApiCrudApp.dto.TeacherDTO;
import com.example.restApiCrudApp.entities.Teacher;
import com.example.restApiCrudApp.mappers.GroupMapper;
import com.example.restApiCrudApp.mappers.TeacherMapper;
import com.example.restApiCrudApp.repositories.GroupRepository;
import com.example.restApiCrudApp.repositories.SubjectRepository;
import com.example.restApiCrudApp.repositories.TeacherRepository;
import com.example.restApiCrudApp.services.TeacherService;
import com.example.restApiCrudApp.services.impl.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.TeacherTestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private TeacherMapper teacherMapper;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private GroupMapper groupMapper;
    private TeacherService teacherService;
    private TeacherTestUtil util;


    @BeforeEach
    void setUp() {
        teacherService = new TeacherServiceImpl(teacherRepository, groupRepository, subjectRepository, teacherMapper, groupMapper);
        util = new TeacherTestUtil();
    }


    @Test
    @DisplayName("Test find teacher by id - teacher found")
    void testFindById() {
        Long teacherId = 1L;


        when(teacherRepository.findById(teacherId)).thenReturn(util.getExpectedTeacher());
        when(teacherMapper.toDTO(any())).thenReturn(util.getExpectedTeacherDTO());

        TeacherDTO teacherDTO = teacherService.findById(teacherId);

        assertEquals(1L, teacherDTO.getId());
        assertEquals("Firstname1", teacherDTO.getFirstName());
    }

    @Test
    @DisplayName("Test create teacher - teacher created")
    void testCreate() {
        Teacher teacherToCreate = Teacher.builder()
                .firstName("Firstname1")
                .lastName("Lastname1")
                .subjectId(101L)
                .build();
        TeacherDTO teacherDTOToCreate = TeacherDTO.builder()
                .firstName("Firstname1")
                .lastName("Lastname1")
                .subjectId(101L)
                .build();


        when(teacherMapper.toEntity(teacherDTOToCreate)).thenReturn(teacherToCreate);
        when(teacherRepository.create(teacherToCreate)).thenReturn(util.getExpectedTeacher());
        when(teacherMapper.toDTO(util.getExpectedTeacher())).thenReturn(util.getExpectedTeacherDTO());

        TeacherDTO createdTeacherDTO = teacherService.create(teacherDTOToCreate);

        assertEquals(1L, createdTeacherDTO.getId());
        assertEquals("Firstname1", createdTeacherDTO.getFirstName());
    }

    @Test
    @DisplayName("Test update teacher - teacher updated")
    void testUpdate() {


        Teacher teacherToUpdate = Teacher.builder()
                .firstName("Firstname2")
                .lastName("Lastname2")
                .subjectId(102L)
                .build();
        TeacherDTO teacherDTOToUpdate = TeacherDTO.builder()
                .firstName("Firstname2")
                .lastName("Lastname2")
                .subjectId(102L)
                .build();

        when(teacherMapper.toEntity(teacherDTOToUpdate)).thenReturn(teacherToUpdate);
        when(teacherRepository.update(teacherToUpdate)).thenReturn(util.getExpectedTeacher());
        when(teacherMapper.toDTO(util.getExpectedTeacher())).thenReturn(util.getExpectedTeacherDTO());

        TeacherDTO updatedTeacherDTO = teacherService.update(teacherDTOToUpdate);

        assertEquals(1L, updatedTeacherDTO.getId());
        assertEquals("Firstname1", updatedTeacherDTO.getFirstName());
    }
}
