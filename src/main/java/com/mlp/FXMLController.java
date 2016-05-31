package com.mlp;

import com.mlp.oo.entities.Tetromino;
import com.mlp.oo.entities.TetrominoO;
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

    @FXML
    public void newGame(ActionEvent event) {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> move(KeyCode.DOWN)
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tetrominoes = new ArrayList<>();
        tetrominoes.add(new TetrominoO());
        int columns = mainGrid.getColumnConstraints().size();
        int rows = mainGrid.getRowConstraints().size();
        mainBoard = new Rectangle[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                mainBoard[i][j] = new Rectangle(25, 25);
                mainBoard[i][j].setFill(Color.TRANSPARENT);
                mainGrid.add(mainBoard[i][j], j, i);
            }
        }
        columns = nextGrid.getColumnConstraints().size();
        rows = nextGrid.getRowConstraints().size();
        nextBoard = new Rectangle[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                nextBoard[i][j] = new Rectangle(25, 25);
                nextBoard[i][j].setFill(Color.TRANSPARENT);
                nextGrid.add(nextBoard[i][j], j, i);
            }
        }

    }

    public void clearBoards() {
        int columns = mainGrid.getColumnConstraints().size();
        int rows = mainGrid.getRowConstraints().size();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                mainBoard[i][j].setFill(Color.TRANSPARENT);
            }
        }
        columns = nextGrid.getColumnConstraints().size();
        rows = nextGrid.getRowConstraints().size();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                nextBoard[i][j].setFill(Color.TRANSPARENT);
            }
        }
    }

    public void move(KeyCode key) {
        clearBoards();
        for (Tetromino t : tetrominoes) {
            switch (key) {
                case DOWN:
                    t.setMin(t.getMin().add(1, 0));
                    t.setMax(t.getMax().add(1, 0));
                    if (t.getMax().getX() > 20) {
                        t.setMin(Point2D.ZERO);
                        t.setMax(new Point2D(3, 3));
                    }
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    break;
                default:
                    break;
            }
            for (int i = (int) t.getMin().getX(); i <= t.getMax().getX(); ++i) {
                for (int j = (int) t.getMin().getY(); j <= t.getMax().getY(); ++j) {
                    mainBoard[i][j].setFill(t.getColor());
                }
            }
        }
    }
}
