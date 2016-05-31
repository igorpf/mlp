/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.oo.entities;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 *
 * @author igor
 */
public abstract class Tetromino {

    public static final int SIZE = 4;

    private Color color;

    private boolean[][] block;

    private Point2D min;

    private Point2D max;

    public Tetromino() {
        this.color = Color.BLACK;
        this.block = new boolean[][]{{false}, {false}};
        this.min = new Point2D(0,0);
        this.max = new Point2D(3,3);
    }

    public Tetromino(Color cor, boolean[][] bloco) {
        this.color = cor;
        this.block = bloco;
        this.min = new Point2D(0,0);
        this.max = new Point2D(3,3);
    }
    
    public Tetromino(Color cor, boolean[][] bloco, Point2D min) {
        this.color = cor;
        this.block = bloco;
        this.min = min;
        this.max = new Point2D(min.getX(),min.getY());
        this.max.add(SIZE-1, SIZE-1);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean[][] getBlock() {
        return block;
    }

    public void setBlock(boolean[][] block) {
        this.block = block;
    }

    public Point2D getMin() {
        return min;
    }

    public void setMin(Point2D min) {
        this.min = min;
    }

    public Point2D getMax() {
        return max;
    }

    public void setMax(Point2D max) {
        this.max = max;
    }
}
