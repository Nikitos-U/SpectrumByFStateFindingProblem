package ru.mipt.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class FstateRepository {
    private static Statement stmt;

    public static void executeUpdate(String query, Connection con) throws SQLException {
        stmt = con.createStatement();
        stmt.executeUpdate(query);
    }

}
