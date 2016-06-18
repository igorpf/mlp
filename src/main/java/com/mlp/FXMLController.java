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
import funcoes.Funcoes;

public class FXMLController implements Initializable {

    @FXML
    private GridPane mainGrid;

    @FXML
    private GridPane nextGrid;
    @FXML
    private AnchorPane mainPage;

    private Rectangle[][] mainBoard;

    private Rectangle[][] nextBoard;
    
    private int[][] board;

    private Timeline timeline;

    //List<Tetromino> tetrominoes;
    Tetromino currentTetro;
    Tetromino nextTetro;

    public int MAIN_COLUMNS, MAIN_ROWS;
    public int NEXT_COLUMNS, NEXT_ROWS;

    @FXML
    public void newGame(ActionEvent event) {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> move(KeyCode.DOWN, currentTetro)
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainPage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                move(event.getCode(), currentTetro);
            }

        });
        //tetrominoes = new ArrayList<>();


//        tetrominoes.add();
        MAIN_COLUMNS = mainGrid.getColumnConstraints().size();
        MAIN_ROWS = mainGrid.getRowConstraints().size();
        create();
        mainBoard = new Rectangle[MAIN_ROWS][MAIN_COLUMNS];
        for (int i = 0; i < MAIN_ROWS; ++i) {
            for (int j = 0; j < MAIN_COLUMNS; ++j) {
                mainBoard[i][j] = new Rectangle(25, 25);
                mainBoard[i][j].setFill(Color.TRANSPARENT);
                mainGrid.add(mainBoard[i][j], j, i);
            }
        }
        NEXT_COLUMNS = nextGrid.getColumnConstraints().size();
        NEXT_ROWS = nextGrid.getRowConstraints().size();
        nextBoard = new Rectangle[NEXT_ROWS][NEXT_COLUMNS];
        for (int i = 0; i < NEXT_ROWS; ++i) {
            for (int j = 0; j < NEXT_COLUMNS; ++j) {
                nextBoard[i][j] = new Rectangle(25, 25);
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

    }
    
    public void create(){
        int n = (int) (Math.random() * 7.0);
        switch(n){
            case 0:
                currentTetro = new TetrominoT(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 1:
                currentTetro = new TetrominoI(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 2:
                currentTetro = new TetrominoJ(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 3:
                currentTetro = new TetrominoL(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 4:
                currentTetro = new TetrominoO(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 5:
                currentTetro = new TetrominoS(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
                break;
            case 6:
            default:
                currentTetro = new TetrominoZ(new Point2D(0, MAIN_COLUMNS/2.0 - 2));
        }
    }

    public void clearBoards() {
        for (int i = 0; i < MAIN_ROWS; ++i) {
            for (int j = 0; j < MAIN_COLUMNS; ++j) {
                mainBoard[i][j].setFill(Color.TRANSPARENT);
            }
        }
        for (int i = 0; i < NEXT_ROWS; ++i) {
            for (int j = 0; j < NEXT_COLUMNS; ++j) {
                nextBoard[i][j].setFill(Color.TRANSPARENT);
            }
        }
        for (int i = 0; i < MAIN_ROWS; ++i) {
            for (int j = 0; j < MAIN_COLUMNS; ++j) {
                board[i][j] = 0;
            }
        }
    }

    public void move(KeyCode key, Tetromino t) {
        //clearBoards();
        print(t, 0);
        boolean canMove = true;
        switch (key) {
            case DOWN:
                if (t.getMax().getX() < MAIN_ROWS - 1) {
                    for (int i = 0; i < 4; ++i)
                        for (int j = 0; j < 4; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i+1][(int)t.getMin().getY()+j] != 0){
                                    canMove = false;
                                    //System.out.println(i + " " + j + "board: " + ((int)t.getMin().getX()+i+1) + ((int)t.getMin().getY()+j));

                                }
                                    
                    if(canMove){
                        t.setMin(t.getMin().add(1, 0));
                        t.setMax(t.getMax().add(1, 0));
                    }

                }
                
                if(!canMove || t.getMax().getX() >= MAIN_ROWS - 1){ // para a peça: mapeia pra board
                    for (int i = 0; i < 4; ++i)
                        for (int j = 0; j < 4; ++j)
                            if (t.getBlock()[i][j])
                                board[(int)t.getMin().getX() + i][(int)t.getMin().getY() + j] = 1;          
                    print(t, 0);

                    create();
                }
                break;
            case LEFT:
                if (t.getMin().getY() > 0) {
                    for (int i = 0; i < 4; ++i)
                        for (int j = 0; j < 4; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j-1] != 0)
                                    canMove = false;
                    if(canMove){
                        t.setMin(t.getMin().subtract(0, 1));
                        t.setMax(t.getMax().subtract(0, 1));
                        canMove = false;
                    }

                }
                for (int i = 0; i < t.getBlock()[0].length; ++i) {
                    if (t.getBlock()[i][0]) {
                        canMove = false;
                    }
                }
                if(canMove){
                    for (int i = 0; i < 4; ++i)
                        for (int j = 0; j < 4; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j-1] != 0)
                                    canMove = false;
                }
                
                if (canMove) {
                    for (int i = 0; i < t.getBlock()[0].length; ++i) {
                        for (int j = 1; j < t.getBlock()[0].length; ++j) {
                            t.getBlock()[i][j - 1] = t.getBlock()[i][j];
                            t.getBlock()[i][j]=false;
                        }
                    }
                }
                break;
            case RIGHT:
                if (t.getMax().getY() < MAIN_COLUMNS - 1) {
                    for (int i = 0; i < 4; ++i)
                        for (int j = 0; j < 4; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j+1] != 0)
                                    canMove = false;
                    if(canMove){
                        t.setMin(t.getMin().add(0, 1));
                        t.setMax(t.getMax().add(0, 1));
                        canMove = false;
                    }
                }
                for (int i = 0; i < t.getBlock()[0].length; ++i) {
                    if (t.getBlock()[i][3]) {
                        canMove = false;
                    }
                }
                if(canMove){
                    for (int i = 0; i < 4; ++i)
                        for (int j = 0; j < 4; ++j)
                            if(t.getBlock()[i][j])
                                if(board[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j+1] != 0)
                                    canMove = false;
                }
                if (canMove) {
                    for (int i = 0; i <t.getBlock()[0].length ; ++i) {
                        for (int j = t.getBlock()[0].length-2;j>=0 ; --j) {
                            t.getBlock()[i][j+1] = t.getBlock()[i][j];
                            t.getBlock()[i][j]=false;
                        }
                    }                 

                }
                break;
            case R: // sentido anti-horário
                if(Funcoes.rotaciona(currentTetro,2,board) != null)
                    currentTetro.setBlock(Funcoes.rotaciona(currentTetro,1,board));
                break;
            case T: // sentido horário
                if(Funcoes.rotaciona(currentTetro,2,board) != null)
                    currentTetro.setBlock(Funcoes.rotaciona(currentTetro,2,board));
                break;
                
            default:
                break;
        }
        
        print(t, 1);

        /*for (Tetromino te : tetrominoes) {
            print(te, 1);
        }*/
    }

    public void print(Tetromino t, int apagar) {

        for (int i = (int) t.getMin().getX(); i <= t.getMax().getX(); ++i) {
            for (int j = (int) t.getMin().getY(); j <= t.getMax().getY(); ++j) {
                int indexX = i - (int) t.getMin().getX();
                int indexY = j - (int) t.getMin().getY();
                if (t.getBlock()[indexX][indexY]) {
                    if(apagar == 1){
                        mainBoard[i][j].setFill(t.getColor());
                        board[i][j] = 1;
                    }
                    else{
                        mainBoard[i][j].setFill(Color.TRANSPARENT);
                        board[i][j] = 0;
                    }
                }
            }
        }
    }
}
