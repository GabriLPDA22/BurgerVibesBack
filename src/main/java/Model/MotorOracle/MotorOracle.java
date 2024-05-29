package Model.MotorOracle;

import java.sql.*;

public class MotorOracle {
    private static final String URL = "jdbc:oracle:thin:@burgervibesbbdd.ceotvomboedr.us-east-1.rds.amazonaws.com:1521:orcl";
    private static final String USER = "admin";
    private static final String PASSWORD = "123456789";
    private Connection conn;

    public void connect() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle JDBC Driver not found", e);
        }
    }

    public void disconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        if (conn == null || conn.isClosed()) {
            connect();
        }
        return conn.prepareStatement(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        if (conn == null || conn.isClosed()) {
            connect();
        }
        Statement st = conn.createStatement();
        return st.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws SQLException {
        if (conn == null || conn.isClosed()) {
            connect();
        }
        Statement st = conn.createStatement();
        return st.executeUpdate(sql);
    }


}
