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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.classes.*;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class EventsController implements Initializable {

    @FXML
    private TableView<Event> tableViewInvitedEvents;
    @FXML
    private TableView tableViewHostingEvents;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<Event> invitedData = FXCollections.observableArrayList();
            
            tableColumnTeamsInvited.setCellValueFactory(new PropertyValueFactory<Team, String>("name"));
            tableColumnDateInvited.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
            tableColumnNameOfEventInvited.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
            
            ArrayList<Event> testEvents = new ArrayList<>();
            
            ArrayList<Team> testTeams =  new ArrayList<>();
            
            testTeams.add(new Team(0, "testTeam", null, null));
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy/ HH:mm:ss");
            
            testEvents.add(new Event(null, dtf.format(LocalDateTime.now()), "testEvent", null, testTeams));
            
            ObservableList<Event> testEventsOB = FXCollections.observableArrayList();
            
            testEventsOB.addAll(testEvents);
            
            tableViewInvitedEvents.setItems(testEventsOB);
            
            System.out.println(testEventsOB.get(0));
            System.out.println("HHHHHHHHHHHHHHHHEEEEEEEEEEEEEEEEEEEEEEEEELLLLLLLLLLLLLLLLLLLLLLLLLLPPPPPPPPPPPPPPPPPPPPPPPP");
            
        } catch (Exception e) {
        }
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
        App.setRoot("login");
    }
    @FXML
    private void main () throws IOException {
        App.setRoot("mainScreen");
    }
    @FXML
    private void eventInfo() throws IOException {
        App.setRoot("eventView");
    }
    @FXML
    private void eventCreate() throws IOException{
        App.setRoot("eventCreate");
    }
}
