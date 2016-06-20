package com.mlp.oo.functions;

import com.mlp.oo.entities.Tetromino;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;

public class Functions {
    
    public static Color intToColor(int n){
        Map<Integer, Color> map= new HashMap<>();
        map.put(0, Color.TRANSPARENT);
        map.put(1, Color.STEELBLUE);
        map.put(2, Color.HOTPINK);
        map.put(3, Color.ORANGE);
        map.put(4, Color.GOLD);
        map.put(5, Color.RED);
        map.put(6, Color.DARKMAGENTA);
        map.put(7, Color.DARKGREEN);
        return map.get(n);
    }
    
    public static int colorToInt(Color c){
        Map<Color, Integer> map= new HashMap<>();
        map.put(Color.TRANSPARENT,0);
        map.put(Color.STEELBLUE, 1);
        map.put(Color.HOTPINK, 2);
        map.put(Color.ORANGE, 3);
        map.put(Color.GOLD, 4);
        map.put(Color.RED, 5);
        map.put(Color.DARKMAGENTA, 6);
        map.put(Color.DARKGREEN, 7);
        return map.get(c);
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
            for(j = 0; j < columns; j++)
                if(matrixRotated[rows-1][j])
                    flag = false;
            if(flag){
                for(i = rows-1; i > 0; i--)
                    for(j = 0; j < columns; j++)
                        matrixRotated[i][j] = matrixRotated[i-1][j];
                
                for(j = 0; j < columns; j++)
                    matrixRotated[0][j] = false;
                        
                canMoveDown = true;
            }
        }
        
        // determina se pode virar
        for(i = 0; i < rows-1; i++)
            for(j = 0; j < columns-1; j++)
                if(matrixRotated[i][j])
                    if (map[(int)t.getMin().getX()+i][(int)t.getMin().getY()+j] != 0)
                        canRotate = false;
                
        if(!canRotate)
            return t.getBlock();
        
        return matrixRotated;
    }
}
