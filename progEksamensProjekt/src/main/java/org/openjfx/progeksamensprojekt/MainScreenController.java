/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.openjfx.classes.NewsFeedMessage;
import org.openjfx.classes.User;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;
import org.openjfx.databaseRepository.SecurityMethods;

public class MainScreenController implements Initializable {

    @FXML
    private VBox newfeed;
    private Parent fxml;

    @FXML
    private TextField textFieldUserInfoName;
    @FXML
    private TextField textFieldUserInfoEmail;
    @FXML
    private TableView<Event> tableViewUsersEvents;
    @FXML
    private TableColumn tableColumnTeamsUsersEvents;
    @FXML
    private TableColumn tableColumnNameOfEventUsersEvents;
    @FXML
    private TableColumn tableColumnDateUsersEvents;
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    @FXML
    private Label labelName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("newsfeed.fxml"));
            newfeed.getChildren().removeAll();
            newfeed.getChildren().setAll(fxml);
                  

            textFieldUserInfoName.setText(App.getLoggedInUser().getName());
            textFieldUserInfoEmail.setText(App.getLoggedInUser().getEmail());
            labelName.setText(" " + App.getLoggedInUser().getName());
            
            //set event table view
            tableColumnTeamsUsersEvents.setCellValueFactory(new PropertyValueFactory<Event, String>("teamNames"));
            tableColumnDateUsersEvents.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
            tableColumnNameOfEventUsersEvents.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
            
            ObservableList<Event> usersvents = FXCollections.observableArrayList();

            usersvents.addAll(gdm.getUserParticipantEvents(App.getLoggedInUser().getUser_ID()));

            usersvents.addAll(gdm.getUserHostEvents(App.getLoggedInUser().getUser_ID()));

            tableViewUsersEvents.setItems(usersvents);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void teams() throws IOException {
        App.setCurrentNewsFeedMessage(null);
        App.setRoot("teams");
    }

    @FXML
    private void events() throws IOException {
        App.setCurrentNewsFeedMessage(null);
        App.setRoot("events");
    }

    @FXML
    private void settings() throws IOException {
        App.setCurrentNewsFeedMessage(null);
        App.setRoot("settings");
    }

    @FXML
    private void logout() throws IOException {
        App.setCurrentNewsFeedMessage(null);
        App.setLoggedInUser(null);
        App.setRoot("login");
    }

    @FXML
    private void main() throws IOException {
        App.setCurrentNewsFeedMessage(null);
        App.setRoot("mainScreen");
    }

    @FXML
    private void comments() throws IOException {
        App.setRoot("comments");
    }
}
