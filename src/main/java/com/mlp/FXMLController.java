package com.mlp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FXMLController implements Initializable {

    @FXML
    private GridPane mainGrid;

    @FXML
    private GridPane nextGrid;
    
    private Rectangle[][] mainBoard;
    
    private Rectangle[][] nextBoard;
    @FXML
    private void handleButtonAction(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int columns=mainGrid.getColumnConstraints().size();
        int rows=mainGrid.getRowConstraints().size();
        mainBoard=new Rectangle[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {

                mainBoard[i][j] = new Rectangle(25, 25);
                mainBoard[i][j].setFill(Color.TRANSPARENT);
                mainGrid.add(mainBoard[i][j], j, i);
            }
        }
        columns=nextGrid.getColumnConstraints().size();
        rows=nextGrid.getRowConstraints().size();
        nextBoard=new Rectangle[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {

                nextBoard[i][j] = new Rectangle(25, 25);
                nextBoard[i][j].setFill(Color.BLACK);
                nextGrid.add(nextBoard[i][j], j, i);
            }
        }
    
    }
    public void clearBoards(){
        
    }
}
