package com.example.tryspringdata.dao;

import com.example.tryspringdata.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SqlResolve")
@Repository
public class JdbcTemplateAccess {

    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateAccess.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAccess(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc-JdbcTemplate
     */
    public void doSomething() {
        // Initial the table.
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

        tryJdbcTemplate();
        tryNamedParameterJdbcTemplate();

        jdbcTemplate.execute("drop table students;");
    }

    /**
     * Try default style jdbc template access.
     */
    private void tryJdbcTemplate() {

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
    }

    /**
     * Named parameter jdbc template interface.
     */
    private void tryNamedParameterJdbcTemplate() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        Map<String, Object> params = new HashMap<>();
        params.put("name", "小王");
        params.put("gender", 2);
        params.put("grade", 2);
        params.put("score", 99);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(params);
        int effect = namedParameterJdbcTemplate.update(
                "INSERT INTO students (name, gender, grade, score) VALUES (:name, :gender, :grade, :score)",
                sqlParameterSource
        );

        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("name", "小王");
        Integer id = namedParameterJdbcTemplate.queryForObject(
                "SELECT id FROM students WHERE name=:name",
                queryParams, Integer.class
        );

        log.info("The queried id is : " + id);
    }

}
