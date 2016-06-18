package funcoes;

import com.mlp.oo.entities.Tetromino;
import com.mlp.oo.entities.TetrominoJ;
import javafx.geometry.Point2D;
import java.util.List;
import java.util.ArrayList;

public class Funcoes {
    
    /*public static boolean colisao(Tetromino peca1, Tetromino peca2){
        boolean ret = false;
        List<Point2D> lista_ocupacoes = new ArrayList();
        int i, j, k;
        int aux[] = new int[2];
        int aux2[] = new int[2];
        boolean bloco[][] = peca2.getBlock();
        
        // guarda todos pontos do espaço de jogo que uma das peças ocupa
        for(i = 0; i < 4; i++){
            for(j = 0; j < 4; j++)
                if(bloco[i][j]){
                    aux[0] = j + (int)peca2.getMin().getX();
                    aux[1] = i + (int)peca2.getMin().getY();
                    System.out.println(aux[0]);
                    System.out.println(aux[1]);
                    Point2D p = new Point2D(aux[0], aux[1]);
                    lista_ocupacoes.add(p);
                }  
        }
        
        // testa se algum ponto ocupado pela outra peça está na lista
        bloco = peca1.getBlock();
        for(i = 0; i < 4; i++)
            for(j = 0; j < 4; j++)
                if(bloco[i][j]){
                    aux[0] = j + (int)peca1.getMin().getX();
                    aux[1] = i + (int)peca1.getMin().getY();
                    System.out.println(aux[0]);
                    System.out.println(aux[1]);
                    for(k = 0; k < lista_ocupacoes.size(); k++)
                        if(aux[0] == lista_ocupacoes.get(k).getX())
                            if(aux[1] == lista_ocupacoes.get(k).getY())
                                ret = true;
                }
        return ret;
    }*/
            
    public static boolean[][] rotaciona(Tetromino peca, int sentido, int[][] mapa){
        int i, j, contador = 0;
        boolean pode_virar = true, pode_descer = true, flag = true;
        Tetromino peca_aux = new TetrominoJ();
        peca_aux.setBlock(peca.getBlock());
        peca_aux.setMax(peca.getMax());
        peca_aux.setMin(peca.getMin());
        boolean[][] matriz = peca_aux.getBlock();
        int nro_linhas = matriz.length;
        int nro_colunas = matriz[0].length;
        boolean matriz_aux[][] = new boolean[nro_linhas][nro_colunas];
        boolean matriz_virada[][] = new boolean[nro_linhas][nro_colunas];
        int min_x = nro_colunas - 1, min_y = nro_linhas - 1, max_x = 0, max_y = 0;
        
        // inicializa a matriz de retorno
        for(i = 0; i < nro_linhas; i++)
            for(j = 0; j < nro_colunas; j++){
                matriz_virada[i][j] = false;
            }
        
        if(sentido == 1){ // sentido anti-horário
            // obtém a matriz transposta
            for(i = 0; i < nro_linhas; i++)
                for(j = 0; j < nro_colunas; j++)
                    matriz_aux[j][i] = matriz[i][j];
            
            // copia matriz auxiliar inteira
            for(i = 0; i < nro_linhas; i++)
                for(j = 0; j < nro_colunas; j++)
                    matriz[i][j] = matriz_aux[i][j];
        }
        
        // determina a submatriz da matriz 4x4 que contém a peça
        for(i = 0; i < nro_linhas; i++)
            for(j = 0; j < nro_colunas; j++){
                if(matriz[i][j]){
                    if(j > max_x)
                        max_x = j;
                    if(i > max_y)
                        max_y = i;
                    if(j < min_x)
                        min_x = j;
                    if(i < min_y)
                        min_y = i;
               }
            }
        
        // zera a matriz auxiliar
        for(i = 0; i < nro_linhas; i++)
            for(j = 0; j < nro_colunas; j++)
                matriz_aux[i][j] = false;
        
        // reflete verticalmente essa submatriz
        for(i = min_y; i <= max_y; i++){  
            for(j = min_x; j <= max_x; j++)
                matriz_aux[i][j] = matriz[max_y - contador][j];
            contador++;
        }
        
        if(sentido == 2){ // sentido horário
            // obtém a matriz transposta
            for(i = min_y; i <= max_y; i++)
                for(j = min_x; j <= max_x; j++)
                    matriz_virada[j][i] = matriz_aux[i][j];
        }
        else
            matriz_virada = matriz_aux;
        
        peca_aux.setBlock(matriz_virada);
        
        while(pode_descer){
            pode_descer = false;
            flag = true;
            for(j = 0; j < 4; j++)
                if(matriz_virada[3][j])
                    flag = false;
            if(flag){
                for(i = 3; i > 0; i--)
                    for(j = 0; j < 4; j++)
                        matriz_virada[i][j] = matriz_virada[i-1][j];
                
                for(j = 0; j < 4; j++)
                    matriz_virada[0][j] = false;
                        
                pode_descer = true;
            }
        }
        //TESTE SE É POSSÍVEL VIRAR OU NÃO
        /*for(i = 0; i < lista_pecas.length; i++)
            if(colisao(lista_pecas[i], peca_aux))
                pode_virar = false;*/
        for(i = 0; i < 4; i++)
            for(j = 0; j < 4; j++)
                if(matriz_virada[i][j])
                    if (mapa[(int)peca.getMin().getX()+i][(int)peca.getMin().getY()+j] != 0)
                        pode_virar = false;
                
        if(!pode_virar)
            return null;
        
        return matriz_virada;
    }
}
