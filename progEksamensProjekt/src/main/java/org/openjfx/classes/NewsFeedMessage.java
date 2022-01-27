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
    private ArrayList<Team> teams = new ArrayList<>();
    private User sender;
    private String messages;
    private ArrayList<NewsFeedMessageComment> comments = new ArrayList<>();

    public NewsFeedMessage(int newsFeedMessage_ID, Date date, ArrayList<Team> teams, User sender, String messages, ArrayList<NewsFeedMessageComment> comments) {
        this.newsFeedMessage_ID = newsFeedMessage_ID;
        this.date = date;
        this.teams = teams;
        this.sender = sender;
        this.messages = messages;
        this.comments = comments;
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
    
    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
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

    public ArrayList<NewsFeedMessageComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<NewsFeedMessageComment> comments) {
        this.comments = comments;
    }
}
