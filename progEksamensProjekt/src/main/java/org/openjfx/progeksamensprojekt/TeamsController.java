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
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.openjfx.classes.Team;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TeamsController implements Initializable {

    @FXML
    private ListView listViewTeams;
    @FXML
    private Text textErroMessage;

    private ArrayList<Team> teams = new ArrayList<>();
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            teams = gdm.getTeamsUserMemberOf(App.getLoggedInUser().getUser_ID());

            teams.addAll(gdm.getTeamsUserCreaterOf(App.getLoggedInUser().getUser_ID()));

            //--------------
            //sort alpabetic
            //--------------
            listViewTeams.getItems().clear();

            for (Team team : teams) {
                listViewTeams.getItems().add(team.getName());
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
    private void teamInfo() throws IOException {
        int index = listViewTeams.getSelectionModel().getSelectedIndex();

        //check if team is selected
        if (index >= 0) {
            //check if admin
            if (teams.get(index).getCreaterOfTeam().equals(App.getLoggedInUser())) {
                App.setTeam(teams.get(index));
                App.setRoot("teamsInfoAdmin");
            } else {
                //send to member view
                App.setTeam(teams.get(index));
                App.setRoot("teamsInfoMember");
            }
        } else {
            textErroMessage.setText("Select team to view");
        }
    }

    @FXML
    private void teamCreate() throws IOException {
        App.setRoot("teamCreate");
    }
}
