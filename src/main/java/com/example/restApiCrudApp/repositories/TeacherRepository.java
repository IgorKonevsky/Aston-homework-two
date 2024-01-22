package com.example.restApiCrudApp.repositories;

import com.example.restApiCrudApp.entities.Teacher;

import java.util.List;

public interface TeacherRepository extends RepositoryTemplate<Teacher> {
    List<Teacher> findTeachersByGroupId(Long id);

    void linkTeacherToGroup(Long teacherId, Long groupId);
}
