/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.oo.entities;

import javafx.scene.paint.Color;
import javafx.geometry.Point2D;

/**
 *
 * @author igor
 */
public class TetrominoO extends Tetromino{
    /** This is the template for the tetromino. 
     *  To change the tetromino form, use the method setForm(form)
     */
    public static final boolean [][]FORM = {{false, false, false,false},
                                            {false, false, false,false},
                                            {false, true, true,false},
                                            {false, true, true,false}};
    public TetrominoO() {
        super(Color.GOLD,FORM);
    }
    public TetrominoO(Point2D min) {
        super(Color.GOLD,FORM,min);
    }
}
