package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HelloWorld {

    public TextField nameField;

    public void startButton(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println("Hello " + nameField.getText());
    }
}
