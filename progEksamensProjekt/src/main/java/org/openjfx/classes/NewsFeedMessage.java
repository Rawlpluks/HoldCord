/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.classes;

import java.util.*;

/**
 *
 * @author chris
 */
public class NewsFeedMessage {
    private int newsFeedMessage_ID;
    private Date date;
    private Team team;
    private User sender;
    private String messages;

    public NewsFeedMessage(int newsFeedMessage_ID, Date date, Team team, User sender, String messages) {
        this.newsFeedMessage_ID = newsFeedMessage_ID;
        this.date = date;
        this.team = team;
        this.sender = sender;
        this.messages = messages;
    }

    public int getNewsFeedMessage_ID() {
        return newsFeedMessage_ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    } 
}
