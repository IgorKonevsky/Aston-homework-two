package service;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.entities.Group;
import com.example.restApiCrudApp.mappers.GroupMapperImpl;
import com.example.restApiCrudApp.mappers.TeacherMapper;
import com.example.restApiCrudApp.repositories.GroupRepository;
import com.example.restApiCrudApp.repositories.TeacherRepository;
import com.example.restApiCrudApp.services.GroupService;
import com.example.restApiCrudApp.services.impl.GroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {


    @Mock
    private GroupRepository groupRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private GroupMapperImpl groupMapper;
    @Mock
    private TeacherMapper teacherMapper;

    private GroupService groupService;

    private Group expectedGroup;

    private GroupDTO expectedGroupDTO;


    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(groupRepository, teacherRepository, groupMapper, teacherMapper);

        expectedGroup = Group.builder()
                .id(1L)
                .number(101L)
                .build();
        expectedGroupDTO = GroupDTO.builder()
                .id(1L)
                .number(101L)
                .build();

    }


    @Test
    void testFindById() {
        Long groupId = 1L;


        when(groupRepository.findById(groupId)).thenReturn(expectedGroup);
        when(groupMapper.toDTO(any())).thenReturn(expectedGroupDTO);

        GroupDTO groupDTO = groupService.findById(groupId);

        assertEquals(1L, groupDTO.getId());
        assertEquals(101L, groupDTO.getNumber());
    }

    @Test
    void testCreate() {
        Group groupToCreate = Group.builder()
                .number(101L)
                .build();
        GroupDTO groupDTOToCreate = GroupDTO.builder()
                .number(101L)
                .build();


        when(groupMapper.toEntity(groupDTOToCreate)).thenReturn(groupToCreate);
        when(groupRepository.create(groupToCreate)).thenReturn(expectedGroup);
        when(groupMapper.toDTO(expectedGroup)).thenReturn(expectedGroupDTO);

        GroupDTO createdGroupDTO = groupService.create(groupDTOToCreate);

        assertEquals(1L, createdGroupDTO.getId());
        assertEquals(101L, createdGroupDTO.getNumber());
    }

    @Test
    void testUpdate() {


        Group groupToUpdate = Group.builder()
                .number(102L)
                .build();
        GroupDTO groupDTOToUpdate = GroupDTO.builder()
                .number(102L)
                .build();

        when(groupMapper.toEntity(groupDTOToUpdate)).thenReturn(groupToUpdate);
        when(groupRepository.update(groupToUpdate)).thenReturn(expectedGroup);
        when(groupMapper.toDTO(expectedGroup)).thenReturn(expectedGroupDTO);

        GroupDTO updatedGroupDTO = groupService.update(groupDTOToUpdate);

        assertEquals(1L, updatedGroupDTO.getId());
        assertEquals(101L, updatedGroupDTO.getNumber());
    }


}


