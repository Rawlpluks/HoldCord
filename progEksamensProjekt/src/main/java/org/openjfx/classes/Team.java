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
    private String teamName;
    private ArrayList<User> teamMembers = new ArrayList<>();

    public Team(int team_ID, String teamName) {
        this.team_ID = team_ID;
        this.teamName = teamName;
    }

    public int getTeam_ID() {
        return team_ID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<User> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
