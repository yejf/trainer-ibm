package com.kclmedu.datajpa.dao.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {

    <S extends T> S save(S var1);

    Optional<T> findById(ID var1);

    Iterable<T> findAll();

    void deleteById(ID var1);

    void delete(T var1);

}
