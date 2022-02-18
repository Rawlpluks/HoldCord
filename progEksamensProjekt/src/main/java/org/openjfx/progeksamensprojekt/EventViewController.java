/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.openjfx.classes.*;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class EventViewController implements Initializable {

    @FXML
    private Label labelEventName;
    @FXML
    private ListView<String> listViewParticipants;
    @FXML
    private ListView<String> listViewAssignedTeams;
    @FXML
    private TextArea textAreaDescreption;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Event event =  App.getEvent();
            
            listViewAssignedTeams.getItems().clear();
            for (Team team : event.getTeams()) {
                listViewAssignedTeams.getItems().add(team.getName());
            }
            
            listViewParticipants.getItems().clear();
            for(Participant participant : event.getParticipants()){
                listViewParticipants.getItems().add(participant.getParticipant().getName());
            }
            
            labelEventName.setText(event.getTitle());
            textAreaDescreption.setText(event.getDescreption());
        } catch (Exception e) {
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
}
