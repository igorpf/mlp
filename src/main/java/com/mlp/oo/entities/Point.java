/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.oo.entities;

/**
 *
 * @author igor
 */
public class Point {

    private int x;
    private int y;

    public Point() {
    }

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isInsideBox(final Point boxMin, final Point boxMax) {
        return (this.x > boxMin.getX() && this.y > boxMin.getY()
                && this.x < boxMax.getX() && this.y < boxMax.getY());
    }
}
