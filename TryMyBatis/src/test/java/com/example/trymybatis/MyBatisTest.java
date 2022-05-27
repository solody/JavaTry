package com.example.trymybatis;

import com.example.trymybatis.entity.People;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;

public class MyBatisTest {
    @Test
    void testMapping() throws IOException {
        String resource = "com/example/trymybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        String init_script = "com/example/trymybatis/init-db.sql";
        Reader init_script_stream = Resources.getResourceAsReader(init_script);

        try (SqlSession session = sqlSessionFactory.openSession()) {

            ScriptRunner scriptRunner = new ScriptRunner(session.getConnection());
            scriptRunner.runScript(init_script_stream);

            People people = new People("kent", 100);

            session.insert("com.example.trymybatis.insertPeople", people);

            People people2 = (People) session.selectOne("com.example.trymybatis.selectPeople", 1);
            Assertions.assertInstanceOf(People.class, people2);

            session.update("com.example.trymybatis.dropPeoples");
        }
    }
}
