package com.example.restApiCrudApp.services.impl;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.dto.IdDTO;
import com.example.restApiCrudApp.dto.TeacherDTO;
import com.example.restApiCrudApp.entities.Group;
import com.example.restApiCrudApp.entities.Teacher;
import com.example.restApiCrudApp.mappers.GroupMapper;
import com.example.restApiCrudApp.mappers.GroupMapperImpl;
import com.example.restApiCrudApp.mappers.TeacherMapper;
import com.example.restApiCrudApp.mappers.TeacherMapperImpl;
import com.example.restApiCrudApp.repositories.GroupRepository;
import com.example.restApiCrudApp.repositories.SubjectRepository;
import com.example.restApiCrudApp.repositories.TeacherRepository;
import com.example.restApiCrudApp.repositories.impl.GroupRepositoryImpl;
import com.example.restApiCrudApp.repositories.impl.SubjectRepositoryImpl;
import com.example.restApiCrudApp.repositories.impl.TeacherRepositoryImpl;
import com.example.restApiCrudApp.services.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;

    private final SubjectRepository subjectRepository;
    private final TeacherMapper teacherMapper;
    private final GroupMapper groupMapper;

    public TeacherServiceImpl() {
        this.teacherRepository = new TeacherRepositoryImpl();
        this.groupRepository = new GroupRepositoryImpl();
        this.subjectRepository = new SubjectRepositoryImpl();
        this.teacherMapper = new TeacherMapperImpl();
        this.groupMapper = new GroupMapperImpl();

    }

    public TeacherServiceImpl(TeacherRepository teacherRepository, GroupRepository groupRepository, SubjectRepository subjectRepository, TeacherMapper teacherMapper, GroupMapper groupMapper) {
        this.teacherRepository = teacherRepository;
        this.groupRepository = groupRepository;
        this.subjectRepository = subjectRepository;
        this.teacherMapper = teacherMapper;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<TeacherDTO> findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDTO> teacherDTOS = teachers.stream().map(teacherMapper::toDTO).toList();
//        for (TeacherDTO teacherDTO : teacherDTOS){
//            String subjectName = pullSubjectName(teacherDTO.getId());
//            teacherDTO.setSubjectName(subjectName);
//        }
        return teacherDTOS;
    }

    @Override
    public TeacherDTO findById(Long id) {
        Teacher teacher = teacherRepository.findById(id);
        TeacherDTO teacherDTO = teacherMapper.toDTO(teacher);
        String subjectName = pullSubjectName(teacherDTO.getId());
        teacherDTO.setSubjectName(subjectName);
        return teacherDTO;
    }

    @Override
    public TeacherDTO create(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        Teacher createdTeacher = teacherRepository.create(teacher);
        return teacherMapper.toDTO(createdTeacher);
    }

    @Override
    public TeacherDTO update(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        Teacher updatedTeacher = teacherRepository.update(teacher);
        return teacherMapper.toDTO(updatedTeacher);
    }

    @Override
    public void delete(Long id) {
        teacherRepository.delete(id);
    }

    @Override
    public List<GroupDTO> pullGroupsByTeacherId(Long id) {
        List<Group> groups = groupRepository.findGroupsByTeacherId(id);
        List<GroupDTO> groupDTOS = groups.stream().map(groupMapper::toDTO).toList();
        return groupDTOS;
    }

    @Override
    public List<TeacherDTO> findAllWithGroups() {
        List<TeacherDTO> teacherDTOS = findAll();
        for (TeacherDTO teacherDTO : teacherDTOS) {
            List<GroupDTO> groupDTOS = pullGroupsByTeacherId(teacherDTO.getId());
            teacherDTO.setGroupDTOS(groupDTOS);
        }
        return teacherDTOS;
    }

    @Override
    public String pullSubjectName(Long id) {
        return subjectRepository.getSubjectNameByTeacherId(id);
    }

    @Override
    public IdDTO linkTeacherToGroup(IdDTO ids) {
        teacherRepository.linkTeacherToGroup(ids.getTeacherId(), ids.getGroupId());
        return ids;
    }
}
