package com.example.restApiCrudApp.services.impl;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.dto.TeacherDTO;
import com.example.restApiCrudApp.entities.Group;
import com.example.restApiCrudApp.entities.Teacher;
import com.example.restApiCrudApp.mappers.GroupMapper;
import com.example.restApiCrudApp.mappers.GroupMapperImpl;
import com.example.restApiCrudApp.mappers.TeacherMapper;
import com.example.restApiCrudApp.mappers.TeacherMapperImpl;
import com.example.restApiCrudApp.repositories.GroupRepository;
import com.example.restApiCrudApp.repositories.TeacherRepository;
import com.example.restApiCrudApp.repositories.impl.GroupRepositoryImpl;
import com.example.restApiCrudApp.repositories.impl.TeacherRepositoryImpl;
import com.example.restApiCrudApp.services.GroupService;

import java.util.List;

public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final TeacherRepository teacherRepository;

    private final GroupMapper groupMapper;
    private final TeacherMapper teacherMapper;

    public GroupServiceImpl() {
        this.groupRepository = new GroupRepositoryImpl();
        this.teacherRepository = new TeacherRepositoryImpl();
        this.groupMapper = new GroupMapperImpl();
        this.teacherMapper = new TeacherMapperImpl();
    }

    public GroupServiceImpl(GroupRepository groupRepository, TeacherRepository teacherRepository, GroupMapper groupMapper, TeacherMapper teacherMapper) {
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.groupMapper = groupMapper;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<TeacherDTO> pullTeachersByGroupId(Long id) {
        List<Teacher> teachers = teacherRepository.findTeachersByGroupId(id);
        List<TeacherDTO> teacherDTOS = teachers.stream().map(teacherMapper::toDTO).toList();
        return teacherDTOS;

    }

    @Override
    public List<GroupDTO> findAllWithTeachers() {
        List<GroupDTO> groupDTOS = findAll();
        for (GroupDTO groupDTO : groupDTOS) {
            List<TeacherDTO> teacherDTOS = pullTeachersByGroupId(groupDTO.getId());
            groupDTO.setTeacherDTOS(teacherDTOS);
        }
        return groupDTOS;
    }

    @Override
    public List<GroupDTO> findAll() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOS = groups.stream().map(groupMapper::toDTO).toList();
        return groupDTOS;
    }

    @Override
    public GroupDTO findById(Long id) {
        Group group = groupRepository.findById(id);
        return groupMapper.toDTO(group);
    }

    @Override
    public GroupDTO create(GroupDTO groupDTO) {
        Group group = groupMapper.toEntity(groupDTO);
        Group createdGroup = groupRepository.create(group);
        return groupMapper.toDTO(createdGroup);
    }

    @Override
    public GroupDTO update(GroupDTO groupDTO) {
        Group group = groupMapper.toEntity(groupDTO);
        Group updatedGroup = groupRepository.update(group);
        return groupMapper.toDTO(updatedGroup);

    }


    @Override
    public void delete(Long id) {
        groupRepository.delete(id);
    }
}
