package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.entity.LogRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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
}
