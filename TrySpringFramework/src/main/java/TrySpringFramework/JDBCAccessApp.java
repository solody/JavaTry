package TrySpringFramework;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class JDBCAccessApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("JDBCAccess.xml");
        JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource) context.getBean("dataSource"));

        try {
            jdbcTemplate.update("""
                    CREATE TABLE students2 (
                      id BIGINT AUTO_INCREMENT NOT NULL,
                      name VARCHAR(50) NOT NULL,
                      gender TINYINT(1) NOT NULL,
                      grade INT NOT NULL,
                      score INT NOT NULL,
                      PRIMARY KEY(id)
                    ) Engine=MEMORY DEFAULT CHARSET=UTF8;
                    """);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        jdbcTemplate.update("INSERT INTO students2 (name, gender, grade, score) VALUES (?, ?, ?, ?);", "小明", 1, 1, 99);
        jdbcTemplate.update("drop table students2");
    }
}
