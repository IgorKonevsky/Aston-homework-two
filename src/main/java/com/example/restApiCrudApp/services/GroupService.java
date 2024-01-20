package com.example.restApiCrudApp.services;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.dto.TeacherDTO;

import java.util.List;

public interface GroupService extends ServiceTemplate<GroupDTO> {
    List<TeacherDTO> pullTeachersByGroupId(Long id);

    List<GroupDTO> findAllWithTeachers();
}
