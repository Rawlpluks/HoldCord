/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.databaseRepository;

import java.sql.*;

/**
 *
 * @author chris
 */
public class userDatabaseMethods {
    private final String connectionString = "jdbc:sqlite:Database.db";
    
    //---------------------------------------------
    //---------- check for matching user ----------
    //---------------------------------------------
    public boolean cehckForMatchingUser(String _username) throws SQLException, Exception {
        String databaseUsername = "";
        _username = _username.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        
        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching user): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select Username from Users WHERE Username = ('" + _username + "');");
            
            rs.next();
            
            databaseUsername = rs.getString("Username");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching user): " + e.getMessage() + "\n");
        }

        if (_username.equalsIgnoreCase(databaseUsername)) {
            return true;
        }

        return false;
    }

    //--------------------------------------------------
    //---------- check for matching password -----------
    //--------------------------------------------------
    public boolean checkForMatchingPassword(String _username, String _password) throws SQLException, Exception {
        String databasePassword = "";
        _username = _username.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching password): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select Password from users WHERE Username = ('" + _username + "');");

            rs.next();

            databasePassword = rs.getString("Password");

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (check for matching password): " + e.getMessage() + "\n");
        }

        if (_password.equals(databasePassword)) {
            return true;
        }

        return false;
    }

    //---------------------------------
    //---------- create user ----------
    //---------------------------------
    public void createUser(User _newUser) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        String sql;
        
        _newUser.setUsername(_newUser.getUsername().toLowerCase());
        
        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skriver fejlhåndtering her
            System.out.println("\n Database error (create user): " + e.getMessage() + "\n");
        }
        
        int school_ID = 0;
        
        try {
            Statement stat = conn.createStatement();
            ResultSet rs =  stat.executeQuery("SELECT school_ID FROM Schools WHERE school_key =('" + _newUser.getSchoolKey() + "')");
            
            rs.next();
            
            school_ID = rs.getInt("school_ID");
            
            rs.close();
         } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (create user): " + e.getMessage() + "\n");
        }

        sql = "INSERT INTO users(school_ID, UserName, Password, School_key) VALUES('" + school_ID + "','" + _newUser.getUsername() + "','" + _newUser.getPassword() + "', '" + _newUser.getSchoolKey() + "');";
        
        
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create user): " + e.getMessage() + "\n");
        }
    }

    //----------------------------------------
    //---------- get logged in user ----------
    //----------------------------------------
    public User getoggedInUser(String _username) throws SQLException, Exception {
        User loggedInUser = new User();
        _username = _username.toLowerCase();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        //Skab forbindelse til databasen...
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get logged ind user): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("select * from users WHERE Username = ('" + _username + "');");

            rs.next();

            loggedInUser = new User(rs.getInt("User_ID"), rs.getInt("school_ID"), rs.getString("Username"), rs.getString("Password"), rs.getString("School_key"));

            rs.close();
        } catch (SQLException e) {
            //Skrive fejlhåndtering her
            System.out.println("\n Database error (get logged ind user): " + e.getMessage() + "\n");
        }

        return loggedInUser;
    }
}
