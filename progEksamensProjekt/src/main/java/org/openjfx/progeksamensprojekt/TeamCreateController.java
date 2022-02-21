/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.openjfx.classes.Team;
import org.openjfx.classes.User;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class TeamCreateController implements Initializable {
    
    @FXML
    private TextField textFieldTeamName;
    @FXML
    private TextArea textAreaTeamDescription;
    @FXML
    private ListView listViewNotAddedMembers;
    @FXML
    private ListView listViewAddedMembers;
    
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    private ArrayList<User> addedMembers = new ArrayList<>();
    private ArrayList<User> notAddedMembers = new ArrayList<>();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            notAddedMembers = gdm.getAllOtherUsers(App.getLoggedInUser().getUser_ID());
            
            updateListViews();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void updateListViews() {
        listViewNotAddedMembers.getItems().clear();
        listViewAddedMembers.getItems().clear();
        
        //--------------
        //sort alpabetic
        //--------------
        
        for (User user : notAddedMembers) {
            listViewNotAddedMembers.getItems().add(user.getName());
        }
        
        for (User user : addedMembers) {
            listViewAddedMembers.getItems().add(user.getName());
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
    private void createTeam(ActionEvent event) throws Exception {
        //check if any field is empty
        if(!textFieldTeamName.getText().isBlank() &&
                !textAreaTeamDescription.getText().isBlank()){
            if(!listViewAddedMembers.getItems().isEmpty()){
                gdm.createTeam(new Team(textFieldTeamName.getText(), 
                        textAreaTeamDescription.getText(), App.getLoggedInUser(), addedMembers));
                
                //reset for new team
                listViewAddedMembers.getItems().clear();
                listViewNotAddedMembers.getItems().clear();

                notAddedMembers.addAll(addedMembers);

                
                updateListViews();

                textAreaTeamDescription.setText("");
                textFieldTeamName.setText("");
            } else {
                //pleas add atleast one member to 2 the team
            }
        } else {
            //fill all fields
        }
    }

    @FXML
    private void addMember(ActionEvent event) {
        int index = listViewNotAddedMembers.getSelectionModel().getSelectedIndex();
        
        addedMembers.add(notAddedMembers.get(index));
        notAddedMembers.remove(index);
        
        updateListViews();
    }

    @FXML
    private void removeMember(ActionEvent event) {
        int index = listViewAddedMembers.getSelectionModel().getSelectedIndex();
        
        notAddedMembers.add(addedMembers.get(index));
        addedMembers.remove(index);
        
        updateListViews();
    }
}
