package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.User;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = String.class)
public interface UserRepository {

    //这里的方法定义参考 CrudRepository 接口或者是 JpaRepository

    User save(User user);

    Optional<User> findById(String id);

    Iterable<User> findAll();

}
