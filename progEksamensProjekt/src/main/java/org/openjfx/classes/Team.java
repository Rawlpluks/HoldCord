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
public class Team {

    private int team_ID;
    private String name;
    private String description;
    private User createrOfTeam;
    private ArrayList<User> teamMembers = new ArrayList<>();

    //create
    public Team(String name, String description, User createrOfTeam, ArrayList<User> teamMembers) {
        this.name = name;
        this.description = description;
        this.createrOfTeam = createrOfTeam;
        this.teamMembers = teamMembers;
    }

    //load
    public Team(int team_ID, String name, String descreption, User createrOfTeam, ArrayList<User> teamMembers) {
        this.team_ID = team_ID;
        this.name = name;
        this.description = descreption;
        this.createrOfTeam = createrOfTeam;
        this.teamMembers = teamMembers;
    }

    public Team() {
    }

    public int getTeam_ID() {
        return team_ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
