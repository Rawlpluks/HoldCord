/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author clara
 */
public class CommentsController implements Initializable {
@FXML 
   private VBox newfeed; 
   private Parent fxml;
    /**
     * Initializes the controller class.
     */
    @Override 
   public void initialize(URL url, ResourceBundle rb) {
       try{
            fxml = FXMLLoader.load(getClass().getResource("newsfeed.fxml"));
            newfeed.getChildren().removeAll();
            newfeed.getChildren().setAll(fxml);
        }catch(IOException ex){
            
        }
    }    
    
    @FXML
    private void main () throws IOException {
        App.setRoot("mainScreen");
    }
    @FXML
    private void exit (){
        System.exit(0);
    }
    
}
