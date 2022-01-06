/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.classes;

/**
 *
 * @author chris
 */
public class User {
    private int user_ID;
    private String username;
    private String password;
    private String name;
    private String email;

    public User(int user_ID, String username, String password, String name, String email) {
        this.user_ID = user_ID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
