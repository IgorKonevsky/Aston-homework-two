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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.GroupTestUtil;

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
    private GroupTestUtil util;


    @BeforeEach
    void setUp() {
        groupService = new GroupServiceImpl(groupRepository, teacherRepository, groupMapper, teacherMapper);

        util = new GroupTestUtil();

    }


    @Test
    @DisplayName("Test find group by id - group found")
    void testFindById() {
        Long groupId = 1L;


        when(groupRepository.findById(groupId)).thenReturn(util.getExpectedGroup());
        when(groupMapper.toDTO(any())).thenReturn(util.getExpectedGroupDTO());

        GroupDTO groupDTO = groupService.findById(groupId);

        assertEquals(1L, groupDTO.getId());
        assertEquals(101L, groupDTO.getNumber());
    }

    @Test
    @DisplayName("Test create group - group created")
    void testCreate() {
        Group groupToCreate = Group.builder()
                .number(101L)
                .build();
        GroupDTO groupDTOToCreate = GroupDTO.builder()
                .number(101L)
                .build();


        when(groupMapper.toEntity(groupDTOToCreate)).thenReturn(groupToCreate);
        when(groupRepository.create(groupToCreate)).thenReturn(util.getExpectedGroup());
        when(groupMapper.toDTO(util.getExpectedGroup())).thenReturn(util.getExpectedGroupDTO());

        GroupDTO createdGroupDTO = groupService.create(groupDTOToCreate);

        assertEquals(1L, createdGroupDTO.getId());
        assertEquals(101L, createdGroupDTO.getNumber());
    }

    @Test
    @DisplayName("Test update group - group updated")
    void testUpdate() {


        Group groupToUpdate = Group.builder()
                .number(102L)
                .build();
        GroupDTO groupDTOToUpdate = GroupDTO.builder()
                .number(102L)
                .build();

        when(groupMapper.toEntity(groupDTOToUpdate)).thenReturn(groupToUpdate);
        when(groupRepository.update(groupToUpdate)).thenReturn(util.getExpectedGroup());
        when(groupMapper.toDTO(util.getExpectedGroup())).thenReturn(util.getExpectedGroupDTO());

        GroupDTO updatedGroupDTO = groupService.update(groupDTOToUpdate);

        assertEquals(1L, updatedGroupDTO.getId());
        assertEquals(101L, updatedGroupDTO.getNumber());
    }


}


