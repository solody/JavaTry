import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Application started!!!!!!==============");

        // JDBC连接的URL, 不同数据库有不同的格式:
        String JDBC_URL = "jdbc:mysql://localhost:3333/test";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "123";



        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(JDBC_USER);
        config.setPassword(JDBC_PASSWORD);
        config.addDataSourceProperty("connectionTimeout", "1000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "1000"); // 最大连接数：10
        final DataSource ds = new HikariDataSource(config);


/*
                try {
                    boolean created = stmt.execute("CREATE TABLE students (\n" +
                            "  id BIGINT AUTO_INCREMENT NOT NULL,\n" +
                            "  name VARCHAR(50) NOT NULL,\n" +
                            "  gender TINYINT(1) NOT NULL,\n" +
                            "  grade INT NOT NULL,\n" +
                            "  score INT NOT NULL,\n" +
                            "  PRIMARY KEY(id)\n" +
                            ") Engine=INNODB DEFAULT CHARSET=UTF8;");
                } catch (Exception exception){

                }*/

        Connection conn = ds.getConnection();
        final Statement stmt = conn.createStatement();
                for (long i = 0; i < 4000L; i++){
                    Thread t = new Thread() {
                        public void run() {
                            for (long ii = 0; ii < 10L; ii++) {

                                try {
                                    final String insert = "INSERT INTO students (name, gender, grade, score) VALUES ('小明" + ii + "', 1, 1, 88);";
                                    System.out.println(insert);

                                    stmt.execute(insert);
                                } catch (Exception exception) {
                                    System.out.println(exception.toString());
                                }
                            }
                        }
                    };
                    t.start();
                }

        Connection conn2 = ds.getConnection();
        final Statement stmt2 = conn2.createStatement();
        try (ResultSet rs = stmt2.executeQuery("SELECT id, grade, name, gender FROM students WHERE gender=1")) {
                    while (rs.next()) {
                        long id = rs.getLong(1); // 注意：索引从1开始
                        long grade = rs.getLong(2);
                        String name = rs.getString(3);
                        int gender = rs.getInt(4);
                        System.out.println(id);
                    }
                }
    }
}
