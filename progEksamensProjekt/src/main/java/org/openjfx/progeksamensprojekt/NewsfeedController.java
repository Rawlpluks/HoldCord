/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;
import org.openjfx.classes.*;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class NewsfeedController implements Initializable {

    private int newsFeedMessagesNumber = 0;
    private ArrayList<NewsFeedMessage> newsfeedmesages = new ArrayList<>();
    @FXML
    private Label labelSenderName;
    @FXML
    private Label labelTeamNameTo;
    @FXML
    private TextField textFieldTitelOfMessages;
    @FXML
    private Label labelDateOfMessage;
    @FXML
    private TextArea textAreaMessage;
    @FXML
    private Button buttonDeleteNews;
    
    
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //get alle news feedmessages
            newsfeedmesages = gdm.getUsersNewsFeedMessages(App.getLoggedInUser().getUser_ID());
            
            //check if we already viewng af specefic one
            if (App.getCurrentNewsFeedMessage() == null) {
                displayNewsfeedMessages(newsfeedmesages.get(newsFeedMessagesNumber));
            } else {
                for (int i = 0; i < newsfeedmesages.size(); i++){
                    if(newsfeedmesages.get(i).equals(App.getCurrentNewsFeedMessage())){
                        newsFeedMessagesNumber = i;
                        return;
                    }
                }
                
                displayNewsfeedMessages(newsfeedmesages.get(newsFeedMessagesNumber));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayNewsfeedMessages(NewsFeedMessage _newsFeedMessage) throws Exception {
        //check if your the owner and therefor can delete the news
        if (_newsFeedMessage.getSender().equals(App.getLoggedInUser())) {
            buttonDeleteNews.setVisible(true);
        } else {
            buttonDeleteNews.setVisible(false);
        }

        labelSenderName.setText(_newsFeedMessage.getSender().getName());

        //set up teams
        String teams = "";
        for (Team team : _newsFeedMessage.getTeams()) {
            if (teams.equals("")) {
                teams += team.getName();
            } else {
                teams += ", " + team.getName();
            }
        }
        labelTeamNameTo.setText(teams);

        labelDateOfMessage.setText(_newsFeedMessage.getDate());
        textAreaMessage.setText(_newsFeedMessage.getMessages());
        
        App.setCurrentNewsFeedMessage(_newsFeedMessage);
    }

    @FXML
    private void prevNewsfeedMessages(ActionEvent event) throws Exception {
        if (newsFeedMessagesNumber < 0) {
            newsFeedMessagesNumber = newsfeedmesages.size() - 1;
        } else if (newsFeedMessagesNumber > newsfeedmesages.size() - 1) {
            newsFeedMessagesNumber = 0;
        } else {
            newsFeedMessagesNumber--;
        }

        displayNewsfeedMessages(newsfeedmesages.get(newsFeedMessagesNumber));
    }

    @FXML
    private void nextNewsfeedMessages(ActionEvent event) throws Exception {
        if (newsFeedMessagesNumber < 0) {
            newsFeedMessagesNumber = newsfeedmesages.size() - 1;
        } else if (newsFeedMessagesNumber > newsfeedmesages.size() - 1) {
            newsFeedMessagesNumber = 0;
        } else {
            newsFeedMessagesNumber++;
        }

        displayNewsfeedMessages(newsfeedmesages.get(newsFeedMessagesNumber));
    }

    @FXML
    private void deleteNewsFeedMessages(ActionEvent event) throws Exception{
        gdm.deleteNewsFeedMessage(newsfeedmesages.get(newsFeedMessagesNumber).getNewsFeedMessage_ID());
        
        newsfeedmesages.remove(newsFeedMessagesNumber);
        
        newsFeedMessagesNumber --;
        
        if(newsFeedMessagesNumber < 0){
            newsFeedMessagesNumber = 0;
        }
        displayNewsfeedMessages(newsfeedmesages.get(newsFeedMessagesNumber));
    }
}
