package com.resonanz.client.controller;

import com.resonanz.client.Network;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Network network;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        network = new Network();
    }
}
