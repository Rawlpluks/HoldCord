module org.openjfx.progeksamensprojekt {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.progeksamensprojekt to javafx.fxml;
    exports org.openjfx.progeksamensprojekt;
}
