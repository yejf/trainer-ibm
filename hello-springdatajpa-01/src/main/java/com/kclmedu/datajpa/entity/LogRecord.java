package com.kclmedu.datajpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "TBL_LOG_RECORD")
public class LogRecord {

    @Id  //表明是主键
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "record_time")
    private Date recordTime;

    @Column
    private String user;

    public LogRecord() {
    }

    public LogRecord(String title, String content, Date recordTime, String user) {
        this.title = title;
        this.content = content;
        this.recordTime = recordTime;
        this.user = user;
    }
}
