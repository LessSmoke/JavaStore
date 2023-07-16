package com.cy.store;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
// MapperScan注解指定当前项目中的Mapper接口的路径位置，在项目启动的时候会自动加载所有的mapper接口
@MapperScan(basePackages = "com.cy.store.mapper")
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
        log.info("项目启动成功");
    }

}
