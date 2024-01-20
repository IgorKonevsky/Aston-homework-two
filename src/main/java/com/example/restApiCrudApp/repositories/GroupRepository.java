package com.example.restApiCrudApp.repositories;

import com.example.restApiCrudApp.entities.Group;

import java.util.List;

public interface GroupRepository extends RepositoryTemplate<Group> {

    List<Group> findGroupsByTeacherId(Long id);
}
