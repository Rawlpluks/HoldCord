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
public class Event {
    private int event_ID;
    private User host;
    private Date date;
    private String title;
    private String descreption;
    private ArrayList<Participant> participants = new ArrayList<>();

    public Event(User host, Date date, String title, String descreption, ArrayList<Participant> participants) {
        this.host = host;
        this.date = date;
        this.title = title;
        this.descreption = descreption;
        this.participants = participants;
    }

    public Event(int event_ID, User host, Date date, String title, String descreption, ArrayList<Participant> participants) {
        this.event_ID = event_ID;
        this.host = host;
        this.date = date;
        this.title = title;
        this.descreption = descreption;
        this.participants = participants;
    }

    public int getEvent_ID() {
        return event_ID;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }
}
