package service;

import com.example.restApiCrudApp.dto.SubjectDTO;
import com.example.restApiCrudApp.entities.Subject;
import com.example.restApiCrudApp.mappers.SubjectMapper;
import com.example.restApiCrudApp.repositories.SubjectRepository;
import com.example.restApiCrudApp.services.SubjectService;
import com.example.restApiCrudApp.services.impl.SubjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.SubjectTestUtil;

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
    private SubjectTestUtil util;


    @BeforeEach
    void setUp() {
        subjectService = new SubjectServiceImpl(subjectRepository, subjectMapper);
        util = new SubjectTestUtil();
    }

    @Test
    @DisplayName("Test find subject by id - subject found")
    void testFindById() {
        Long subjectId = 1L;


        when(subjectRepository.findById(subjectId)).thenReturn(util.getExpectedSubject());
        when(subjectMapper.toDTO(any())).thenReturn(util.getExpectedSubjectDTO());

        SubjectDTO subjectDTO = subjectService.findById(subjectId);

        assertEquals(1L, subjectDTO.getId());
        assertEquals("name", subjectDTO.getName());
    }

    @Test
    @DisplayName("Test create subject - subject created")
    void testCreate() {
        Subject subjectToCreate = Subject.builder()
                .name("name")
                .build();
        SubjectDTO subjectDTOToCreate = SubjectDTO.builder()
                .name("name")
                .build();


        when(subjectMapper.toEntity(subjectDTOToCreate)).thenReturn(subjectToCreate);
        when(subjectRepository.create(subjectToCreate)).thenReturn(util.getExpectedSubject());
        when(subjectMapper.toDTO(util.getExpectedSubject())).thenReturn(util.getExpectedSubjectDTO());

        SubjectDTO createdSubjectDTO = subjectService.create(subjectDTOToCreate);

        assertEquals(1L, createdSubjectDTO.getId());
        assertEquals("name", createdSubjectDTO.getName());
    }

    @Test
    @DisplayName("Test update subject - subject updated")
    void testUpdate() {


        Subject SubjectToUpdate = Subject.builder()
                .name("name")
                .build();
        SubjectDTO SubjectDTOToUpdate = SubjectDTO.builder()
                .name("name")
                .build();

        when(subjectMapper.toEntity(SubjectDTOToUpdate)).thenReturn(SubjectToUpdate);
        when(subjectRepository.update(SubjectToUpdate)).thenReturn(util.getExpectedSubject());
        when(subjectMapper.toDTO(util.getExpectedSubject())).thenReturn(util.getExpectedSubjectDTO());

        SubjectDTO updatedSubjectDTO = subjectService.update(SubjectDTOToUpdate);

        assertEquals(1L, updatedSubjectDTO.getId());
        assertEquals("name", updatedSubjectDTO.getName());
    }

}
