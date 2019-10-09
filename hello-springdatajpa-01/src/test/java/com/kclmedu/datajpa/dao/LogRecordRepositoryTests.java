package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.LogRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogRecordRepositoryTests {

    @Autowired
    private LogRecordRepository logRecordRepository;

    @Test
    public void testSave() {
        LogRecord record = new LogRecord();
        record.setTitle("更新操作");
        record.setContent("更新了一条日志记录");
        record.setRecordTime(new Date());
        record.setUser("jack");
        //
        logRecordRepository.save(record);
    }

    @Test
    public void testFindAll() {
        List<LogRecord> records = logRecordRepository.findAll();
        //
        if(records != null) {
            records.forEach(System.out::println);
        }
    }

    @Test
    public void testDelete() {
        logRecordRepository.deleteById(6L);
        testFindAll();
    }

    @Test
    public void testUpdate() {
        Optional<LogRecord> record = logRecordRepository.findById(2L);
        //
        System.out.println(record.get());
        //更新
        LogRecord logRecord = record.get();
        logRecord.setContent("xxxxxxxhehehehe");
        logRecord.setUser("jack");
        //
        logRecordRepository.saveAndFlush(logRecord);
    }

    @Test
    public void testFindByTitle() {
        List<LogRecord> records = logRecordRepository.findByTitle("标题一");
        //
        if(records != null) {
            records.forEach(System.out::println);
        }
    }

    @Test
    public void testFindByTitleLike() {
        List<LogRecord> records = logRecordRepository.findByTitleContaining("操作");
        //
        if(records != null) {
            records.forEach(System.out::println);
        }
    }

    @Test
    public void testQueryByTitleAndUser() {
        List<LogRecord> logRecords = logRecordRepository.queryByTitleAndUser("添加操作", "admin");
        //
        logRecords.forEach(System.out::println);
    }

    @Test
    public void testQueryByTitleAndUser2() {
        List<LogRecord> logRecords = logRecordRepository.queryByTitleAndUser2("添加操作", "admin");
        //
        logRecords.forEach(System.out::println);
    }

    @Test
    public void testFindTitleLike() {
        List<LogRecord> logRecords = logRecordRepository.findTitleLike("操作");
        //
        logRecords.forEach(System.out::println);
    }

    @Test
    public void testFindByRecordTime() throws Exception{
        List<LogRecord> logRecords = logRecordRepository.
                            findByRecordTimeBetween(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-09-30 00:00:00"),new Date());
        //
        logRecords.forEach(System.out::println);
    }

    @Test
    public void testFindByUserIn() {
        List<LogRecord> logRecords = logRecordRepository.findByUserIn(Arrays.asList("jack","admin"));
        //
        logRecords.forEach(System.out::println);
    }

    @Test
    public void testFindByLog() {
        LogRecord log = new LogRecord();
        log.setTitle("操作一");
        log.setUser("jack");
        List<LogRecord> logRecords = logRecordRepository.findByLog(log);
        //
        logRecords.forEach(System.out::println);
    }

    @Test
    public void testFindAllPage() {
        Page<LogRecord> page = logRecordRepository.findAllPage(PageRequest.of(0,3, Sort.Direction.DESC,"id"));
        //
        System.out.println(page);
        page.getContent().forEach(System.out::println);
    }
}
