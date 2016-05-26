package com.mlp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FXMLController implements Initializable {
    
    @FXML
    private GridPane mainGrid;
    
    @FXML
    private GridPane nextGrid;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(mainGrid.getHeight());
    }    
}
