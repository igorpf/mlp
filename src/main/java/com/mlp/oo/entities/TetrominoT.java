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
public class TetrominoT extends Tetromino{
    /** This is the template for the tetromino. 
     *  To change the tetromino form, use the method setForm(form)
     */
    public static final boolean [][]FORM = {{false, false, false,false},
                                            {false, true, true, true},
                                            {false, false, true, false},
                                            {false, false, false,  false}};
    public TetrominoT() {
        super(Color.DARKMAGENTA,FORM);
    }
}