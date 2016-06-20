/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.funcional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author igor
 */
public abstract class Tetromino {

    public static final int SIZE = 4;

    public Color color;

    public List<Boolean> block;

    public Point2D min;

    public Point2D max;

    public Tetromino() {
        this.color = Color.BLACK;
        this.block = new ArrayList<>();
        IntStream.range(0, SIZE*SIZE).forEach(n->{
            this.block.add(Boolean.FALSE);
        });
        this.min = new Point2D(0,0);
        this.max = new Point2D(SIZE-1,SIZE-1);
    }
    public Tetromino(Color c, List<Boolean> l) {
        this.color = c;
        this.block = l;
        this.min = new Point2D(0,0);
        this.max = new Point2D(SIZE-1,SIZE-1);
    }
    public Tetromino(Color cor, List<Boolean> l, Point2D min) {
        this.color = cor;
        this.block = l;
        this.min = min;
        this.max = new Point2D(min.getX()+SIZE-1,min.getY()+SIZE-1);
    }
}
