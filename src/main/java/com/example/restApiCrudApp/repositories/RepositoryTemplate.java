package com.example.restApiCrudApp.repositories;


import java.util.List;

public interface RepositoryTemplate<Entity> {
    public List<Entity> findAll();

    public Entity findById(Long id);

    public Entity create(Entity entity);

    public Entity update(Entity entity);

    public void delete(Long id);


}
