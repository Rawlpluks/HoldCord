/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.openjfx.classes.NewsFeedMessageComment;
import org.openjfx.classes.User;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

/**
 * FXML Controller class
 *
 * @author clara
 */
public class CommentsController implements Initializable {

    @FXML
    private VBox newfeed;
    private Parent fxml;
    @FXML
    private TextArea textAreaCommentsDisplay;
    @FXML
    private TextArea textAreaComment;

    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("newsfeed.fxml"));
            newfeed.getChildren().removeAll();
            newfeed.getChildren().setAll(fxml);

            updateComments();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateComments() {

        ArrayList<NewsFeedMessageComment> comments = App.getCurrentNewsFeedMessage().getComments();

        //struktur - Name sender - date -(new line)- comment
        String allCommentsFormated = "";
        for (NewsFeedMessageComment comment : comments) {
            if (allCommentsFormated.equals("")) {
                allCommentsFormated += comment.getSender().getName() + " " + comment.getDate()
                        + "\n" + comment.getComment();
            } else {
                allCommentsFormated += "\n\n" + comment.getSender().getName() + " " + comment.getDate()
                        + "\n" + comment.getComment();
            }
        }

        textAreaCommentsDisplay.setText(allCommentsFormated);
    }

    @FXML
    private void main() throws IOException {
        App.setRoot("mainScreen");
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void commentPost(ActionEvent event) throws Exception {
        if (!textAreaComment.getText().isBlank()) {
            gdm.commentNewsFeedMessage(new NewsFeedMessageComment(App.getLoggedInUser(),
                    App.getDtf().format(LocalDateTime.now()), textAreaComment.getText()));

            updateComments();
        } else {
            //pleas write at least one character in your comment
        }
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
        App.setLoggedInUser(new User());
    }

}
