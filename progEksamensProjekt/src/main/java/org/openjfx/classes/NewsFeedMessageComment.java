/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.classes;

import java.util.Date;

/**
 *
 * @author chris
 */
public class NewsFeedMessageComment {
    int newsFeedMessages_ID;
    User sender;
    String date;
    String comment;
    
    public NewsFeedMessageComment(int newsFeedMessages_ID, User sender, String date, String comment) {
        this.newsFeedMessages_ID = newsFeedMessages_ID;
        this.sender = sender;
        this.date = date;
        this.comment = comment;
    }

    public int getNewsFeedMessages_ID() {
        return newsFeedMessages_ID;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
