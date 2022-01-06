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
    private int host_ID;
    private Date date;
    private String name;
    private String descreption;
    private ArrayList<Participant> participants = new ArrayList<>();

    public Event(int event_ID, int host_ID, Date date, String name, String descreption) {
        this.event_ID = event_ID;
        this.host_ID = host_ID;
        this.date = date;
        this.name = name;
        this.descreption = descreption;
    }

    public int getEvent_ID() {
        return event_ID;
    }

    public int getHost_ID() {
        return host_ID;
    }

    public void setHost_ID(int host_ID) {
        this.host_ID = host_ID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
