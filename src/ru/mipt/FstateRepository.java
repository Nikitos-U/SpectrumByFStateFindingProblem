package ru.mipt;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class FstateRepository {
    private static Statement stmt;

    static void executeUpdate(String query, Connection con) throws SQLException {
        stmt = con.createStatement();
        stmt.executeUpdate(query);
    }

}
