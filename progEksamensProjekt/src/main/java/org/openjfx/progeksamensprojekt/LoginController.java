package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.classes.*;
import org.openjfx.databaseRepository.*;

public class LoginController {
    private TextField textFieldUsername;
    private PasswordField passwordFieldPassword;
    private Text textErrorMessage;
    
    
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
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
                App.setRoot("Sorting");
                //System.out.println("logged in");

            } else {
                textErrorMessage.setText("user dosen't exist or password dont match");
            }
        } else {
            textErrorMessage.setText("user dosen't exist or password dont match");
        }
    }
}
