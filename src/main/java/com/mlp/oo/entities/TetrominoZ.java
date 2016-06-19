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
public class TetrominoZ extends Tetromino{
    /** This is the template for the tetromino. 
     *  
     */
    public static final boolean [][]FORM = {{false, false, false,false},
                                            {false, false, false,false},
                                            {false, true, true,false},
                                            {false, false, true,true}};
    public TetrominoZ() {
        super(Color.DARKGREEN,FORM);
    }
    public TetrominoZ(Point2D min) {
        super(Color.DARKGREEN,FORM,min);
    }
    
}
