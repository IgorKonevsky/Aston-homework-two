package com.example.restApiCrudApp.repositories;

import com.example.restApiCrudApp.entities.Subject;

public interface SubjectRepository extends RepositoryTemplate<Subject> {
    String getSubjectNameByTeacherId(Long id);
}
