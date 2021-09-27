import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Counter {
    public static final Object lock = new Object();
    public static int count = 0;
}

public class MySQLTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123";

    private static Instant start;
    private static Instant end;

    public static void main(String[] args) throws Exception {
        System.out.println("Application started!!!!!!==============");

        Connection conn = jdbcConnection();

        createTable(conn);

        start = Instant.now();
        insertData(conn);
        //insertData(connectionPool());
    }

    private static Connection jdbcConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private static Connection connectionPool() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(JDBC_USER);
        config.setPassword(JDBC_PASSWORD);
        config.addDataSourceProperty("connectionTimeout", "1000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "1000"); // 最大连接数：10
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource.getConnection();
    }

    private static void createTable(Connection conn) throws SQLException {
        final Statement stmt = conn.createStatement();
        try {
            boolean created = stmt.execute("""
                    CREATE TABLE students (
                      id BIGINT AUTO_INCREMENT NOT NULL,
                      name VARCHAR(50) NOT NULL,
                      gender TINYINT(1) NOT NULL,
                      grade INT NOT NULL,
                      score INT NOT NULL,
                      PRIMARY KEY(id)
                    ) Engine=INNODB DEFAULT CHARSET=UTF8;
                    """);
        } catch (Exception exception){
            System.out.println("Table had been created, don't create again.");
        }
    }

    private static void insertData(Connection conn) throws SQLException {
        final Statement stmt = conn.createStatement();
        // 创建一个固定大小的线程池:
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (long i = 0; i < 400L; i++){
            Thread t = new Thread() {
                public void run() {
                    for (long ii = 0; ii < 2L; ii++) {

                        try {
                            final String insert = "INSERT INTO students (name, gender, grade, score) VALUES ('小明" + ii + "', 1, 1, 88);";
                            System.out.println(insert);

                            stmt.execute(insert);
                        } catch (Exception exception) {
                            System.out.println(exception.toString());
                        }
                    }
                    synchronized(Counter.lock) { // 获取锁
                        Counter.count--;
                        if (Counter.count == 0) {
                            report();
                        }
                    } // 释放锁
                }
            };
            es.submit(t);
            Counter.count++;
        }
        // 关闭线程池:
        es.shutdown();
    }

    private static void report() {
        end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();
        System.out.println("用时" + timeElapsed + "毫秒");
    }

    private static void countData(Connection conn) throws SQLException {
        final Statement stmt = conn.createStatement();
        try (ResultSet rs = stmt.executeQuery("SELECT id, grade, name, gender FROM students WHERE gender=1")) {
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
