package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.LogRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    //使用@Query注解
    @Query("from LogRecord r where r.title = ?1 and r.user = ?2")
    List<LogRecord> queryByTitleAndUser(String title, String user);

    @Query("from LogRecord r where r.title = :title and r.user = :user")
    List<LogRecord> queryByTitleAndUser2(@Param("title") String title, @Param("user") String user);

    @Query("from LogRecord r where r.title like %:titleLike%")
    List<LogRecord> findTitleLike(@Param("titleLike") String title);

    @Query("from LogRecord lr where lr.recordTime between :start And :end")
    List<LogRecord> findByRecordTimeBetween(@Param("start") Date start, @Param("end") Date end);

    @Query("from LogRecord lr where lr.user in :users")
    List<LogRecord> findByUserIn(@Param("users") List<String> users);

    @Query("from LogRecord lr where lr.title = :#{#log.title} and lr.user = :#{#log.user}")
    List<LogRecord> findByLog(@Param("log") LogRecord logRecord);

    /*****
     * 只要方法的参数加了 Pageable，则Spring data jpa就会认为此方法要做分页查询
     *
     * @param pageable
     * @return
     */
    @Query("select lr from LogRecord lr")
    Page<LogRecord> findAllPage(Pageable pageable);
}
