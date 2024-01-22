package com.example.restApiCrudApp.mappers;

import com.example.restApiCrudApp.dto.TeacherDTO;
import com.example.restApiCrudApp.entities.Teacher;
import org.mapstruct.Mapper;

@Mapper
public interface TeacherMapper extends MapperTemplate<Teacher, TeacherDTO> {
    @Override
    Teacher toEntity(TeacherDTO teacherDTO);

    @Override
    TeacherDTO toDTO(Teacher teacher);
}
