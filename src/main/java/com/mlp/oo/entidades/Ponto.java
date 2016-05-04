/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.oo.entidades;

/**
 *
 * @author igor
 */
public class Ponto {

    private int x;
    private int y;

    public Ponto() {
    }

    public Ponto(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Ponto(Ponto p) {
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

    public boolean isInsideBox(final Ponto boxMin, final Ponto boxMax) {
        return (this.x > boxMin.getX() && this.y > boxMin.getY()
                && this.x < boxMax.getX() && this.y < boxMax.getY());
    }
}
