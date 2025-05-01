module graphicTest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    exports view;
    opens view to javafx.fxml;
}