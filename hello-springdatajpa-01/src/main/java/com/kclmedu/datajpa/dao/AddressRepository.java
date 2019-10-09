package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.dao.common.BaseRepository;
import com.kclmedu.datajpa.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends BaseRepository<Address, String> {

    //nothing!

    //1.多对一， 可以使用隐式关联
    @Query("from Address a where a.user.name = :uname")
    List<Address> findByUserName(@Param("uname") String userName);

}
