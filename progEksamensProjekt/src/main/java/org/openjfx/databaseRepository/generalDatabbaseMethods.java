/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.databaseRepository;

import org.openjfx.progeksamensprojekt.Event;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.openjfx.classes.*;

/**
 *
 * @author chris
 */
public class GeneralDatabbaseMethods {

    private final String connectionString = "jdbc:sqlite:Database.db";

    //--------------------------------
    //---------- load users ----------
    //--------------------------------
    private ArrayList<User> loadUsers(ResultSet rs, Connection conn) throws SQLException, Exception {
        ArrayList<User> loadedUsers = new ArrayList<>();

        //get user info
        try {
            while (rs.next()) {
                loadedUsers.add(new User(rs.getInt("user_ID"), rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getString("email"), null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load users (info): " + e.getMessage() + "\n");
        }

        //get users teams
        for (User user : loadedUsers) {
            try {
                Statement stat = conn.createStatement();

                rs = stat.executeQuery("SELECT team_ID FROM usersAndTeams WHERE user_ID = "
                        + "('" + user.getUser_ID() + "');");

                ArrayList<Integer> team_IDs = new ArrayList<>();

                while (rs.next()) {
                    team_IDs.add(rs.getInt("team_ID"));
                }

                user.setTeam_IDs(team_IDs);

            } catch (SQLException e) {
                System.out.println("\n Database error (load users (team ID's): " + e.getMessage() + "\n");
            }
        }

        return loadedUsers;
    }

    //-------------------------------
    //---------- load user ----------
    //-------------------------------
    private User loadUser(int _user_ID, Connection conn) throws SQLException, Exception {
        User user = new User();

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM users WHERE user_ID = ('" + _user_ID + "');");

            user = new User(rs.getInt("user_ID"), rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getString("email"), null);

        } catch (SQLException e) {
            System.out.println("\n Database error (load user (info): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT team_ID FROM usersAndTeams WHERE user_ID = "
                    + "('" + user.getUser_ID() + "');");

            ArrayList<Integer> team_IDs = new ArrayList<>();

            while (rs.next()) {
                team_IDs.add(rs.getInt("team_ID"));
            }

            user.setTeam_IDs(team_IDs);

        } catch (SQLException e) {
            System.out.println("\n Database error (load user (team ID's): " + e.getMessage() + "\n");
        }

        return user;
    }

    //---------------------------------
    //---------- Load events ----------
    //---------------------------------
    private ArrayList<Event> loadEvents(ResultSet rs, Connection conn) throws SQLException, Exception {
        ArrayList<Event> events = new ArrayList<>();

        //get event info
        try {
            while (rs.next()) {
                events.add(new Event(rs.getInt("event_ID"), loadUser(rs.getInt("host_ID"), conn), rs.getString("date"), rs.getString("title"), rs.getString("descreption"), null, null));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load events (get events info): " + e.getMessage() + "\n");
        }

        //get events teams
        for (Event event : events) {
            try {
                Statement stat = conn.createStatement();

                rs = stat.executeQuery("SELECT * FROM Teams WHERE team_ID IN"
                        + "(SELECT team_ID FROM teamsAndEvents WHERE event_ID = ('" + event.getEvent_ID() + "'));");

                event.setTeams(loadTeams(rs, conn));
            } catch (SQLException e) {
                System.out.println("\n Database error (load events (get teams): " + e.getMessage() + "\n");
            }
        }

        //get events participants
        for (Event event : events) {
            try {
                Statement stat = conn.createStatement();

                rs = stat.executeQuery("SELECT * FROM participants WHERE event_ID = ('" + event.getEvent_ID() + "');");

                event.setParticipants(loadParticipants(rs, conn));

            } catch (SQLException e) {
                System.out.println("\n Database error (load events (get participants): " + e.getMessage() + "\n");
            }
        }

        return events;
    }

    //---------------------------------------
    //---------- load participants ----------
    //---------------------------------------
    private ArrayList<Participant> loadParticipants(ResultSet rs, Connection conn) throws SQLException, Exception {
        ArrayList<Participant> participants = new ArrayList<>();

        try {
            while (rs.next()) {
                participants.add(new Participant(rs.getInt("participant_ID"), loadUser(rs.getInt("user_ID"), conn), ParticipantStatus.valueOf(rs.getString("participantStatus"))));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load particpants (load participant info): " + e.getMessage() + "\n");
        }

        return participants;
    }

    //--------------------------------
    //---------- load teams ----------
    //--------------------------------
    private ArrayList<Team> loadTeams(ResultSet rs, Connection conn) throws SQLException, Exception {
        ArrayList<Team> teams = new ArrayList<>();

        //load team info
        while (rs.next()) {
            teams.add(new Team(rs.getInt("team_ID"), rs.getString("name"), rs.getString("description"), loadUser(rs.getInt("createrOfTeam_ID"), conn), null));
        }

        //load team members
        for (Team team : teams) {
            try {
                Statement stat = conn.createStatement();

                rs = stat.executeQuery("SELECT * FROM users WHERE user_ID IN"
                        + "(SELECT user_ID FROM usersAndTeams WHERE team_ID = ('" + team.getTeam_ID() + "'));");

                team.setTeamMembers(loadUsers(rs, conn));
            } catch (SQLException e) {
                System.out.println("\n Database error (load teams (get members): " + e.getMessage() + "\n");
            }
        }

        return teams;
    }

    private ArrayList<NewsFeedMessage> loadNewsFeedMessages(ResultSet rs, Connection conn) throws SQLException, Exception {
        ArrayList<NewsFeedMessage> newsFeedMessages = new ArrayList<>();
        
        try {
            Statement stat = conn.createStatement();

            while (rs.next()) {
                //get sender
                User sender = new User();
                try {
                    sender = loadUser(rs.getInt("sender_ID"), conn);
                } catch (SQLException e) {
                    System.out.println("\n Database error (load news feed meassges (get sender): " + e.getMessage() + "\n");
                }

                //get teams
                
                /*ArrayList<Team> teams = new ArrayList<>();
                try {
                    ResultSet loadTeams = stat.executeQuery("SELECT * FROM teams WHERE team_ID IN"
                            + "(SELECT team_ID FROM newsFeedMessagesAndTeams WHERE"
                            + "newsFeedMessages_ID = ('" + test + "'));");
                    teams = loadTeams(loadTeams, conn);
                } catch (SQLException e) {
                    System.out.println("\n Database error (load news feed meassges (get teams): " + e.getMessage() + "\n");
                }*/

                //get comments
                ArrayList<NewsFeedMessageComment> comments = new ArrayList<>();
                try {
                    ResultSet loadComments = stat.executeQuery("SELECT * FROM newsFeedMessagesComments "
                            + "WHERE newsFeedMessages_ID = ('" + rs.getInt("newsFeedMessages_ID") + "');");

                    while (loadComments.next()) {
                        comments.add(new NewsFeedMessageComment(loadUser(loadComments.getInt("user_ID"), conn), loadComments.getString("date"), loadComments.getString("comment")));
                    }
                } catch (SQLException e) {
                    System.out.println("\n Database error (load news feed meassges (get comments): " + e.getMessage() + "\n");
                }

                //set info
                newsFeedMessages.add(new NewsFeedMessage(rs.getInt("newsFeedMessages_ID"), rs.getString("titel"), rs.getString("date"), null, sender, rs.getString("messages"), comments));
            }
        } catch (SQLException e) {
            System.out.println("\n Database error (load news feed meassges (run trough result set): " + e.getMessage() + "\n");
        }
        
        //load teams
        for(NewsFeedMessage nfm : newsFeedMessages) {
            ArrayList<Team> teams = new ArrayList<>();
            try {
                Statement stat = conn.createStatement();
                
                ResultSet loadTeams = stat.executeQuery("SELECT * FROM teams WHERE team_ID IN"
                            + "(SELECT team_ID FROM newsFeedMessagesAndTeams WHERE "
                            + "newsFeedMessages_ID = ('" + nfm.getNewsFeedMessage_ID() + "'));");
                    teams = loadTeams(loadTeams, conn);
            } catch (SQLException e) {
                System.out.println("test of load teams: " + e.getMessage());
            }
            nfm.setTeams(teams);
        }
        

        //reverse so the newst news is first
        Collections.reverse(newsFeedMessages);

        return newsFeedMessages;
    }

    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    //------------------------------ public ------------------------------
    //--------------------------------------------------------------------
    //--------------------------------------------------------------------
    //-----------------------------------------
    //---------- get all other users ----------
    //-----------------------------------------
    public ArrayList<User> getAllOtherUsers(int _user_ID) throws SQLException, Exception {
        ArrayList<User> users = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get alle other users (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM users WHERE NOT user_ID = ('" + _user_ID + "');");

            users = loadUsers(rs, conn);
        } catch (SQLException e) {
            System.out.println("\n Database error (get alle other users (get users): " + e.getMessage() + "\n");
        }
        
        conn.close();

        return users;
    }

    //----------------------------------
    //---------- Create event ----------
    //----------------------------------
    public void createEvent(Event _event) throws SQLException, Exception {

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (create event (connection): " + e.getMessage() + "\n");
        }

        //insert event info
        String sql = "INSERT INTO events(host_ID, date, title, descreption) "
                + "VALUES('" + _event.getHost().getUser_ID() + "', '" + _event.getDate() + "',"
                + "'" + _event.getTitle() + "', '" + _event.getDescreption() + "');";
        
        System.out.println("\n" + "sql: " + sql + "\n");
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create event (insert info): " + e.getMessage() + "\n");
        }

        //get event ID
        int event_ID = 0;

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(event_ID) FROM events");

            event_ID = rs.getInt("MAX(event_ID)");
            System.out.println(event_ID);
        } catch (SQLException e) {
            System.out.println("\n Database error (create event (get new event ID): " + e.getMessage() + "\n");
        }

        //insert for teams and get participants
        ArrayList<Participant> eventsParticipants = new ArrayList<>();
        for (Team team : _event.getTeams()) {
            sql = "INSERT INTO teamsAndEvents(team_ID, event_ID) "
                    + "VALUES('" + team.getTeam_ID() + "', '" + event_ID + "');";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (create event (insert participants): " + e.getMessage() + "\n");
            }

            for (User user : team.getTeamMembers()) {
                Participant teamsParticipant = new Participant(_event.getEvent_ID(), user, ParticipantStatus.hasentAnswered);

                //if already counted from another team dont add else do
                if (!eventsParticipants.contains(teamsParticipant)) {
                    eventsParticipants.add(teamsParticipant);
                }
            }
        }

        //add the participants
        for (Participant participant : eventsParticipants) {
            sql = "INSERT INTO participants(event_ID, user_ID, participantStatus) "
                    + "VALUES('" + event_ID + "', '" + participant.getParticipant().getUser_ID() + "', "
                    + "'" + participant.getStatus().toString() + "');";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (create event (insert participants): " + e.getMessage() + "\n");
            }
        }

        conn.close();
    }

    //-------------------------------------------------
    //---------- Get users particpents event ----------
    //-------------------------------------------------
    public ArrayList<Event> getUserParticipantEvents(int _user_ID) throws SQLException, Exception {
        ArrayList<Event> events = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get user participant event (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM events WHERE event_ID IN"
                    + "(SELECT event_ID FROM participants WHERE user_ID = ('" + _user_ID + "'));");

            events = loadEvents(rs, conn);

        } catch (SQLException e) {
            System.out.println("\n Database error (get user participant event (get events info): " + e.getMessage() + "\n");
        }
        conn.close();

        return events;
    }

    //-------------------------------------------
    //---------- get users host events ----------
    //-------------------------------------------
    public ArrayList<Event> getUserHostEvents(int _user_ID) throws SQLException, Exception {
        ArrayList<Event> events = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get user host event (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM events WHERE host_ID = ('" + _user_ID + "');");

            events = loadEvents(rs, conn);

        } catch (SQLException e) {
            System.out.println("\n Database error (get user host event (get events info): " + e.getMessage() + "\n");
        }
        conn.close();

        return events;
    }

    //--------------------------------------
    //---------- Get teams events ----------
    //--------------------------------------
    public ArrayList<Event> getTeamsEvents(int _team_ID) throws SQLException, Exception {
        ArrayList<Event> teamsEvents = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get teams event (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM events WHERE event_ID IN"
                    + "(SELECT event_ID FROM teamsAndEvents WHERE team_ID = ('" + _team_ID + "'));");

            teamsEvents = loadEvents(rs, conn);

        } catch (SQLException e) {
            System.out.println("\n Database error (get teams event (get event): " + e.getMessage() + "\n");
        }
        
        conn.close();

        return teamsEvents;
    }
    //---------------------------------------------
    //---------- Edit participant status ----------
    //---------------------------------------------
    public void editParticipantStatus(Participant _participant) throws SQLException, Exception{
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (edit participant status (connection): " + e.getMessage() + "\n");
        }
        
        String sql = "UPDATE participants SET participantStatus = '" + _participant.getStatus().toString() + "'"
                + "WHERE participant_ID = ('" + _participant.getParticipant_ID() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (edit participant status (update status): " + e.getMessage() + "\n");
        }
        
        conn.close();
    }

