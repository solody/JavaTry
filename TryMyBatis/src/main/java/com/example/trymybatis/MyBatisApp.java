package com.example.trymybatis;

import com.example.trymybatis.entity.People;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisApp {
    public static void main(String[] args) throws IOException {
        String resource = "com/example/trymybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            People people = (People) session.selectOne("com.example.trymybatis.mappers.PeopleMapper.selectPeople", 1);
            System.out.println(people.getName());
        }
    }
}
