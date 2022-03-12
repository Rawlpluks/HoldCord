/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.classes.User;
import org.openjfx.databaseRepository.SecurityMethods;
import org.openjfx.databaseRepository.UserDatabaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class SignupController implements Initializable {

    @FXML
    private TextField textFieldUsername;
    @FXML
    private Text textErroMessage;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldrepeatPassword;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldName;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void creatUser(ActionEvent event) throws Exception {

        //Check if all fields is filled
        if (!textFieldUsername.getText().isBlank()
                && !passwordFieldPassword.getText().isBlank()
                && !passwordFieldrepeatPassword.getText().isBlank()
                && !textFieldEmail.getText().isBlank()
                && !textFieldName.getText().isBlank()){

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
                                App.setRoot("mainScreen");
                            } else {
                                textErroMessage.setText("Kodeordet møder kravene, men matcher ikke"); //Password meets requirements, but donsen't match
                            }
                        } else {
                            textErroMessage.setText("Kodeordet mangler et stort bogstav"); //Password is missing a uppercase character
                        }
                    } else {
                        textErroMessage.setText("Kodeordet mangler et specialtegn "); //Password is missing a special character
                    }
                } else {
                    textErroMessage.setText("Kodeordet skal være minimum 8 tegn langt"); //Password needs to be at least 8 characters long"

                }
            } else {
                textErroMessage.setText("Brugeren findes allerede"); //User already exist
            }
        } else {
            textErroMessage.setText("Alle felterne skal være udfyldt"); //All fields need to be filled
        }

    }
    
    @FXML
    private void exit(){
        System.exit(0);
    }
    @FXML
    private void goToLogin() throws IOException {
        App.setRoot("login");
    }
}
