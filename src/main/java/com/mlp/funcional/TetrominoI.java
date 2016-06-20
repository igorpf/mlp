/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlp.funcional;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;

/**
 *
 * @author igor
 */
public class TetrominoI extends Tetromino{
    /** This is the template for the tetromino. 
     *  
     */
    public static final List<Boolean> 
            FORM = Arrays.asList(false, true, false, false,
                                 false, true, false, false,
                                 false, true, false, false,
                                 false, true, false, false);
    public TetrominoI() {
        super(Color.STEELBLUE,FORM);
    }
    public TetrominoI(Point2D min) {
        super(Color.STEELBLUE,FORM,min);
    }
}
