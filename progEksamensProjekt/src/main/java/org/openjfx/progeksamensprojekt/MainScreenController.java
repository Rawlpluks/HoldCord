/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainScreenController implements Initializable{

    @FXML
    private VBox newfeed;
    private Parent fxml;
    
    @FXML
    private Label userName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            System.out.println("jeg prøver");
            fxml = FXMLLoader.load(getClass().getResource("newsfeed.fxml"));
            newfeed.getChildren().removeAll();
            newfeed.getChildren().setAll(fxml);
        }catch(IOException ex){
            
        }
    } 
    
    @FXML
    private void exit () {
        System.exit(0);
    }
}
