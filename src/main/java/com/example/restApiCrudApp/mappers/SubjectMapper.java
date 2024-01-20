package com.example.restApiCrudApp.mappers;

import com.example.restApiCrudApp.dto.SubjectDTO;
import com.example.restApiCrudApp.entities.Subject;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper extends MapperTemplate<Subject, SubjectDTO> {

    @Override
    Subject toEntity(SubjectDTO subjectDTO);

    @Override
    SubjectDTO toDTO(Subject subject);
}
