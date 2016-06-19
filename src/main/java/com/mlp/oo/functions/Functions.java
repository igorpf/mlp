package com.mlp.oo.functions;

import com.mlp.oo.entities.Tetromino;
import com.mlp.oo.entities.TetrominoJ;

public class Functions {
            
    public static boolean[][] rotate(Tetromino peca, int sentido, int[][] map){
        int i, j, counter = 0;
        boolean canRotate = true, canMoveDown = true, flag = true;
        Tetromino tetroAux = new TetrominoJ();
        tetroAux.setBlock(peca.getBlock());
        tetroAux.setMax(peca.getMax());
        tetroAux.setMin(peca.getMin());
        boolean[][] matrix = tetroAux.getBlock();
        int rows = matrix.length;
        int columns = matrix[0].length;
        boolean matrixAux[][] = new boolean[rows][columns];
        boolean[][] matrixRotated = new boolean[rows][columns];
        int minX = columns - 1, minY = rows - 1, maxX = 0, maxY = 0;
        
        // inicializa a matriz de retorno
        for(i = 0; i < rows; i++)
            for(j = 0; j < columns; j++){
                matrixRotated[i][j] = false;
            }
        
        if(sentido == 1){ // sentido anti-horário
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
        
        if(sentido == 2){ // sentido horário
            // obtém a matriz transposta
            for(i = minY; i <= maxY; i++)
                for(j = minX; j <= maxX; j++)
                    matrixRotated[j][i] = matrixAux[i][j];
        }
        else
            matrixRotated = matrixAux;
        
        tetroAux.setBlock(matrixRotated);
        
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
        for(i = 0; i < 4; i++)
            for(j = 0; j < 4; j++)
                if(matrixRotated[i][j])
                    if (map[(int)peca.getMin().getX()+i][(int)peca.getMin().getY()+j] != 0)
                        canRotate = false;
                
        if(!canRotate)
            return null;
        
        return matrixRotated;
    }
}
