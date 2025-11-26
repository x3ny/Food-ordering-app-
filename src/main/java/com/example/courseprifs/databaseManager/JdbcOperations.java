package com.example.courseprifs.databaseManager;

import java.sql.*;

public class JdbcOperations {
    public static Connection connectToDb()  throws ClassNotFoundException{
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        String DB_URL = "jdbc:mysql://localhost/theory";
        String USER = "root";
        String PASS = "";
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return conn;
    }

    public void printAuthors() throws ClassNotFoundException, SQLException {
        Connection connection = connectToDb();
        Statement stmt = connection.createStatement();
        String query = "Select * from user";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            String name = rs.getString("name");
            String surname = rs.getString(2);
            int year = rs.getInt(3);
            String id = rs.getString("authorId");
            System.out.println(name + " " + surname + " " + year + " " + id);
        }
    }
}
