/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.openjfx.classes.*;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

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
    @FXML
    private ChoiceBox<ParticipantStatus> participentStatus;
    
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    @FXML
    private Button buttonUpdateStatus;

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
            
            participentStatus.getItems().clear();
            participentStatus.getItems().addAll(ParticipantStatus.values());
            
            if(event.getHost().getUser_ID() == App.getLoggedInUser().getUser_ID()) {
                participentStatus.setVisible(false);
                buttonUpdateStatus.setVisible(false);
            } else {
                //find participant
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
        App.setEvent(new Event());
    }

    @FXML
    private void events() throws IOException {
        App.setRoot("events");
        App.setEvent(new Event());
    }

    @FXML
    private void settings() throws IOException {
        App.setRoot("settings");
        App.setEvent(new Event());
    }

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
        App.setEvent(new Event());
        App.setLoggedInUser(new User());
    }

    @FXML
    private void main() throws IOException {
        App.setRoot("mainScreen");
        App.setEvent(new Event());
    }

    @FXML
    private void updateStatus(ActionEvent event) {
        ParticipantStatus status = participentStatus.getSelectionModel().getSelectedItem();
        
        //gdm.
    }
}
