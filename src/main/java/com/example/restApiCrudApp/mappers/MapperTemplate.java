package com.example.restApiCrudApp.mappers;

public interface MapperTemplate<Entity, DTO> {
    Entity toEntity(DTO dto);

    DTO toDTO(Entity entity);
}
