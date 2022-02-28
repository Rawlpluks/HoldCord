/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
            
            
            labelTeamName.setText(loadeTeam.getName());
            
            listViewTeamMembers.getItems().clear();
            
            //--------------
            //sort alpabetic
            //--------------
            
            for(User user : loadeTeam.getTeamMembers()) {
                listViewTeamMembers.getItems().add(user.getName());
            }
            
            listViewTeamsEvents.getItems().clear();
            
            teamsEvents = gdm.getTeamsEvents(loadeTeam.getTeam_ID());
            //how we display here
            
            listViewTeamsNewsfeed.getItems().clear();
            
            teamsNewsFeedMessages = gdm.getTeamsNewsFeedMessages(loadeTeam.getTeam_ID());
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    
    @FXML
    private void exit () {
        System.exit(0);
    }
    @FXML
    private void teams() throws IOException {
        App.setRoot("teams");
    }
    @FXML
    private void events () throws IOException {
        App.setRoot("events");
    }
    @FXML
    private void settings () throws IOException {
        App.setRoot("settings");
    }
    @FXML
    private void logout () throws IOException {
        App.setRoot("login");
    }
    @FXML
    private void main () throws IOException {
        App.setRoot("mainScreen");
    }
    @FXML
    private void eventCreate () throws IOException {
        App.setRoot("eventCreate");
    }
    @FXML
    private void newsCreate () throws IOException {
        App.setRoot("newsCreate");
    }
}
