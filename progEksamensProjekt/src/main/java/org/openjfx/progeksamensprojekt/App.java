package org.openjfx.progeksamensprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.openjfx.classes.*;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static User loggedInUser = new User();
    private static Event event = new Event();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy/ HH:mm:ss");
    private static Team team = new Team();

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        scene = new Scene(loadFXML("login"));
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User _loggedInUser) {
        App.loggedInUser = _loggedInUser;
    }

    public static Event getEvent() {
        return event;
    }

    public static void setEvent(Event editingEvent) {
        App.event = editingEvent;
    }

    public static DateTimeFormatter getDtf() {
        return dtf;
    }

    public static Team getTeam() {
        return team;
    }

    public static void setTeam(Team team) {
        App.team = team;
    }
}