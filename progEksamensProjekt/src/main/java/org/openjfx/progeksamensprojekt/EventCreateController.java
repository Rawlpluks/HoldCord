/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.classes.*;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class EventCreateController implements Initializable {

    @FXML
    private ListView listViewTeamsNotAdded;
    @FXML
    private ListView<String> listViewTeamsAdded;
    @FXML
    private TextField textFieldNameOfEvent;
    @FXML
    private TextArea textAreaDescreptionOfEvent;
    @FXML
    private DatePicker datePickerDate;
    @FXML
    private Text textErrorMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //if editing an event load the info and dispaly it
            Event edittingEvent = App.getEvent();
            if (!edittingEvent.equals(new Event())) {
                listViewTeamsAdded.getItems().clear();
                //listViewTeamsAdded.getItems().addAll(edittingEvent.getTeams());
            } else {
                //if no event is to be edited, load teams

            }
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
        App.setRoot("primary");
    }

    @FXML
    private void addSelectedTeamToEvent(ActionEvent event) {
    }

    @FXML
    private void removeSelectedTeamFromEvent(ActionEvent event) {
    }

    @FXML
    private void createOrUpdateEvent(ActionEvent event) {

        /*//create list of participants from list of teams
        ArrayList<String> teamsNames = new ArrayList<>();
        teamsNames.addAll(listViewTeamsAdded.getItems());
        
        Predicate<Team> findTeamsFromNames = team -> team.getName() !=;
        
        ArrayList<Team> teams = new ArrayList<>();
        
        teams.re*/
    }
}
