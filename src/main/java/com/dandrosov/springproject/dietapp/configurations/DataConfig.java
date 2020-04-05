//package com.dandrosov.springproject.dietapp.configurations;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//@ConfigurationProperties(prefix = "spring.datasource")
//public class DataConfig {
//
//    @Bean(name = "dataSource")
//    @Primary
//    public DataSource getDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//}
