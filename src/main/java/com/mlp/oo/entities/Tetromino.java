/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.oo.entities;

import javafx.scene.paint.Color;

/**
 *
 * @author igor
 */
public abstract class Tetromino {

    public static final int SIZE = 4;

    private Color color;

    private boolean[][] block;

    private Point min;

    private Point max;

    public Tetromino() {
        this.color = Color.BLACK;
        this.block = new boolean[][]{{false}, {false}};
        this.min = new Point(0,0);
        this.min = new Point(3,3);
    }

    public Tetromino(Color cor, boolean[][] bloco) {
        this.color = cor;
        this.block = bloco;
        this.min = new Point(0,0);
        this.min = new Point(3,3);
    }
    
    public Tetromino(Color cor, boolean[][] bloco, Point min) {
        this.color = cor;
        this.block = bloco;
        this.min = min;
        this.max = new Point(min);
        this.max.setX(this.min.getX()+Tetromino.SIZE-1);
        this.max.setY(this.min.getY()+Tetromino.SIZE-1);
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

    public Point getMin() {
        return min;
    }

    public void setMin(Point min) {
        this.min = min;
    }

    public Point getMax() {
        return max;
    }

    public void setMax(Point max) {
        this.max = max;
    }
}
