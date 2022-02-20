/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.classes;

import java.util.*;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author chris
 */
public class Team {
    private int team_ID;
    private String name;
    private User createrOfTeam;
    private ArrayList<User> teamMembers = new ArrayList<>();

    public Team() {
    }
    
    public Team(int team_ID, String name, User createrOfTeam, ArrayList<User> teamMembers) {
        this.team_ID = team_ID;
        this.name = name;
        this.teamMembers = teamMembers;
        this.createrOfTeam = createrOfTeam;
    }

    public int getTeam_ID() {
        return team_ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String Name) {
        this.name = name;
    }

    public User getCreaterOfTeam() {
        return createrOfTeam;
    }

    public void setCreaterOfTeam(User createrOfTeam) {
        this.createrOfTeam = createrOfTeam;
    }
    
    public ArrayList<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<User> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
