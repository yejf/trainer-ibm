package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.LogRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogRecordRepositoryTests {

    @Autowired
    private LogRecordRepository logRecordRepository;

    @Test
    public void testSave() {
        LogRecord record = new LogRecord();
        record.setTitle("添加操作");
        record.setContent("添加了一条日志记录");
        record.setRecordTime(new Date());
        record.setUser("admin");
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
}
