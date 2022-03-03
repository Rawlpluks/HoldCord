/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.openjfx.progeksamensprojekt;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.openjfx.classes.*;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class EventsController implements Initializable {

    @FXML
    private TableView<Event> tableViewInvitedEvents;
    @FXML
    private TableView<Event> tableViewHostingEvents;
    @FXML
    private TableColumn tableColumnTeamsInvited;
    @FXML
    private TableColumn tableColumnNameOfEventInvited;
    @FXML
    private TableColumn tableColumnDateInvited;
    @FXML
    private TableColumn tableColumnTeamsHost;
    @FXML
    private TableColumn tableColumnNameOfEventHost;
    @FXML
    private TableColumn tableColumnDateHost;
    @FXML
    private Text textErrorMessage;
    
    private ArrayList<Event> invitedEvents = new ArrayList<>();
    private ArrayList<Event> hostedEvents = new ArrayList<>();

    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tableColumnTeamsInvited.setCellValueFactory(new PropertyValueFactory<Event, String>("teamNames"));
            tableColumnDateInvited.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
            tableColumnNameOfEventInvited.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
            
            //------
            
            tableColumnTeamsHost.setCellValueFactory(new PropertyValueFactory<Event, String>("teamNames"));
            tableColumnDateHost.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
            tableColumnNameOfEventHost.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
            
            updateTabels();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void updateTabels() throws Exception{
        tableViewInvitedEvents.getItems().clear();
        tableViewHostingEvents.getItems().clear();
        
        ObservableList<Event> invitedEvents = FXCollections.observableArrayList();
        
        invitedEvents.addAll(gdm.getUserParticipantEvents(App.getLoggedInUser().getUser_ID()));
            
        tableViewInvitedEvents.setItems(invitedEvents);
        
        //-------------------------------------------------------------
        
        ObservableList<Event> hostEvents = FXCollections.observableArrayList();
            
        hostEvents.addAll(gdm.getUserHostEvents(App.getLoggedInUser().getUser_ID()));

        
        tableViewHostingEvents.setItems(hostEvents);
    }
    
    @FXML
    private void exit () {
        System.exit(0);
    }
    @FXML
    private void teams() throws IOException {
        App.setRoot("teams");
    }
    @FXML
    private void events () throws IOException {
        App.setRoot("events");
    }
    @FXML
    private void settings () throws IOException {
        App.setRoot("settings");
    }
    @FXML
    private void logout () throws IOException {
        App.setLoggedInUser(null);
        App.setRoot("login");
    }
    @FXML
    private void main () throws IOException {
        App.setRoot("mainScreen");
    }
    @FXML
    private void eventInfo() throws IOException {
        System.out.println(tableViewInvitedEvents.getSelectionModel().getSelectedIndex());
        if(tableViewInvitedEvents.getSelectionModel().getSelectedIndex() >= 0) {
            System.out.println("test invited");
            
            App.setEvent(tableViewInvitedEvents.getSelectionModel().getSelectedItem());
            
            App.setRoot("eventView");
        } else {
           textErrorMessage.setText("Intet valgt"); //nothing selected
        }
    }
    @FXML
    private void eventCreate() throws IOException{
        App.setRoot("eventCreate");
    }

    @FXML
    private void cancelEvent(ActionEvent event) throws Exception {
        if(tableViewHostingEvents.getSelectionModel().getSelectedIndex() >= 0){
            gdm.deleteEvent(tableViewHostingEvents.getSelectionModel().getSelectedItem().getEvent_ID());
            
            updateTabels();
        } else  {
            textErrorMessage.setText("Vælg venligst en begivenhed du arrangere");
        }
    }

    @FXML
    private void editEvent(ActionEvent event) throws Exception{
        if(tableViewHostingEvents.getSelectionModel().getSelectedIndex() >= 0){
            App.setEvent(tableViewHostingEvents.getSelectionModel().getSelectedItem());
            
            App.setRoot("eventCreate");
        } else  {
            textErrorMessage.setText("Vælg venligst en begivenhed du arrangere");
        }
    }
}
