package com.example.tryspringdata;

import com.example.tryspringdata.dao.JdbcTemplateAccess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrySpringDataApplicationTests {

    @Autowired
    private JdbcTemplateAccess jdbcTemplateAccess;

    @Test
    void contextLoads() {
        jdbcTemplateAccess.doSomething();
    }

}
