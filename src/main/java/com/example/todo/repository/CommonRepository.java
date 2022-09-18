package com.example.todo.repository;

import java.util.Collection;

public interface CommonRepository<T> {

    Iterable<T> findAll();

    T findById(String id);

    T save(T domain);

    Iterable<T> save(Collection<T> domains);

    void delete(T domain);
}
