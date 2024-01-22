package com.example.restApiCrudApp.services.impl;

import com.example.restApiCrudApp.dto.SubjectDTO;
import com.example.restApiCrudApp.entities.Subject;
import com.example.restApiCrudApp.mappers.SubjectMapper;
import com.example.restApiCrudApp.mappers.SubjectMapperImpl;
import com.example.restApiCrudApp.repositories.SubjectRepository;
import com.example.restApiCrudApp.repositories.impl.SubjectRepositoryImpl;
import com.example.restApiCrudApp.services.SubjectService;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public SubjectServiceImpl() {
        this.subjectRepository = new SubjectRepositoryImpl();
        this.subjectMapper = new SubjectMapperImpl();
    }

    public SubjectServiceImpl(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public List<SubjectDTO> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectDTO> subjectDTOS = subjects.stream().map(subjectMapper::toDTO).toList();
        return subjectDTOS;
    }

    @Override
    public SubjectDTO findById(Long id) {
        Subject subject = subjectRepository.findById(id);
        return subjectMapper.toDTO(subject);
    }

    @Override
    public SubjectDTO create(SubjectDTO subjectDTO) {
        Subject subject = subjectMapper.toEntity(subjectDTO);
        Subject createdSubject = subjectRepository.create(subject);
        return subjectMapper.toDTO(createdSubject);
    }

    @Override
    public SubjectDTO update(SubjectDTO subjectDTO) {
        Subject subject = subjectMapper.toEntity(subjectDTO);
        Subject updatedSubject = subjectRepository.update(subject);
        return subjectMapper.toDTO(updatedSubject);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.delete(id);
    }
}
