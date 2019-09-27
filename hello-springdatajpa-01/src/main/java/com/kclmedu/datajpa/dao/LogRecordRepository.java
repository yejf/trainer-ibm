package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.LogRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  //可选
public interface LogRecordRepository extends JpaRepository<LogRecord, Long> {

    //可以自定义方法，方法名要遵守一定的语义规则，以便让Spring data jpa可以“充份”
    //理解你的意图。如：
    List<LogRecord> findByTitle(String title);

    //如果想要模糊匹配的话，则
    List<LogRecord> findByTitleContaining(String title); // %title%
    List<LogRecord> findByTitleStartingWith(String title); // title%
    List<LogRecord> findByTitleEndingWith(String title); // %title

    //如果使用 标题和作者两个条件去查询，则
    List<LogRecord> findByTitleAndUser(String title,String user);

}
