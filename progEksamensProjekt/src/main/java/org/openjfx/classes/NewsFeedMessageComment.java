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
    User sender;
    Date date;
    String comment;

    public NewsFeedMessageComment(User sender, Date date, String comment) {
        this.sender = sender;
        this.date = date;
        this.comment = comment;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
