package com.example.restApiCrudApp.services;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.dto.IdDTO;
import com.example.restApiCrudApp.dto.TeacherDTO;

import java.util.List;

public interface TeacherService extends ServiceTemplate<TeacherDTO> {
    List<GroupDTO> pullGroupsByTeacherId(Long id);

    List<TeacherDTO> findAllWithGroups();

    String pullSubjectName(Long id);

    IdDTO linkTeacherToGroup(IdDTO ids);
}
