package com.mlp.oo.functions;

import com.mlp.oo.entities.Tetromino;
import javafx.scene.paint.Color;

public class Functions {
    
    public static Color intToColor(int n){
        Color c = Color.TRANSPARENT;
        switch(n){
            case 0:
                c = Color.WHITE;
                break;
            case 1:
                c = Color.STEELBLUE;
                break;
            case 2:
                c = Color.HOTPINK;
                break;
            case 3:
                c = Color.ORANGE;
                break;
            case 4:
                c = Color.GOLD;
                break;
            case 5:
                c = Color.RED;
                break;
            case 6:
                c = Color.DARKMAGENTA;
                break;
            case 7:
                c = Color.DARKGREEN;
                break;    
        }
        return c;
    }
    
    public static int colorToInt(Color c){
        if(c.equals(Color.STEELBLUE))
            return 1;
        if(c.equals(Color.HOTPINK))
            return 2;
        if(c.equals(Color.ORANGE))
            return 3;
        if(c.equals(Color.GOLD))
            return 4;
        if(c.equals(Color.RED))
            return 5;
        if(c.equals(Color.DARKMAGENTA))
            return 6;
        if(c.equals(Color.DARKGREEN))
            return 7;
        return 0;
    }
            
    public static boolean[][] rotate(Tetromino t, int way, int[][] map){
        int i, j, counter = 0;
        boolean canRotate = true, canMoveDown = true, flag;
        final int rows = t.getBlock().length;
        final int columns = t.getBlock()[0].length;
        boolean[][] matrix = new boolean[rows][columns];
        boolean[][] matrixAux = new boolean[rows][columns];
        boolean[][] matrixRotated = new boolean[rows][columns];
        int minX = columns - 1, minY = rows - 1, maxX = 0, maxY = 0;
        
        // inicializa a matriz de retorno
        for(i = 0; i < rows; i++)
            for(j = 0; j < columns; j++){
                matrixRotated[i][j] = false;
                matrix[i][j] = t.getBlock()[i][j];
            }
        
        if(way == 1){ // sentido anti-horário
            // obtém a matriz transposta
            for(i = 0; i < rows; i++)
                for(j = 0; j < columns; j++)
                    matrixAux[j][i] = matrix[i][j];
            
            // copia matriz auxiliar inteira
            for(i = 0; i < rows; i++)
                for(j = 0; j < columns; j++)
                    matrix[i][j] = matrixAux[i][j];
        }
        
        // determina a submatriz da matriz 4x4 que contém a peça
        for(i = 0; i < rows; i++)
            for(j = 0; j < columns; j++){
                if(matrix[i][j]){
                    if(j > maxX)
                        maxX = j;
                    if(i > maxY)
                        maxY = i;
                    if(j < minX)
                        minX = j;
                    if(i < minY)
                        minY = i;
               }
            }
        
        // zera a matriz auxiliar
        for(i = 0; i < rows; i++)
            for(j = 0; j < columns; j++)
                matrixAux[i][j] = false;
        
        // reflete verticalmente essa submatriz
        for(i = minY; i <= maxY; i++){  
            for(j = minX; j <= maxX; j++)
                matrixAux[i][j] = matrix[maxY - counter][j];
            counter++;
        }
        
        if(way == 2){ // sentido horário
            // obtém a matriz transposta
            for(i = minY; i <= maxY; i++)
                for(j = minX; j <= maxX; j++)
                    matrixRotated[j][i] = matrixAux[i][j];
        }
        else
            matrixRotated = matrixAux;
        
        // desce a peça dentro do bounding box
        while(canMoveDown){
            canMoveDown = false;
            flag = true;
            for(j = 0; j < 4; j++)
                if(matrixRotated[3][j])
                    flag = false;
            if(flag){
                for(i = 3; i > 0; i--)
                    for(j = 0; j < 4; j++)
                        matrixRotated[i][j] = matrixRotated[i-1][j];
                
                for(j = 0; j < 4; j++)
                    matrixRotated[0][j] = false;
                        
                canMoveDown = true;
            }
        }
        
        // determina se pode virar
        for(i = 0; i < 4; i++)
            for(j = 0; j < 4; j++)
                if(matrixRotated[i][j])
                    if (map[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j] != 0)
                        canRotate = false;
                
        if(!canRotate)
            return t.getBlock();
        
        return matrixRotated;
    }
}
