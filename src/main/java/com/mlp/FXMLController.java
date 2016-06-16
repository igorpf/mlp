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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FXMLController implements Initializable {

    @FXML
    private GridPane mainGrid;

    @FXML
    private GridPane nextGrid;

    private Rectangle[][] mainBoard;

    private Rectangle[][] nextBoard;

    private Timeline timeline;

    List<Tetromino> tetrominoes;

    public int MAIN_COLUMNS, MAIN_ROWS;
    public int NEXT_COLUMNS, NEXT_ROWS;

    @FXML
    public void newGame(ActionEvent event) {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> move(KeyCode.RIGHT, tetrominoes.get(0))
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tetrominoes = new ArrayList<>();
        tetrominoes.add(new TetrominoT());
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
    }

    public void move(KeyCode key, Tetromino t) {
        clearBoards();
        switch (key) {
            case DOWN:
                if (t.getMax().getX() < MAIN_ROWS - 1) {
                    t.setMin(t.getMin().add(1, 0));
                    t.setMax(t.getMax().add(1, 0));
                }
                break;
            case LEFT:
                if (t.getMax().getY() > 0) {
                    t.setMin(t.getMin().subtract(0, 1));
                    t.setMax(t.getMax().subtract(0, 1));
                }
                break;
            case RIGHT:
                if (t.getMax().getY() < MAIN_COLUMNS - 1) {
                    t.setMin(t.getMin().add(0, 1));
                    t.setMax(t.getMax().add(0, 1));
                }
                break;
            default:
                break;

        }
        print(t);
    }

    public void print(Tetromino t) {
        for (int i = (int) t.getMin().getX(); i <= t.getMax().getX(); ++i) {
            for (int j = (int) t.getMin().getY(); j <= t.getMax().getY(); ++j) {
                int indexX = i - (int) t.getMin().getX();
                int indexY = j - (int) t.getMin().getY();
                if (t.getBlock()[indexX][indexY]) {
                    mainBoard[i][j].setFill(t.getColor());
                }
            }
        }
    }
}
