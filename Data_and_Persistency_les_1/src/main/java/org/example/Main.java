package org.example;

import java.sql.*;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        try {
            Connection conn = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        select();
        insert();
        update();
        delete();
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=lupos";
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws
            SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private static void select() throws SQLException {
        getConnection();
        String query = "SELECT * FROM reiziger";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        while (set != null && set.next()) {
            if(set.getString("tussenvoegsel") == null || set.getString("tussenvoegsel").equals(""))
                System.out.println("#" + set.getInt("reiziger_id") + ": " + set.getString("voorletters") + " " + set.getString("achternaam") + " (" + set.getDate("geboortedatum") + ")");
            else{
                System.out.println("#" + set.getInt("reiziger_id") + ": " + set.getString("voorletters") + " " + set.getString("tussenvoegsel") + " " + set.getString("achternaam") + " (" + set.getDate("geboortedatum") + ")");
            }
        }
        closeConnection();
    }

    private static void insert() throws SQLException {
        getConnection();
        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, 8);
        statement.setString(2, "Igo");
        statement.setString(3, "");
        statement.setString(4, "Ulenbelt");
        statement.setDate(5, Date.valueOf("2004-06-10"));
        statement.executeUpdate();
        closeConnection();
    }

    private static void update() throws SQLException {
        getConnection();
        String query = "UPDATE reiziger SET geboortedatum = ? WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, Date.valueOf("2004-06-11"));
        statement.setInt(2, 8);
        statement.executeUpdate();
        closeConnection();
    }

    private static void delete() throws SQLException {
        getConnection();
        String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, 8);
        statement.executeUpdate();
        closeConnection();
    }
}
