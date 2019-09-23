package com.kclmedu.springboot.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration //表示自己是一个配置类
@EnableTransactionManagement //启动事务管理器
public class MasterDataSourceConfig {

    @Bean("masterDS")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        //设置 数据源属性
        DataSource ds = DataSourceBuilder.create().build();
        System.out.println("-- 创建主数据源："+ds);
        //返回
        return ds;
    }

    @Bean(name = "masterSesssionFactory")
    public SqlSessionFactory masterSessionFactory(
            @Qualifier("masterDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean =
                    new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //如果要指定 映射文件的位置
        sqlSessionFactoryBean
            .setTypeAliasesPackage("com.kclmedu.springboot.entity");
        //
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="masterTxManager")
    public PlatformTransactionManager masterTxManager(
                    @Qualifier("masterDS") DataSource dataSource) {
        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
        dstm.setDataSource(dataSource);
        //
        return dstm;
    }
}
