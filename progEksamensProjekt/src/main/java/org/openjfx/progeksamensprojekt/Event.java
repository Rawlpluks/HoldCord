/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.progeksamensprojekt;

import java.util.*;
import org.openjfx.classes.Participant;
import org.openjfx.classes.Team;
import org.openjfx.classes.User;

/**
 *
 * @author chris
 */
public class Event {
    private int event_ID;
    private User host;
    private String date;
    private String title;
    private String descreption;
    private ArrayList<Participant> participants = new ArrayList<>();
    private ArrayList<Team> teams = new ArrayList<>();
    
    //create
    public Event(User host, String date, String title, String descreption, ArrayList<Team> teams) {
        this.host = host;
        this.date = date;
        this.title = title;
        this.descreption = descreption;
        this.teams = teams;
    }
    
    //load
    public Event(int event_ID, User host, String date, String title, String descreption, ArrayList<Participant> participants, ArrayList<Team> teams) {
        this.event_ID = event_ID;
        this.host = host;
        this.date = date;
        this.title = title;
        this.descreption = descreption;
        this.participants = participants;
        this.teams = teams;
    }
    
    //app
    public Event() {
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

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
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

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    
    //for table view
    public String getTeamNames() {
        String teamNames = "";
        
        for(int i = 0; i < teams.size(); i++) {
            if(i == 0) {
                teamNames += teams.get(i).getName();
            } else {
                teamNames += "\n" + teams.get(i).getName();
            }
        }
        return teamNames;
    }
}
