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
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.openjfx.classes.*;
import org.openjfx.databaseRepository.GeneralDatabbaseMethods;

/**
 * FXML Controller class
 *
 * @author Clara Maj
 */
public class EventCreateController implements Initializable {

    @FXML
    private ListView listViewTeamsNotAdded;
    @FXML
    private ListView listViewTeamsAdded;
    @FXML
    private TextField textFieldNameOfEvent;
    @FXML
    private TextArea textAreaDescreptionOfEvent;
    @FXML
    private DatePicker datePickerDate;
    @FXML
    private Text textErroMessage;

    private boolean edittingEvent;
    private GeneralDatabbaseMethods gdm = new GeneralDatabbaseMethods();
    private ArrayList<Team> teamsAdded = new ArrayList<>();
    private ArrayList<Team> teamsNotAdded = new ArrayList<>();

    Comparator<Team> sortTeamNameAlphabeticAscending = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            return t1.getName().compareTo(t2.getName());
        }
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            Event eventToBeEdited = App.getEvent();
            teamsNotAdded = gdm.getTeamsUserMemberOf(App.getLoggedInUser().getUser_ID());
            teamsNotAdded.addAll(gdm.getTeamsUserCreaterOf(App.getLoggedInUser().getUser_ID()));

            //if editing an event load the info and dispaly it
            if (eventToBeEdited != null) {
                edittingEvent = true;

                teamsAdded.addAll(eventToBeEdited.getTeams());
                //teamsNotAdded.removeAll(teamsAdded);
                //teamsNotAdded.removeAll(eventToBeEdited.getTeams());

                for (Team teamAdded : teamsAdded) {
                    for (int i = 0; i < teamsNotAdded.size(); i++) {
                        if (teamAdded.getTeam_ID() == teamsNotAdded.get(i).getTeam_ID()) {
                            teamsNotAdded.remove(i);
                            i--;
                        }
                    }
                }

                updateListViews();

                textFieldNameOfEvent.setText(eventToBeEdited.getTitle());
                textAreaDescreptionOfEvent.setText(eventToBeEdited.getDescreption());

                //format event date to date picker format
                String[] eventDate = eventToBeEdited.getDate().split("/");

                String dateToFormat = eventDate[2] + "-" + eventDate[1] + "-" + eventDate[0];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateToBeSet = LocalDate.parse(dateToFormat, formatter);

                datePickerDate.setValue(dateToBeSet);
            } else {
                //if no event is to be edited, load teams
                edittingEvent = false;

                updateListViews();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateListViews() {
        listViewTeamsAdded.getItems().clear();
        listViewTeamsNotAdded.getItems().clear();

        Collections.sort(teamsAdded, sortTeamNameAlphabeticAscending);
        Collections.sort(teamsNotAdded, sortTeamNameAlphabeticAscending);

        for (Team team : teamsAdded) {
            listViewTeamsAdded.getItems().add(team.getName());
        }
        for (Team team : teamsNotAdded) {
            listViewTeamsNotAdded.getItems().add(team.getName());
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void teams() throws IOException {
        App.setEvent(null);
        App.setRoot("teams");
    }

    @FXML
    private void events() throws IOException {
        App.setEvent(null);
        App.setRoot("events");
    }

    @FXML
    private void settings() throws IOException {
        App.setEvent(null);
        App.setRoot("settings");
    }

    @FXML
    private void logout() throws IOException {
        App.setEvent(null);
        App.setLoggedInUser(null);
        App.setRoot("login");
    }

    @FXML
    private void main() throws IOException {
        App.setEvent(null);
        App.setRoot("primary");
    }

    @FXML
    private void addSelectedTeamToEvent(ActionEvent event) {
        int index = listViewTeamsNotAdded.getSelectionModel().getSelectedIndex();

        teamsAdded.add(teamsNotAdded.get(index));
        teamsNotAdded.remove(index);

        updateListViews();
    }

    @FXML
    private void removeSelectedTeamFromEvent(ActionEvent event) {
        int index = listViewTeamsAdded.getSelectionModel().getSelectedIndex();

        teamsNotAdded.add(teamsAdded.get(index));
        teamsAdded.remove(index);

        updateListViews();
    }

    private boolean checkIfDateIsValid(LocalDate selectedDate) {
        String currnetDate = App.getDtf().format(LocalDateTime.now());

        //split dates
        String[] currentDateElements = currnetDate.split("/");
        String[] selectedDateElemets = selectedDate.toString().split("-");

        //format for comparisen
        String formatedCurrentDate = currentDateElements[2] + currentDateElements[1] + currentDateElements[0];
        String formatedSelectedDate = selectedDateElemets[0] + selectedDateElemets[1] + selectedDateElemets[2];

        //compare
        if (formatedCurrentDate.equals(formatedSelectedDate)) {
            return true;
        } else {
            ArrayList<String> sortedDates = new ArrayList<>();
            sortedDates.add(formatedCurrentDate);
            sortedDates.add(formatedSelectedDate);

            Collections.sort(sortedDates);

            if (sortedDates.get(1).equals(formatedSelectedDate)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @FXML
    private void createOrUpdateEvent(ActionEvent event) throws Exception {
        if (!textFieldNameOfEvent.getText().isBlank()
                && !textAreaDescreptionOfEvent.getText().isBlank()) {
            if (checkIfDateIsValid(datePickerDate.getValue())) {
                if (listViewTeamsAdded.getItems().size() > 0) {
                    String[] selectedDate = datePickerDate.getValue().toString().split("-");

                    String date = selectedDate[2] + "/" + selectedDate[1] + "/" + selectedDate[0];
                    
                    Event ourEvent = new Event(App.getLoggedInUser(),
                                date, textFieldNameOfEvent.getText(),
                                textAreaDescreptionOfEvent.getText(), teamsAdded);
                    
                    if (edittingEvent) {
                        gdm.editEvent(ourEvent);
                    } else {
                        gdm.createEvent(ourEvent);
                    }
                    edittingEvent = false;

                    App.setRoot("events");
                    App.setEvent(null);

                } else {
                    textErroMessage.setText("Inviter mindst et hold");
                }
            } else {
                textErroMessage.setText("Vælg en gyldig dato");
            }
        } else {
            textErroMessage.setText("Udfyld alle felter");
        }
    }
}
