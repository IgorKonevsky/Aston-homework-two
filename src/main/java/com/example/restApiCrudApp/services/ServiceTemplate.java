package com.example.restApiCrudApp.services;

import java.util.List;

public interface ServiceTemplate<T> {

    List<T> findAll();

    T findById(Long id);

    T create(T t);

    T update(T t);

    void delete(Long id);
}
