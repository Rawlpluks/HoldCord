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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.databaseRepository.SecurityMethods;
import org.openjfx.databaseRepository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class LoginController implements Initializable {

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Text textErrorMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    @FXML
    private void login(ActionEvent event) throws Exception {
        textErrorMessage.setText("");

        //check if user exist
        UserDatabaseMethods userDatabasemethods = new UserDatabaseMethods();
        if (userDatabasemethods.cehckForMatchingUser(textFieldUsername.getText())) {

            //check if user and passowrd match
            SecurityMethods securityMethods = new SecurityMethods();
            if (userDatabasemethods.checkForMatchingPassword(textFieldUsername.getText(),
                    securityMethods.hexString(passwordFieldPassword.getText()))) {

                App.setLoggedInUser(userDatabasemethods.getoggedInUser(textFieldUsername.getText()));
                App.setRoot("mainScreen");
                //System.out.println("logged in");

            } else {
                textErrorMessage.setText("Enten findes brugeren ikke, ellers matcher kodeordet ikke");
            }
        } else {
            textErrorMessage.setText("Enten findes brugeren ikke, ellers matcher kodeordet ikke");
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }


    @FXML
    private void switchToSignup(ActionEvent event) throws Exception {
        App.setRoot("signup");
    }
}
