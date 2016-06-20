/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp;

import com.mlp.funcional.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    
    private List<Rectangle> mainBoard;

    private List<Rectangle> nextBoard;
    
    List<Integer> board;
    
    private boolean hasStarted = false;
    
    private Tetromino currentTetro;
    private Tetromino nextTetro;
    
    public int MAIN_COLUMNS, MAIN_ROWS;
    public int NEXT_COLUMNS, NEXT_ROWS;
    @FXML
    public void newGame(ActionEvent event) {
        hasStarted = true;
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> move(KeyCode.DOWN, currentTetro)
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MAIN_COLUMNS = mainGrid.getColumnConstraints().size();
        MAIN_ROWS = mainGrid.getRowConstraints().size();
        mainBoard = new ArrayList<>();
        IntStream.range(0, MAIN_ROWS*MAIN_COLUMNS).forEach(n->{
            Rectangle r = new Rectangle(25,25);
            r.setFill(Color.TRANSPARENT);
            mainBoard.add(r);
            mainGrid.add(r, n%MAIN_COLUMNS, n/MAIN_COLUMNS);
        });
        NEXT_COLUMNS = nextGrid.getColumnConstraints().size();
        NEXT_ROWS = nextGrid.getRowConstraints().size();
        nextBoard = new ArrayList<>();
        IntStream.range(0, NEXT_ROWS*NEXT_COLUMNS).forEach(n->{
            Rectangle r = new Rectangle(25,25);
            r.setFill(Color.TRANSPARENT);
            nextBoard.add(r);
            nextGrid.add(r, n%NEXT_COLUMNS, n/NEXT_COLUMNS);
        });
        board = new ArrayList<>();
        IntStream.range(0, MAIN_ROWS*MAIN_COLUMNS).forEach(n->{
            board.add(0);
        });
        mainPage.setOnKeyPressed((KeyEvent event) -> {
            move(event.getCode(), currentTetro);
        });
        currentTetro = new TetrominoJ();
        print(currentTetro);
    }
    public void move(KeyCode key, Tetromino t) {
        if(!hasStarted)
            return;
        
        boolean canMove = true, flag = true;
        switch (key) {
            case DOWN:
                
                break;
            case LEFT:
                
                break;
            case RIGHT:
                break;
            case R: // sentido anti-horário
                break;
            case T: // sentido horário
                break;
                
            default:
                break;
        }
    }
    public void print(Tetromino t) {
        Point2D min=t.min, max = t.max;
        mainBoard = mainBoard.stream().map(r->{
            int index=mainBoard.indexOf(r);
            int x=index/MAIN_COLUMNS,y=index%MAIN_COLUMNS;
            int dxMin=(int) (x-min.getX()), dxMax=(int) (x-max.getX());
            int dyMin=(int) (y-min.getY()), dyMax = (int) (y-max.getY());
            if(dxMin >=0 && dxMax<=0 && dyMin >= 0 && dyMax <= 0// está dentro do bounding box
                && t.block.get(dxMin*Tetromino.SIZE+dyMin)){
                r.setFill(t.color);
                return r;
            }
            else 
                return r;
        }).collect(Collectors.toList());
    }
}
