import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class Counter {
    public static final Object lock = new Object();
    public static int count = 0;
}

public class MySQLTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123";

    private static Instant start;

    public static void main(String[] args) throws Exception {
        System.out.println("Application started!");

        Connection conn = jdbcConnection();

        createTable(conn);

        System.out.println(insertData(conn));
        System.out.println(insertData(connectionPool()));
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
        config.addDataSourceProperty("maximumPoolSize", "10000"); // 最大连接数：10
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource.getConnection();
    }

    private static void createTable(Connection conn) throws SQLException {
        final Statement stmt = conn.createStatement();
        try {
            stmt.execute("""
                    CREATE TABLE students (
                      id BIGINT AUTO_INCREMENT NOT NULL,
                      name VARCHAR(50) NOT NULL,
                      gender TINYINT(1) NOT NULL,
                      grade INT NOT NULL,
                      score INT NOT NULL,
                      PRIMARY KEY(id)
                    ) Engine=MEMORY DEFAULT CHARSET=UTF8;
                    """);
        } catch (Exception exception){
            System.out.println("Table had been created, don't create again.");
        }
    }

    private static Long insertData(Connection conn) throws SQLException {
        start = Instant.now();
        Queue<String> sql = new LinkedList<>();
        for (long i = 0; i < 1000; i++) {
            sql.add("INSERT INTO students (name, gender, grade, score) VALUES ('小明', 1, 1, 88);");
        }
        Long result = ForkJoinPool.commonPool().invoke(new InsertTask(conn, sql));
        report();
        return result;
    }

    private static void report() {
        Instant end = Instant.now();
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
                System.out.println(id + "：" + grade + name + gender);
            }
        }
    }
}

class InsertTask extends RecursiveTask<Long> {

    private final Connection conn;
    private final Queue<String> sql;

    InsertTask(Connection conn, Queue<String> sql) {
        this.conn = conn;
        this.sql = sql;
    }

    @Override
    protected Long compute() {
        try {
            String s = this.sql.poll();
            Long count = 1L;
            if (this.sql.size() > 0) {
                InsertTask task = new InsertTask(this.conn, this.sql);
                invokeAll(task);
                count += task.join();;
            }
            this.conn.createStatement().execute(s);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}