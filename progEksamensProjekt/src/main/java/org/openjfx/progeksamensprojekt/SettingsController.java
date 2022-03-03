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
import javafx.scene.control.TextField;
import org.openjfx.classes.User;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;
import org.openjfx.databaseRepository.SecurityMethods;
import org.openjfx.databaseRepository.UserDatabaseMethods;
import org.w3c.dom.UserDataHandler;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldPassword;
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    @FXML
    private TextField textFieldUsername;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            updateUserDisplayInfo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateUserDisplayInfo() {
        User user = App.getLoggedInUser();

        textFieldName.setText(user.getName());
        textFieldEmail.setText(user.getEmail());
        textFieldPassword.setText("Skriv dit nye kodeord her");
        textFieldUsername.setText(user.getUsername());
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
    private void updateUserInfo(ActionEvent event) throws Exception{
        UserDatabaseMethods udm = new UserDatabaseMethods();
        SecurityMethods sm = new SecurityMethods();

        App.getLoggedInUser().setName(textFieldName.getText());
        App.getLoggedInUser().setEmail(textFieldEmail.getText());
        App.getLoggedInUser().setUsername(textFieldUsername.getText());
        App.getLoggedInUser().setPassword(sm.hexString(textFieldPassword.getText()));

        udm.editUser(App.getLoggedInUser());
        
        updateUserDisplayInfo();
    }
}
