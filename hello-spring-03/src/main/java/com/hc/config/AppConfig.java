package com.hc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.hc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/***********************
 * Spring 注解配置类
 */
@Configuration
@ComponentScan(value = {"com.hc.service.impl",
                        "com.hc.dao.impl",
                        "com.hc.aspect"})
@EnableAspectJAutoProxy  //表示启动AOP 机制
//通过PropertySource读取的属性文件，会存放在 Environment 对象中
@PropertySource(value = "classpath:db.properties")
@EnableTransactionManagement   //启动事务管理器
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean //把此方法的返回值由ioc容器进行管理
    public DataSource dataSource() {
        //使用druid数据源
        DruidDataSource dds = new DruidDataSource();
        //属性设置
        dds.setUrl(env.getProperty("url"));
        dds.setDriverClassName(env.getProperty("driver"));
        dds.setUsername(env.getProperty("user"));
        dds.setPassword(env.getProperty("password"));
        //
        String initialSize = env.getProperty("initialSize");
        if(initialSize != null && initialSize.trim().length() > 0) {
            dds.setInitialSize(Integer.parseInt(initialSize));
        }
        String maxActive = env.getProperty("maxActive");
        if(maxActive != null && maxActive.trim().length() > 0){
            dds.setMaxActive(Integer.parseInt(maxActive));
        }
        String maxWait = env.getProperty("maxWait");
        if(maxWait != null && maxWait.trim().length() >0) {
            dds.setMaxWait(Long.parseLong(maxWait));
        }
        //返回
        return dds;
    }

    @Bean
    public JdbcTemplate template(@Autowired DataSource dataSource) {
        //
        JdbcTemplate template = new JdbcTemplate(dataSource);
        //
        return template;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        PlatformTransactionManager tx =
                new DataSourceTransactionManager(dataSource);
        //
        return tx;
    }

}
