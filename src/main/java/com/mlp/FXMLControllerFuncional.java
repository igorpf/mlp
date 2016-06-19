/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 *
 * @author igor
 */
public class FXMLControllerFuncional implements Initializable{
    @FXML
    private GridPane mainGrid;

    @FXML
    private GridPane nextGrid;
    @FXML
    private AnchorPane mainPage;

    

    public int MAIN_COLUMNS, MAIN_ROWS;
    public int NEXT_COLUMNS, NEXT_ROWS;
    @FXML
    public void newGame(ActionEvent event) {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> move(KeyCode.DOWN)
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public void move(KeyCode key) {}
}
