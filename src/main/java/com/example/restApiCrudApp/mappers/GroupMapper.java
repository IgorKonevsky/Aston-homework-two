package com.example.restApiCrudApp.mappers;

import com.example.restApiCrudApp.dto.GroupDTO;
import com.example.restApiCrudApp.entities.Group;
import org.mapstruct.Mapper;

@Mapper
public interface GroupMapper extends MapperTemplate<Group, GroupDTO> {

    @Override
    Group toEntity(GroupDTO groupDTO);

    @Override
    GroupDTO toDTO(Group group);

}
