package com.cy.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() {
    }

    /**
     * 数据库连接池:
     *          1. DBCP
     *          2. C3P0
     *          3. Hikari：springboot默认的数据库连接池
     *          4. Druid: 德鲁伊 阿里巴巴开发的数据库连接池
     */

    @Test
    void getConnection() throws SQLException {
        System.out.println(dataSource.getConnection());

    }

}
