module org.openjfx.progeksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.openjfx.progeksamensprojekt to javafx.fxml;
    exports org.openjfx.progeksamensprojekt;
}
