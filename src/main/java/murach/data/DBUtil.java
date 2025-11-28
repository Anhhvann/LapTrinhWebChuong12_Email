package murach.data;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = System.getenv("DB_URL") != null 
        ? System.getenv("DB_URL")
        : "jdbc:mysql://localhost:3306/EmailList?useSSL=true&requireSSL=true&serverTimezone=UTC";
    
    private static final String USER = System.getenv("DB_USER") != null 
        ? System.getenv("DB_USER")
        : "root";
    
    private static final String PASSWORD = System.getenv("DB_PASS") != null 
        ? System.getenv("DB_PASS")
        : "password";
    private static ComboPooledDataSource cpds;
    
    static {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
            cpds.setJdbcUrl(URL);
            cpds.setUser(USER);
            cpds.setPassword(PASSWORD);
            cpds.setMinPoolSize(5);
            cpds.setMaxPoolSize(20);
            System.out.println("✓ Database connection pool initialized successfully!");
        } catch (Exception e) {
            System.err.println("✗ Error initializing connection pool: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = cpds.getConnection();
            System.out.println("✓ Connection obtained from pool");
            return conn;
        } catch (SQLException e) {
            System.err.println("✗ Error getting connection: " + e.getMessage());
            throw e;
        }
    }
}