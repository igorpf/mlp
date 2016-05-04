/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.oo.entidades;

import javafx.scene.paint.Color;

/**
 *
 * @author igor
 */
public abstract class Peca {

    public static final int SIZE = 4;

    private Color cor;

    private boolean[][] bloco;

    private Ponto min;

    private Ponto max;

    public Peca() {
        this.cor = Color.BLACK;
        this.bloco = new boolean[][]{{false}, {false}};
        this.min = new Ponto(0,0);
        this.min = new Ponto(3,3);
    }

    public Peca(Color cor, boolean[][] bloco) {
        this.cor = cor;
        this.bloco = bloco;
        this.min = new Ponto(0,0);
        this.min = new Ponto(3,3);
    }
    
    public Peca(Color cor, boolean[][] bloco, Ponto min) {
        this.cor = cor;
        this.bloco = bloco;
        this.min = min;
        this.max = new Ponto(min);
        this.max.setX(this.min.getX()+Peca.SIZE-1);
        this.max.setY(this.min.getY()+Peca.SIZE-1);
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public boolean[][] getBloco() {
        return bloco;
    }

    public void setBloco(boolean[][] bloco) {
        this.bloco = bloco;
    }

    public Ponto getMin() {
        return min;
    }

    public void setMin(Ponto min) {
        this.min = min;
    }

    public Ponto getMax() {
        return max;
    }

    public void setMax(Ponto max) {
        this.max = max;
    }
}
