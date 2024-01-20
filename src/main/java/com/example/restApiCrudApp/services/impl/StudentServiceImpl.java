package com.example.restApiCrudApp.services.impl;

import com.example.restApiCrudApp.dto.StudentDTO;
import com.example.restApiCrudApp.entities.Student;
import com.example.restApiCrudApp.mappers.StudentMapper;
import com.example.restApiCrudApp.mappers.StudentMapperImpl;
import com.example.restApiCrudApp.repositories.StudentRepository;
import com.example.restApiCrudApp.repositories.impl.StudentRepositoryImpl;
import com.example.restApiCrudApp.services.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentServiceImpl() {
        this.studentRepository = new StudentRepositoryImpl();
        this.studentMapper = new StudentMapperImpl();
    }

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<StudentDTO> findAll() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = students.stream().map(studentMapper::toDTO).toList();
        return studentDTOS;
    }

    @Override
    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id);
        return studentMapper.toDTO(student);
    }

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        Student createdStudent = studentRepository.create(student);
        return studentMapper.toDTO(createdStudent);
    }

    @Override
    public StudentDTO update(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        Student updatedStudent = studentRepository.update(student);
        return studentMapper.toDTO(updatedStudent);
    }

    @Override
    public void delete(Long id) {
        studentRepository.delete(id);
    }


}
