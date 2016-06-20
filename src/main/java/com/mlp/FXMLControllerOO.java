package com.mlp;

import com.mlp.oo.entities.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import com.mlp.oo.functions.Functions;
import javafx.scene.control.Label;

public class FXMLControllerOO implements Initializable {

    @FXML
    private GridPane mainGrid;

    @FXML
    private GridPane nextGrid;
    @FXML
    private AnchorPane mainPage;
    @FXML
    private Label scoreLabel;

    private Rectangle[][] mainBoard;

    private Rectangle[][] nextBoard;
    
    private int[][] board;

    private Timeline timeline;

    Tetromino currentTetro;
    Tetromino nextTetro;

    public int MAIN_COLUMNS, MAIN_ROWS;
    
    public int NEXT_COLUMNS, NEXT_ROWS;
    
    private boolean hasStarted = false;
    
    private int score = 0;

    @FXML
    public void newGame(ActionEvent event) {
        hasStarted=true;
        timeline = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> move(KeyCode.DOWN, currentTetro)
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        printNext();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainPage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                move(event.getCode(), currentTetro);
            }

        });
        MAIN_COLUMNS = mainGrid.getColumnConstraints().size();
        MAIN_ROWS = mainGrid.getRowConstraints().size();
        mainBoard = new Rectangle[MAIN_ROWS][MAIN_COLUMNS];
        for (int i = 0; i < MAIN_ROWS; ++i) {
            for (int j = 0; j < MAIN_COLUMNS; ++j) {
                mainBoard[i][j] = new Rectangle(25, 25);
                mainBoard[i][j].setFill(Color.TRANSPARENT);
                mainGrid.add(mainBoard[i][j], j, i);
            }
        }
        colourLimit();
         
        NEXT_COLUMNS = nextGrid.getColumnConstraints().size();
        NEXT_ROWS = nextGrid.getRowConstraints().size();
        Tetromino tAux = new TetrominoT();
        nextBoard = new Rectangle[NEXT_ROWS][NEXT_COLUMNS];
        for (int i = 0; i < NEXT_ROWS; ++i) {
            for (int j = 0; j < NEXT_COLUMNS; ++j) {
                nextBoard[i][j] = new Rectangle(25, 25);
                if(tAux.getBlock()[i][j])
                    nextBoard[i][j].setFill(tAux.getColor());
                else
                    nextBoard[i][j].setFill(Color.TRANSPARENT);  
                nextGrid.add(nextBoard[i][j], j, i);
            }
        }
        
        board = new int[MAIN_ROWS][MAIN_COLUMNS];
        for (int i = 0; i < MAIN_ROWS; ++i) {
            for (int j = 0; j < MAIN_COLUMNS; ++j) {
                board[i][j] = 0;
            }
        }
        createTetromino();
        createTetromino();
    }
    
    public void createTetromino(){
        int n = (int) (Math.random() * 7.0);
        if(nextTetro != null){
            currentTetro = new TetrominoJ();
            currentTetro.setMin(new Point2D(nextTetro.getMin().getX(),nextTetro.getMin().getY()));
            currentTetro.setMax(new Point2D(nextTetro.getMax().getX(),nextTetro.getMax().getY()));
            currentTetro.setColor(nextTetro.getColor());
            boolean[][] matrix = new boolean[4][4];
            for(int i = 0; i < nextTetro.getBlock().length; i++)
                for(int j = 0; j < nextTetro.getBlock()[0].length; j++)
                    matrix[i][j] = nextTetro.getBlock()[i][j];
            currentTetro.setBlock(matrix);
        }
        else
            n = 0;
        switch(n){
            case 0:
                nextTetro = new TetrominoT(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 1:
                nextTetro = new TetrominoI(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 2:
                nextTetro = new TetrominoJ(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 3:
                nextTetro = new TetrominoL(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 4:
                nextTetro = new TetrominoO(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 5:
                nextTetro = new TetrominoS(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 6:
            default:
                nextTetro = new TetrominoZ(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
        }
        if(hasStarted)
            print(currentTetro, 0);
    }

    public void clearNextBoard() {
        for (int i = 0; i < NEXT_ROWS; ++i) {
            for (int j = 0; j < NEXT_COLUMNS; ++j) {
                nextBoard[i][j].setFill(Color.TRANSPARENT);
            }
        }
    }

    public void move(KeyCode key, Tetromino t) {
        if(!hasStarted)
            return;
        print(t, 1);
        boolean canMove = true, flag = true;
        switch (key) {
            case DOWN:
                if (t.getMax().getX() < MAIN_ROWS - 1) {
                    for (int i = 0; i < t.getBlock().length; ++i)
                        for (int j = 0; j < t.getBlock()[0].length; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i+1][(int)t.getMin().getY()+j] != 0){
                                    canMove = false;
                                }
                                    
                    if(canMove){
                        t.setMin(t.getMin().add(1, 0));
                        t.setMax(t.getMax().add(1, 0));
                    }

                }
                
                if(!canMove || t.getMax().getX() >= MAIN_ROWS - 1){ // para a peça: mapeia pra board
                    for (int i = 0; i < t.getBlock().length; ++i)
                        for (int j = 0; j < t.getBlock()[0].length; ++j)
                            if (t.getBlock()[i][j])
                                board[(int)t.getMin().getX() + i][(int)t.getMin().getY() + j] = Functions.colorToInt(t.getColor());      
                    createTetromino();
                    printNext();
                    if(deleteRows())
                        flag = false;
                    for(int j = 0; j < MAIN_COLUMNS; j++)
                        if(board[3][j] > 0)
                            gameOver();
                    score += 3;
                    scoreLabel.setText(((Integer)score).toString());
                }
                break;
            case LEFT:
                if (t.getMin().getY() > 0) {
                    for (int i = 0; i < t.getBlock().length; ++i)
                        for (int j = 0; j < t.getBlock()[0].length; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j-1] != 0)
                                    canMove = false;
                    if(canMove){
                        t.setMin(t.getMin().subtract(0, 1));
                        t.setMax(t.getMax().subtract(0, 1));
                        canMove = false;
                    }

                }
                for (int i = 0; i < t.getBlock().length; ++i) {
                    if (t.getBlock()[i][0]) {
                        canMove = false;
                    }
                }
                if(canMove){
                    for (int i = 0; i < t.getBlock().length; ++i)
                        for (int j = 0; j < t.getBlock()[0].length; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j-1] != 0)
                                    canMove = false;
                }
                
                if (canMove) {
                    for (int i = 0; i < t.getBlock().length; ++i) {
                        for (int j = 1; j < t.getBlock()[0].length; ++j) {
                            t.getBlock()[i][j - 1] = t.getBlock()[i][j];
                            t.getBlock()[i][j]=false;
                        }
                    }
                }
                break;
            case RIGHT:
                if (t.getMax().getY() < MAIN_COLUMNS - 1) {
                    for (int i = 0; i < t.getBlock().length; ++i)
                        for (int j = 0; j < t.getBlock()[0].length; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j+1] != 0)
                                    canMove = false;
                    if(canMove){
                        t.setMin(t.getMin().add(0, 1));
                        t.setMax(t.getMax().add(0, 1));
                        canMove = false;
                    }
                }
                for (int i = 0; i < t.getBlock().length; ++i) {
                    if (t.getBlock()[i][3]) {
                        canMove = false;
                    }
                }
                if(canMove){
                    for (int i = 0; i < t.getBlock().length; ++i)
                        for (int j = 0; j < t.getBlock()[0].length; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j+1] != 0)
                                    canMove = false;
                }
                if (canMove) {
                    for (int i = 0; i < t.getBlock().length ; ++i) {
                        for (int j = t.getBlock()[0].length-2;j>=0 ; --j) {
                            t.getBlock()[i][j+1] = t.getBlock()[i][j];
                            t.getBlock()[i][j]=false;
                        }
                    }                 

                }
                break;
            case R: // sentido anti-horário
                currentTetro.setBlock(Functions.rotate(currentTetro,1,board));
                break;
            case T: // sentido horário
                currentTetro.setBlock(Functions.rotate(currentTetro,2,board));
                break;
                
            default:
                break;
        }
        if(flag)
            print(t, 0);
    }

    public void print(Tetromino t, int erase) {

        for (int i = (int) t.getMin().getX(); i <= t.getMax().getX(); ++i) {
            for (int j = (int) t.getMin().getY(); j <= t.getMax().getY(); ++j) {
                int indexX = i - (int) t.getMin().getX();
                int indexY = j - (int) t.getMin().getY();
                if (t.getBlock()[indexX][indexY]) {
                    if(erase == 0)
                        mainBoard[i][j].setFill(t.getColor());
                    else{
                        mainBoard[i][j].setFill(Color.TRANSPARENT);
                        colourLimit();
                    }
                        
                }
            }
        }
    }
    
    public void printNext(){
        clearNextBoard();
        for (int i = 0 ; i < nextTetro.getBlock().length; ++i) {
            for (int j = 0; j < nextTetro.getBlock()[0].length; ++j) {
                if (nextTetro.getBlock()[i][j])
                    nextBoard[i][j].setFill(nextTetro.getColor());       
            }
        }
    }
    
    public boolean deleteRows(){
        boolean flag, flagExit = false;
        int points = 10;
        for(int i = 0; i < MAIN_ROWS; i++){
            flag = true;
            for(int j = 0; j < MAIN_COLUMNS; j++)
                if(board[i][j] == 0)
                    flag = false;
            if(flag){
                score += points;
                points *= 2;
                for(int j = 0; j < MAIN_COLUMNS; j++)
                    board[i][j] = 0;
                for(int k = i; k > 0; k--)
                    for(int j = 0; j < MAIN_COLUMNS; j++)
                        board[k][j] = board[k-1][j];
                printBoard(i);
                flagExit = true;
            }
        }
        return flagExit;
        
    }
    
    public void printBoard(int i){
        System.out.println(score);
        for(int k = i; k >= 0; k--)
            for(int j = 0; j < MAIN_COLUMNS; j++)
                mainBoard[k][j].setFill(Functions.intToColor(board[k][j]));
        colourLimit();
    }
    
    public void colourLimit(){
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < MAIN_COLUMNS; j++)
                mainBoard[i][j].setFill(Color.LIGHTGRAY);
    }
    
    public void gameOver(){
        System.exit(0);
    }
            
}
