/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.openjfx.classes.*;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TeamsInfoAdminController implements Initializable {

    @FXML
    private ListView listViewTeamMembers;
    @FXML
    private ListView listViewTeamsNewsfeed;
    @FXML
    private ListView listViewTeamsEvents;
    @FXML
    private Label labelTeamName;

    private Team loadeTeam = App.getTeam();
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    private ArrayList<Event> teamsEvents = new ArrayList<>();
    private ArrayList<NewsFeedMessage> teamsNewsFeedMessages = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //---------- users alphabetic ascending ----------
            Comparator<User> sortUserNameAlphabeticAscending = new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    return u1.getName().compareTo(u2.getName());
                }
            };
            //---------- events city alphabetic ascending ----------
            Comparator<Event> sortEventTitelAlphabeticAscending = new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    return e1.getTitle().compareTo(e2.getTitle());
                }
            };
            //---------- news feed messages city alphabetic ascending ----------
            Comparator<NewsFeedMessage> sortNewsFeedMessageTitelAlphabeticAscending = new Comparator<NewsFeedMessage>() {
                @Override
                public int compare(NewsFeedMessage nfm1, NewsFeedMessage nfm2) {
                    return nfm1.getTitel().compareTo(nfm2.getTitel());
                }
            };

            labelTeamName.setText(loadeTeam.getName());

            listViewTeamMembers.getItems().clear();
            
            Collections.sort(loadeTeam.getTeamMembers(), sortUserNameAlphabeticAscending);
            
            for (User user : loadeTeam.getTeamMembers()) {
                listViewTeamMembers.getItems().add(user.getName());
            }

            listViewTeamsEvents.getItems().clear();

            teamsEvents = gdm.getTeamsEvents(loadeTeam.getTeam_ID());
            Collections.sort(teamsEvents, sortEventTitelAlphabeticAscending);
            
            for (Event event : teamsEvents) {
                listViewTeamsEvents.getItems().add(event.getTitle());
            }

            listViewTeamsNewsfeed.getItems().clear();

            teamsNewsFeedMessages = gdm.getTeamsNewsFeedMessages(loadeTeam.getTeam_ID());
            Collections.sort(teamsNewsFeedMessages, sortNewsFeedMessageTitelAlphabeticAscending);
            
            for (NewsFeedMessage newsFeedMessage : teamsNewsFeedMessages) {
                listViewTeamsNewsfeed.getItems().add(newsFeedMessage.getTitel());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void teams() throws IOException {
        App.setRoot("teams");
    }

    @FXML
    private void events() throws IOException {
        App.setRoot("events");
    }

    @FXML
    private void settings() throws IOException {
        App.setRoot("settings");
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void main() throws IOException {
        App.setRoot("mainScreen");
    }

    @FXML
    private void eventCreate() throws IOException {
        App.setRoot("eventCreate");
    }

    @FXML
    private void newsCreate() throws IOException {
        App.setRoot("newsCreate");
    }
}
