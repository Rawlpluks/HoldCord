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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //get alle news feedmessages
            GeneralDatabbaseMethods generalDatabbaseMethods = new GeneralDatabbaseMethods();

            newsfeedmesages = generalDatabbaseMethods.getNewsFeedMessages(App.getLoggedInUser().getUser_ID());

            displayNewsfeedMessages(newsfeedmesages.get(newsFeedMessagesNumber));

        } catch (Exception e) {
        }
    }

    private void displayNewsfeedMessages(NewsFeedMessage _newsFeedMessage) throws Exception {
        //display messages
        //String messages = _newsFeedMessage.getMessages();

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
        labelDateOfMessage.setText(_newsFeedMessage.getDate().toString());
        textAreaMessage.setText(_newsFeedMessage.getMessages());

        //display comments
        ArrayList<NewsFeedMessageComment> comments = _newsFeedMessage.getComments();

        //create new strings to comment
        //struktur - Name sender - date - comment
        String allComments = "";
        for (NewsFeedMessageComment comment : comments) {
            if (allComments.equals("")) {
                allComments += comment.getSender().getName() + " " + comment.getDate().toString()
                        + "\n" + comment.getComment();
            } else {
                allComments += "\n\n" + comment.getSender().getName() + " " + comment.getDate().toString()
                        + "\n" + comment.getComment();
            }
        }
    }

    private void displayNewsfeedMessagesComments() {

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
}
