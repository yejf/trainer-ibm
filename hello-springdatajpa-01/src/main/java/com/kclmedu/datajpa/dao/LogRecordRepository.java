package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  //可选
public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {

    //nothing!

}