    //--------------------------------
    //---------- Edit event ----------
    //--------------------------------
    public void editEvent(Event _event) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (edit event (connection): " + e.getMessage() + "\n");
        }

        //update info
        String sql = "UPDATE events SET host_ID = '" + _event.getHost().getUser_ID() + "', "
                + "date = '" + _event.getDate() + "', title = '" + _event.getTitle() + "' "
                + "WHERE event_ID = ('" + _event.getEvent_ID() + "')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (edit event (insert info): " + e.getMessage() + "\n");
        }

        //needs to first get the already existing particpants and check if they are stil invited
        //if not delete the from the database
        //the needs to check if the new ones to be adde already is adde, and if they are they shouldnt be added
        //delete existing participants
        //------------------------------------------
        ArrayList<Participant> currrentParticipants = _event.getParticipants();
        ArrayList<Participant> existingParticipants = new ArrayList<>();

        //get current participants
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM participants WHERE event_ID = ('" + _event.getEvent_ID() + "');");

            existingParticipants = loadParticipants(rs, conn);

        } catch (SQLException e) {
            System.out.println("\n Database error (edit event (get existing participants): " + e.getMessage() + "\n");
        }

        //see whic participants isent invited anymore
        ArrayList<Participant> participantsToRemove = (ArrayList<Participant>) existingParticipants.clone();
        participantsToRemove.removeAll(currrentParticipants);

        //delte participants who isent invited anymore
        for (Participant participant : participantsToRemove) {
            sql = "DELETE FROM participants WHERE participant_ID = ('" + participant.getParticipant_ID() + "');";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (edit event (delete participants who is not invited anymore): " + e.getMessage() + "\n");
            }
        }

        //see whic participants needs to be added
        ArrayList<Participant> participantsToAdd = (ArrayList<Participant>) currrentParticipants.clone();
        participantsToAdd.removeAll(existingParticipants);

        //add new participants
        for (Participant participant : participantsToAdd) {
            sql = "INSERT INTO participants(event_ID, user_ID, participantStatus) "
                    + "VALUES('" + _event.getEvent_ID() + "', '" + participant.getParticipant().getUser_ID() + "', "
                    + "'" + participant.getStatus().toString() + "');";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (edit event (insert new participants): " + e.getMessage() + "\n");
            }
        }
        //------------------------------------------
        /*sql = "DELETE FROM participants WHERE event_ID = ('" + _event.getEvent_ID() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (edit event (delete existing participants): " + e.getMessage() + "\n");
        }

        //insert new participants
        for (Participant participant : _event.getParticipants()) {
            sql = "INSERT INTO participants(event_ID, user_ID, participantStatus) "
                    + "VALUES('" + _event.getEvent_ID() + "', '" + participant.getParticipant().getUser_ID() + "', "
                    + "'" + participant.getStatus().toString() + "');";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (edit event (insert new participants): " + e.getMessage() + "\n");
            }
        }*/

        conn.close();
    }

    //----------------------------------
    //---------- Delete event ----------
    //----------------------------------
    public void deleteEvent(int _event_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (edit event (connection): " + e.getMessage() + "\n");
        }

        //delete  event info
        String sql = "DELETE FROM events WHERE event_ID = ('" + _event_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete event (delete event info): " + e.getMessage() + "\n");
        }

        //delete  participants
        sql = "DELETE FROM participants WHERE event_ID = ('" + _event_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete event (delete participants): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //---------------------------------
    //---------- Create team ----------
    //---------------------------------
    public void createTeam(Team _team) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (create team (connection): " + e.getMessage() + "\n");
        }

        //insert team info
        String sql = "INSERT INTO teams(createrOfTeam_ID, name, description) "
                + "VALUES('" + _team.getCreaterOfTeam().getUser_ID() + "', '" + _team.getName() + "', "
                + "'" + _team.getDescription() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create team (insert info): " + e.getMessage() + "\n");
        }

        //get new teams id
        int team_ID = 0;
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT MAX(team_ID) FROM teams");

            team_ID = rs.getInt("MAX(team_ID)");

        } catch (SQLException e) {
            System.out.println("\n Database error (create team (get team ID): " + e.getMessage() + "\n");
        }

        //insert members
        for (User user : _team.getTeamMembers()) {
            sql = "INSERT INTO usersAndTeams(user_ID, team_ID) "
                    + "VALUES('" + user.getUser_ID() + "', '" + team_ID + "')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (create team (insert members): " + e.getMessage() + "\n");
            }
        }

        conn.close();
    }

    //-----------------------------------------------
    //---------- Get teams user member  of ----------
    //-----------------------------------------------
    public ArrayList<Team> getTeamsUserMemberOf(int _user_ID) throws SQLException, Exception {
        ArrayList<Team> teams = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get team user member of (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM teams WHERE team_ID IN"
                    + "(SELECT team_ID FROM usersAndTeams WHERE user_ID = ('" + _user_ID + "'));");

            teams = loadTeams(rs, conn);

        } catch (SQLException e) {
            System.out.println("\n Database error (get team user member of (get teams): " + e.getMessage() + "\n");
        }
        
        conn.close();
        
        return teams;
    }

    //-----------------------------------------------
    //---------- Get teams user creater of ----------
    //-----------------------------------------------
    public ArrayList<Team> getTeamsUserCreaterOf(int _user_ID) throws SQLException, Exception {
        ArrayList<Team> teams = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get team user creater of (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM teams WHERE createrOfTeam_ID = ('" + _user_ID + "');");

            teams = loadTeams(rs, conn);

        } catch (SQLException e) {
            System.out.println("\n Database error (get team user creater of (get teams): " + e.getMessage() + "\n");
        }
        
        conn.close();
        
        return teams;
    }

    //-------------------------------
    //---------- Edit team ----------
    //-------------------------------
    public void editTeam(Team _team) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (edit team (connection): " + e.getMessage() + "\n");
        }

        //update info
        String sql = "UPDATE teams SET createrOfTeam_ID = '" + _team.getCreaterOfTeam().getUser_ID() + "',"
                + " name = '" + _team.getName() + "' WHERE team_ID = ('" + _team.getTeam_ID() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (edit team (update info): " + e.getMessage() + "\n");
        }

        //delete existing team members
        sql = "DELETE FROM usersAndTeams WHERE team_ID = ('" + _team.getTeam_ID() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (edit team (Delete existing team members): " + e.getMessage() + "\n");
        }

        //insert new members
        for (User user : _team.getTeamMembers()) {
            sql = "INSERT INTO usersAndTeams(user_ID, team_ID) "
                    + "VALUES('" + user.getUser_ID() + "', '" + _team.getTeam_ID() + "')";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (edit team (insert new members): " + e.getMessage() + "\n");
            }
        }

        conn.close();
    }
    
    //---------------------------------
    //---------- Delete team ----------
    //---------------------------------
    public void deleteTeam(Team _team) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (edit team (connection): " + e.getMessage() + "\n");
        }

        //delete info
        String sql = "DELETE FROM teams WHERE team_ID = ('" + _team.getTeam_ID() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete team (delete info): " + e.getMessage() + "\n");
        }

        //delete members
        sql = "DELTE FROM usersAndTeam WHERE team_ID = ('" + _team.getTeam_ID() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete team (delete members): " + e.getMessage() + "\n");
        }

        conn.close();
    }

    //----------------------------------------------
    //---------- Create news feed message ----------
    //----------------------------------------------
    public void createNewsFeedMssage(NewsFeedMessage _NewsFeedMessage) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (create news feed meassge (connection): " + e.getMessage() + "\n");
        }

        //insert info
        String sql = "INSERT INTO newsFeedMessages(sender_ID, titel, date, messages) "
                + "VALUES('" + _NewsFeedMessage.getSender().getUser_ID() + "','" + _NewsFeedMessage.getTitel() + "',"
                + "'" + _NewsFeedMessage.getDate() + "', '" + _NewsFeedMessage.getMessages() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (create news feed meassge (insert info): " + e.getMessage() + "\n");
        }
        
        //get newsFeedMessages_ID
        int newsFeedMessages_ID = 0;
        try {
            Statement stat = conn.createStatement();
            
            ResultSet rs = stat.executeQuery("SELECT MAX(newsFeedMessages_ID) FROM newsFeedMessages;");
            
            newsFeedMessages_ID = rs.getInt("MAX(newsFeedMessages_ID)");
            
        } catch (SQLException e) {
            System.out.println("\n Database error (create news feed meassge (get new newsFeedMessages_ID): " + e.getMessage() + "\n");
        }
        
        System.out.println(newsFeedMessages_ID);

        //assign to teams
        for (Team team : _NewsFeedMessage.getTeams()) {
            sql = "INSERT INTO newsFeedMessagesAndTeams(newsFeedMessages_ID, team_ID)"
                    + "VALUES('" + newsFeedMessages_ID + "', '" + team.getTeam_ID() + "');";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("\n Database error (create news feed meassge (assign to teams): " + e.getMessage() + "\n");
            }
        }

        conn.close();
    }

    //------------------------------------
    //---------- Get teams news ----------
    //------------------------------------
    public ArrayList<NewsFeedMessage> getTeamsNewsFeedMessages(int _team_ID) throws SQLException, Exception {
        ArrayList<NewsFeedMessage> teamNewsFeedMessages = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get teams news feed meassges (connection): " + e.getMessage() + "\n");
        }

        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM newsFeedMessages WHERE newsFeedMessages_ID IN "
                    + "(SELECT newsFeedMessages_ID FROM newsFeedMessagesAndTeams WHERE team_ID = ('" + _team_ID + "'));");

            teamNewsFeedMessages = loadNewsFeedMessages(rs, conn);

        } catch (SQLException e) {
            System.out.println("\n Database error (get teams news feed meassges (get news feed messages): " + e.getMessage() + "\n");
        }
        
        conn.close();
        
        return teamNewsFeedMessages;
    }

    //--------------------------------------------------
    //---------- Get users news feed messages ----------
    //--------------------------------------------------
    public ArrayList<NewsFeedMessage> getUsersNewsFeedMessages(int _user_ID) throws SQLException, Exception {
        ArrayList<NewsFeedMessage> newsFeedMessages = new ArrayList<>();

        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (get users news feed meassges (connection): " + e.getMessage() + "\n");
        }

        //get messages
        try {
            Statement stat = conn.createStatement();

            ResultSet rs = stat.executeQuery("SELECT * FROM newsFeedMessages WHERE newsFeedMessages_ID IN "
                    + "(SELECT newsFeedMessages_ID FROM newsFeedMessagesAndTeams WHERE team_ID IN"
                    + "(SELECT team_ID FROM teams WHERE createrOfTeam_ID = ('" + _user_ID + "')) OR "
                    + "(SELECT team_ID FROM usersAndTeams WHERE user_ID = ('" + _user_ID + "')));");

            newsFeedMessages = loadNewsFeedMessages(rs, conn);
            /*while (rs.next()) {
                //get sender
                User sender = loadUser(rs.getInt("sender_ID"), conn);

                //get teams
                ArrayList<Team> teams = new ArrayList<>();
                try {
                    ResultSet loadTeams = stat.executeQuery("SELECT * FROM teams WHERE team_ID IN"
                            + "(SELECT team_ID FROM newsFeedMessagesAndTeams WHERE"
                            + "newsFeedMessages_ID = ('" + rs.getInt("newsFeedMessages_ID") + "')) );");

                    teams = loadTeams(loadTeams, conn);
                } catch (SQLException e) {
                    System.out.println("\n Database error (get users news feed meassges (get teams): " + e.getMessage() + "\n");
                }

                //get comments
                ArrayList<NewsFeedMessageComment> comments = new ArrayList<>();
                try {
                    ResultSet loadComments = stat.executeQuery("SELECT FROM * newsFeedMessagesComments"
                            + "WHERE newsFeedMessages_ID = ('" + rs.getInt("newsFeedMessages_ID") + "');");

                    while (rs.next()) {
                        comments.add(new NewsFeedMessageComment(loadUser(loadComments.getInt("user_ID"), conn), Date.valueOf(loadComments.getString("date")), loadComments.getString("comment")));
                    }
                } catch (SQLException e) {
                    System.out.println("\n Database error (get users news feed meassges (get comments): " + e.getMessage() + "\n");
                }

                //set info
                newsFeedMessages.add(new NewsFeedMessage(rs.getInt("newsFeedMessages_ID"), rs.getString("titel"), rs.getString("date"), teams, sender, rs.getString("messages"), comments));
            }*/

        } catch (SQLException e) {
            System.out.println("\n Database error (get users news feed meassges (get messages): " + e.getMessage() + "\n");
        }

        conn.close();

        //reverse so the newst news is first
        //Collections.reverse(newsFeedMessages);
        return newsFeedMessages;
    }

    //----------------------------------------------
    //---------- Delete news feed message ----------
    //----------------------------------------------
    public void deleteNewsFeedMessage(int _newsFeedMessage_ID) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (delete news feed meassges (connection): " + e.getMessage() + "\n");
        }

        //delete info
        String sql = "DELETE FROM newsFeedMessages WHERE newsFeedMessages_ID = ('" + _newsFeedMessage_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete news feed meassge (delete info): " + e.getMessage() + "\n");
        }

        //delte relations to team
        sql = "DELTE FROM newsFeedMessagesAndTeams WHERE newsFeedMessages_ID = ('" + _newsFeedMessage_ID + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete news feed meassge (delete relation to teams): " + e.getMessage() + "\n");
        }

        //delete comments
        sql = "DELETE FROM newsFeedMessagesComments WHERE newsFeedMessages_ID = ('" + _newsFeedMessage_ID + "') ;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (delete news feed meassge (delete comments): " + e.getMessage() + "\n");
        }

        conn.close();
    }

    //-----------------------------------------------
    //---------- comment news feed message ----------
    //-----------------------------------------------
    public void commentNewsFeedMessage(NewsFeedMessageComment _comment) throws SQLException, Exception {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");

        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            System.out.println("\n Database error (delete news feed meassges (connection): " + e.getMessage() + "\n");
        }

        String sql = "INSERT INTO newsFeedMessagesComments(sender_ID, date) "
                + "VALUES('" + _comment.getSender().getUser_ID() + "', '" + _comment.getDate().toString() + "',"
                + "'" + _comment.getComment() + "');";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("\n Database error (comment news feed meassge (insert info): " + e.getMessage() + "\n");
        }
        conn.close();
    }

    //-------------------------------------------------------
    //-------------------- to come, maby --------------------
    //-------------------------------------------------------
}
