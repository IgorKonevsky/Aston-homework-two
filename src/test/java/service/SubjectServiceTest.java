package service;

import com.example.restApiCrudApp.dto.SubjectDTO;
import com.example.restApiCrudApp.entities.Subject;
import com.example.restApiCrudApp.mappers.SubjectMapper;
import com.example.restApiCrudApp.repositories.SubjectRepository;
import com.example.restApiCrudApp.services.SubjectService;
import com.example.restApiCrudApp.services.impl.SubjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectMapper subjectMapper;

    private SubjectService subjectService;

    private Subject expectedSubject;

    private SubjectDTO expectedSubjectDTO;

    @BeforeEach
    void setUp() {
        subjectService = new SubjectServiceImpl(subjectRepository, subjectMapper);

        expectedSubject = Subject.builder()
                .id(1L)
                .name("name")
                .build();
        expectedSubjectDTO = SubjectDTO.builder()
                .id(1L)
                .name("name")
                .build();
    }

    @Test
    void testFindById() {
        Long subjectId = 1L;


        when(subjectRepository.findById(subjectId)).thenReturn(expectedSubject);
        when(subjectMapper.toDTO(any())).thenReturn(expectedSubjectDTO);

        SubjectDTO subjectDTO = subjectService.findById(subjectId);

        assertEquals(1L, subjectDTO.getId());
        assertEquals("name", subjectDTO.getName());
    }

    @Test
    void testCreate() {
        Subject subjectToCreate = Subject.builder()
                .name("name")
                .build();
        SubjectDTO subjectDTOToCreate = SubjectDTO.builder()
                .name("name")
                .build();


        when(subjectMapper.toEntity(subjectDTOToCreate)).thenReturn(subjectToCreate);
        when(subjectRepository.create(subjectToCreate)).thenReturn(expectedSubject);
        when(subjectMapper.toDTO(expectedSubject)).thenReturn(expectedSubjectDTO);

        SubjectDTO createdSubjectDTO = subjectService.create(subjectDTOToCreate);

        assertEquals(1L, createdSubjectDTO.getId());
        assertEquals("name", createdSubjectDTO.getName());
    }

    @Test
    void testUpdate() {


        Subject SubjectToUpdate = Subject.builder()
                .name("name")
                .build();
        SubjectDTO SubjectDTOToUpdate = SubjectDTO.builder()
                .name("name")
                .build();

        when(subjectMapper.toEntity(SubjectDTOToUpdate)).thenReturn(SubjectToUpdate);
        when(subjectRepository.update(SubjectToUpdate)).thenReturn(expectedSubject);
        when(subjectMapper.toDTO(expectedSubject)).thenReturn(expectedSubjectDTO);

        SubjectDTO updatedSubjectDTO = subjectService.update(SubjectDTOToUpdate);

        assertEquals(1L, updatedSubjectDTO.getId());
        assertEquals("name", updatedSubjectDTO.getName());
    }

}
