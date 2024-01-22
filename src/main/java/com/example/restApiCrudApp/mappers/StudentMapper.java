package com.example.restApiCrudApp.mappers;

import com.example.restApiCrudApp.dto.StudentDTO;
import com.example.restApiCrudApp.entities.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper extends MapperTemplate<Student, StudentDTO> {
    @Override
    Student toEntity(StudentDTO studentDTO);

    @Override
    StudentDTO toDTO(Student student);
}
