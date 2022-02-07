/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.classes.*;
import org.openjfx.databaseRepository.*;

/**
 *
 * @author chris
 */
public class CreateUserController {

    private TextField textFieldUsername;
    private Text textErroMessage;
    private PasswordField passwordFieldPassword;
    private PasswordField passwordFieldrepeatPassword;
    private TextField textFieldEmail;
    private TextField textFieldName;

    private void creatUser(ActionEvent event) throws Exception {
        textErroMessage.setText("");

        //Check if all fields is filled
        if (!textFieldUsername.getText().isBlank()
                && !passwordFieldPassword.getText().isBlank()
                && !passwordFieldrepeatPassword.getText().isBlank()
                && !textFieldEmail.getText().isBlank()) {

            //Check if user already exist
            UserDatabaseMethods userDatabasemethods = new UserDatabaseMethods();
            if (!userDatabasemethods.cehckForMatchingUser(textFieldUsername.getText())) {

                //Check if password have a special and uppercase character and at least 8 carachters long
                boolean passwordMeetsRequirements = false;
                char[] passwordChars = passwordFieldPassword.getText().toCharArray();

                Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(passwordFieldPassword.getText());

                //length
                if (passwordFieldPassword.getText().length() >= 8) {
                    //special caracahter
                    if (matcher.find()) {
                        //upper case carachter
                        for (int i = 0; i < passwordChars.length; i++) {
                            if (Character.isUpperCase(passwordChars[i])) {
                                passwordMeetsRequirements = true;
                                break;
                            }
                        }
                        //passwords is identicel
                        if (passwordMeetsRequirements) {
                            if (passwordFieldPassword.getText().equals(passwordFieldrepeatPassword.getText())) {

                                SecurityMethods securityMethods = new SecurityMethods();

                                userDatabasemethods.createUser(new User(textFieldUsername.getText(),
                                        securityMethods.hexString(passwordFieldPassword.getText()),
                                        textFieldName.getText(), textFieldEmail.getText()));

                                App.setLoggedInUser(userDatabasemethods.getoggedInUser(textFieldUsername.getText()));
                                App.setRoot("Sorting");
                            } else {
                                textErroMessage.setText("Password meets requirements, but donsen't match");
                            }
                        } else {
                            textErroMessage.setText("Password is missing a uppercase character");
                        }
                    } else {
                        textErroMessage.setText("Password is missing a special character");
                    }
                } else {
                    textErroMessage.setText("Password needs to be at least 8 characters long");

                }
            } else {
                textErroMessage.setText("User already exist");
            }
        } else {
            textErroMessage.setText("All fields need to be filled");
        }

    }

    private void switchToLoginScreen(ActionEvent event) throws IOException, Exception {
        App.setRoot("login");
    }
}
