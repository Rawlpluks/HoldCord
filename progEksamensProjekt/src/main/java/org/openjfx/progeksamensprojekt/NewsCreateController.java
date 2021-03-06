/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.classes.*;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class NewsCreateController implements Initializable {

    @FXML
    private Text textErroMessage;
    @FXML
    private ListView listViewNotAddedTeams;
    @FXML
    private ListView listViewAddedTeams;
    @FXML
    private TextField textFieldNewsfeedMessagesTitle;
    @FXML
    private TextArea textAreaNewsfeedMessagesDescription;

    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    private ArrayList<Team> addedTeams = new ArrayList<>();
    private ArrayList<Team> notAddedTeams = new ArrayList<>();
    
    Comparator<Team> sortTeamNameAlphabeticAscending = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            return t1.getName().compareTo(t2.getName());
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            notAddedTeams = gdm.getTeamsUserCreaterOf(App.getLoggedInUser().getUser_ID());
            
            updateListViews();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateListViews() {
        listViewNotAddedTeams.getItems().clear();
        listViewAddedTeams.getItems().clear();
        
        Collections.sort(addedTeams, sortTeamNameAlphabeticAscending);
        Collections.sort(notAddedTeams, sortTeamNameAlphabeticAscending);
        
        for (Team team : notAddedTeams) {
            listViewNotAddedTeams.getItems().add(team.getName());
        }
        
        for (Team team : addedTeams) {
            listViewAddedTeams.getItems().add(team.getName());
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
        App.setLoggedInUser(null);
        App.setRoot("login");
    }

    @FXML
    private void main() throws IOException {
        App.setRoot("mainScreen");
    }

    @FXML
    private void teamEdit() throws IOException {
        App.setRoot("teamsInfoAdmin");
    }

    @FXML
    private void createNewsfeedMessages(ActionEvent event) throws Exception {
        //check if fields empty
        if (!textFieldNewsfeedMessagesTitle.getText().isBlank()
                && !textAreaNewsfeedMessagesDescription.getText().isBlank()) {
            //check if any teams is added
            if (!listViewAddedTeams.getItems().isEmpty()) {
                gdm.createNewsFeedMssage(new NewsFeedMessage(textFieldNewsfeedMessagesTitle.getText(),
                        App.getDtf().format(LocalDateTime.now()), addedTeams, App.getLoggedInUser(),
                        textAreaNewsfeedMessagesDescription.getText(), null));
                
                App.setRoot("mainScreen");

            } else {
               textErroMessage.setText("V??r s??d at v??lge minimum 1 hold at sende beskeden til"); //pleas select minimum 1 team to send the messages to
            }
        } else {
            textErroMessage.setText("V??r s??d at udfylde alle felterne");//pleas fill out all fields
        }
    }

    @FXML
    private void addTeam(ActionEvent event) {
        int index = listViewNotAddedTeams.getSelectionModel().getSelectedIndex();
        
        addedTeams.add(notAddedTeams.get(index));
        notAddedTeams.remove(index);
        
        updateListViews();
    }

    @FXML
    private void removeTeam(ActionEvent event) {
        int index = listViewAddedTeams.getSelectionModel().getSelectedIndex();
        
        notAddedTeams.add(addedTeams.get(index));
        addedTeams.remove(index);
        
        updateListViews();
    }
}
