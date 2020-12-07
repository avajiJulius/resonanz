package com.resonanz.client.controller;

import com.resonanz.client.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Network network;

    @FXML
    TextField messageField;

    @FXML
    TextArea mainArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        network = new Network((args) -> {
            mainArea.appendText((String)args[0]);
        });
    }

    public void sendMessage(ActionEvent actionEvent) {
        network.sendMessage(messageField.getText());
        messageField.clear();
        messageField.requestFocus();
    }
}
