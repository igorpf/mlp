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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
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
public class FXMLControllerFuncional implements Initializable {

    @FXML
    private GridPane mainGrid;

    @FXML
    private GridPane nextGrid;
    @FXML
    private AnchorPane mainPage;
    @FXML
    private Label scoreLabel;

    private List<Rectangle> mainBoard;

    private List<Rectangle> nextBoard;

    List<Integer> board;
    

    private boolean hasStarted = false;

    private Tetromino currentTetro;
    private Tetromino nextTetro;

    public int MAIN_COLUMNS, MAIN_ROWS;

    public int NEXT_COLUMNS, NEXT_ROWS;

    private int index;
    
    private int score;

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
        IntStream.range(0, MAIN_ROWS * MAIN_COLUMNS).forEach(n -> {
            Rectangle r = new Rectangle(25, 25);
            r.setFill(Color.TRANSPARENT);
            mainBoard.add(r);
            mainGrid.add(r, n % MAIN_COLUMNS, n / MAIN_COLUMNS);
        });
        NEXT_COLUMNS = nextGrid.getColumnConstraints().size();
        NEXT_ROWS = nextGrid.getRowConstraints().size();
        nextBoard = new ArrayList<>();
        IntStream.range(0, NEXT_ROWS * NEXT_COLUMNS).forEach(n -> {
            Rectangle r = new Rectangle(25, 25);
            r.setFill(Color.TRANSPARENT);
            nextBoard.add(r);
            nextGrid.add(r, n % NEXT_COLUMNS, n / NEXT_COLUMNS);
        });
        board = new ArrayList<>();
        IntStream.range(0, MAIN_ROWS * MAIN_COLUMNS).forEach(n -> {
            board.add(0);
        });
        mainPage.setOnKeyPressed((KeyEvent event) -> {
            move(event.getCode(), currentTetro);
        });
        currentTetro = new TetrominoZ(new Point2D(0, 4));
    }

    public void move(KeyCode key, Tetromino t) {
        if (!hasStarted) {
            return;
        }
        Function<Point2D, Point2D> f;
        Function<List<Boolean>, List<Boolean>> fList;
        boolean canMove = true, flag = true, stop = false;
        Point2D old;
        List<Integer> indices;
        print(t, true);
        switch (key) {
            case DOWN:
                
                f = (p) -> {
                    return ((p.getX() + 1) < MAIN_ROWS) ? p.add(1, 0) : p;
                };
                old = new Point2D(t.max.getX(), t.max.getY());
                t.max = f.apply(t.max);
                t.min = (old.equals(t.max)) ? t.min : f.apply(t.min);
                if((old.equals(t.max)) || test(t.min)){
                    printBoard();
                    createTetro();
                    Function<Integer,Integer> s;
                    s = (i) -> i+score;
                    score = s.apply(7);
                    scoreLabel.setText(((Integer)score).toString());
                }
                break;
            case LEFT:
                //get the indices of the leftmost column
                indices=IntStream.range(0, t.block.size())
                                 .boxed()
                                 .filter(b -> b % Tetromino.SIZE == 0)
                                 .collect(Collectors.toList());
                if (indices.stream().map(i->t.block.get(i)).allMatch(p -> p == false)) {
                    index = 0;
                    fList = (l) -> {
                        return l.stream()
                                .map(e -> {
                                    return (index++ % Tetromino.SIZE == (Tetromino.SIZE - 1)) ? false
                                            : l.get(index);
                                })
                                .collect(Collectors.toList());
                    };
                    t.block = fList.apply(t.block);
                } else { //não moveu dentro do bounding box, então move o ponto
                    f = (p) -> {
                        return ((p.getY() - 1) >= 0) ? p.subtract(0, 1) : p;
                    };
                    old = new Point2D(t.min.getX(), t.min.getY());
                    t.min = f.apply(t.min);
                    t.max = (old.equals(t.min)) ? t.max : f.apply(t.max);
                }
                break;
            case RIGHT:
                //get the indices of the rightmost column
                indices=IntStream.range(0, t.block.size())
                                 .boxed()
                                 .filter(b -> b % Tetromino.SIZE == (Tetromino.SIZE - 1))
                                 .collect(Collectors.toList());
                if (indices.stream().map(i->t.block.get(i)).allMatch(p -> p == false)) {
                    index = 0;
                    fList = (l) -> {
                        return l.stream()
                                .map(e -> {
                                    return (index++ % Tetromino.SIZE == 0) ? false
                                            : l.get(index-2);
                                })
                                .collect(Collectors.toList());
                    };
                    t.block = fList.apply(t.block);
                } else {
                    f = (p) -> {
                        return ((p.getY() + 1) < MAIN_COLUMNS) ? p.add(0, 1) : p;
                    };
                    old = new Point2D(t.max.getX(), t.max.getY());
                    t.max = f.apply(t.max);
                    t.min = (old.equals(t.max)) ? t.min : f.apply(t.min);
                }
                break;
            case R:
                java.util.Collections.reverse(t.block);
                break;
            case T: // sentido horário
                break;

            default:
                break;
        }
        print(t, false);
    }

    public void print(Tetromino t, boolean clear) {
        Point2D min = t.min, max = t.max;
        mainBoard = mainBoard.stream().map(r -> {
            int index = mainBoard.indexOf(r);
            int x = index / MAIN_COLUMNS, y = index % MAIN_COLUMNS;
            int dxMin = (int) (x - min.getX()), dxMax = (int) (x - max.getX());
            int dyMin = (int) (y - min.getY()), dyMax = (int) (y - max.getY());
            if (dxMin >= 0 && dxMax <= 0 && dyMin >= 0 && dyMax <= 0// está dentro do bounding box
                    && t.block.get(dxMin * Tetromino.SIZE + dyMin)) {
                r.setFill((!clear)?t.color: Color.TRANSPARENT);
                board.set(index, (clear)?0:Functions.colorToInt(t.color));
                return r;
            } else {
                return r;
            }
        }).collect(Collectors.toList());
    }
    
    public boolean test(Point2D min){
        Point2D max = new Point2D(min.getX()+3, min.getY()+3);
        List<Integer> indices;
        board.set(0,0);
        //indices=IntStream.range(0, currentTetro.block.size());

        //indices.stream().map(i->currentTetro.block.get(i)).anyMatch(p -> p == true);
        IntStream.range(0, MAIN_ROWS * MAIN_COLUMNS).forEach(index -> {
            int x = index / MAIN_COLUMNS, y = index % MAIN_COLUMNS;
            int dxMin = (int) (x - min.getX()), dxMax = (int) (x - max.getX());
            int dyMin = (int) (y - min.getY()), dyMax = (int) (y - max.getY());
            if (index <= 4 * MAIN_COLUMNS && board.get(index) > 0)
                System.exit(0);
            if (dxMin >= 0 && dxMax <= 0 && dyMin >= 0 && dyMax <= 0// está dentro do bounding box
                    && currentTetro.block.get(dxMin * Tetromino.SIZE + dyMin) && 
                    index < 220 - MAIN_COLUMNS && board.get(index + MAIN_COLUMNS) > 0)
                board.set(0,-1);
        });
        if(board.get(0) == -1)
            return true;
        else
            return false;
    }
    
    public void printBoard(){
        int i = 0;
        mainBoard = mainBoard.stream().map(r -> {
            r.setFill(Functions.intToColor(board.get(mainBoard.indexOf(r))));
            return r;
        }).collect(Collectors.toList());
    }

    public void clearBoard() {
        mainBoard = mainBoard.stream().map(r -> {
            r.setFill(Color.TRANSPARENT);
            return r;
        }).collect(Collectors.toList());
    }
    
    public void createTetro(){
        switch(random()){
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
    
    public int random(){
        return (int) (Math.random() * 7.0);
    }
    
}
