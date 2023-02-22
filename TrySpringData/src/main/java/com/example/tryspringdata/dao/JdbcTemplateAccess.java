package com.example.tryspringdata.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateAccess {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc-JdbcTemplate
     */
    public void doSomething() {
        jdbcTemplate.execute("""
            DROP TABLE IF EXISTS `students`;
            """);
        jdbcTemplate.execute("""
            -- 创建表students:
            CREATE TABLE students (
              id BIGINT AUTO_INCREMENT NOT NULL,
              name VARCHAR(50) NOT NULL,
              gender TINYINT(1) NOT NULL,
              grade INT NOT NULL,
              score INT NOT NULL,
              PRIMARY KEY(id)
            ) Engine=INNODB DEFAULT CHARSET=UTF8;
            """);
        jdbcTemplate.update(
                "INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)",
                "小明", 1, 1, 88);
        this.jdbcTemplate.update(
                "update students set score = ? where name = ?",
                100, "小明");
        int rowCount = this.jdbcTemplate.queryForObject("select count(*) from students", Integer.class);
        System.out.println("Count: " + rowCount);

        String name = this.jdbcTemplate.queryForObject(
                "select name from students where score = ?",
                String.class, 100);
        System.out.println(name + "score is: 100");

        Student student = jdbcTemplate.queryForObject(
                "select id, name, score from students where id = ?",
                (resultSet, rowNum) -> {
                    Student student_temp = new Student();
                    student_temp.setId(resultSet.getLong("id"));
                    student_temp.setName(resultSet.getString("name"));
                    return student_temp;
                },
                1L);

        System.out.println(student.getName());

        jdbcTemplate.update(
                "INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?)",
                "小红", 1, 1, 88);
        List<Student> studentList = this.jdbcTemplate.query(
                "select id, name, score from students",
                (resultSet, rowNum) -> {
                    Student student_temp = new Student();
                    student_temp.setId(resultSet.getLong("id"));
                    student_temp.setName(resultSet.getString("name"));
                    return student_temp;
                });
        System.out.println(studentList.size());

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

        Map<String, Object> params = new HashMap<>();
        params.put("name", "小王");
        params.put("gender", 2);
        params.put("grade", 2);
        params.put("score", 99);
        int effect = namedParameterJdbcTemplate.update("INSERT INTO students (name, gender, grade, score) VALUES (:name, :gender, :grade, :score)", params);
        System.out.println(effect);

        jdbcTemplate.execute("drop table students;");
    }

    class Student {
        private Long id;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
