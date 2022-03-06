package TryMySQL;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3333/test";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123";

    private static Instant start;

    public static void main(String[] args) throws SQLException {

        Connection conn = jdbcConnection();
        createTable(conn);
        System.out.println("Inserted: " + insertData(conn));
        System.out.println("Inserted: " + insertDataBatch(conn));
        System.out.println("Inserted: " + insertDataMultiThread(conn));
        System.out.println("Fetched: " + fetchRows(conn));
        dropTable(conn);
    }

    private static Connection jdbcConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
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

    private static void dropTable(Connection conn) throws SQLException {
        final Statement stmt = conn.createStatement();
        try {
            stmt.execute("""
                    DROP TABLE students;
                    """);
        } catch (Exception exception){
            System.out.println("Table had been dropped, don't drop again.");
        }
    }

    private static Long insertData(Connection conn) throws SQLException {
        start = Instant.now();
        Long rows = 0L;
        for (long i = 0; i < 1000; i++) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?);");
            ps.setObject(1, "小明"); // 注意：索引从1开始
            ps.setObject(2, 1);
            ps.setObject(3, 1);
            ps.setObject(4, 88);
            rows += ps.executeUpdate();
        }
        reportTimeElapsed();
        return rows;
    }

    private static Long insertDataBatch(Connection conn) throws SQLException {
        start = Instant.now();
        Long rows = 0L;
        PreparedStatement ps = conn.prepareStatement("INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?);");
        for (long i = 0; i < 1000; i++) {
            ps.setObject(1, "小明"); // 注意：索引从1开始
            ps.setObject(2, 1);
            ps.setObject(3, 1);
            ps.setObject(4, 88);
            ps.addBatch();
        }
        // 执行batch:
        int[] ns = ps.executeBatch();
        for (int n : ns) {
            // batch中每个SQL执行的结果数量
            rows += n;
        }
        reportTimeElapsed();
        return rows;
    }

    private static Long insertDataMultiThread(Connection conn) throws SQLException {
        start = Instant.now();
        // 创建固定大小的线程池:
        ExecutorService executor = Executors.newFixedThreadPool(100);
        Long rows = 0L;
        for (long i = 0; i < 1000; i++) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO students (name, gender, grade, score) VALUES (?, ?, ?, ?);");
            ps.setObject(1, "小明"); // 注意：索引从1开始
            ps.setObject(2, 1);
            ps.setObject(3, 1);
            ps.setObject(4, 88);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            rows++;
        }
        reportTimeElapsed();
        return rows;
    }

    public static final Long fetchRows(Connection conn) throws SQLException{
        start = Instant.now();
        Long rows = 0L;
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT id, grade, name, gender FROM students")) {
                while (rs.next()) {

                    // 通过列索引取值，注意：索引从1开始
                    long id = rs.getLong(1);
                    long grade = rs.getLong(2);
                    String name = rs.getString(3);
                    int gender = rs.getInt(4);

                    // 通过列名取值
                    id = rs.getLong("id");
                    grade = rs.getLong("grade");
                    name = rs.getString("name");
                    gender = rs.getInt("gender");
                    rows++;
                }
            }
        }
        reportTimeElapsed();
        return rows;
    }

    private static void reportTimeElapsed() {
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();
        System.out.println("用时" + timeElapsed + "毫秒");
    }
}
