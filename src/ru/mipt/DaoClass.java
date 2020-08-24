package ru.mipt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoClass {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:h2:~/test";
    private static final String user = "sa";
    private static final String password = "";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;

    Connection getConnectiontoDb() {
        String query = "select name from particles";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return con;
    }

    public void executeUpdate(String query, Connection con) throws SQLException {
        stmt = con.createStatement();
        stmt.executeUpdate(query);
    }

    public void cleanUp() throws SQLException {
        stmt = con.createStatement();
        stmt.executeUpdate("delete from particles");
        stmt.executeUpdate("delete from decays");
        con.close();
    }
}
