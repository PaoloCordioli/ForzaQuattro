/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forzaquattro;

/**
 *
 * @author IlCordio
 */

public class Gioco {
    private int mat[][]; // matrice per il campo da gioco
    private int valore;  // valore da inserire nella matrice
    private boolean player; //true = giocatoreUno false = giocatoreDue
    private boolean win; // indica se si vince o no
    private int colonne[]; //vettore per le colonne dove inserire
    
    public Gioco (){ //Costruttore per inizializare il primo game
        mat = new int [6][7]; //inizializzo matrice a zero
        for(int i = 0; i < 6 ; i++)
            for(int j = 0; j < 7;j++)
                mat[i][j] = 0;  
        
        colonne = new int[7]; // inizializzo le colonne per inserire dal basso
        for(int i = 0; i < 7; i++)
            colonne[i] = 5;
        
        player = true; // inizia il player uno
        win = false; 
        valore = 1; // inizia il player uno
    }

    public void setPos(int pos) {//inserisco il valore nella posizione corretta
        if(pos == 0){ // controllo quanto vale pos 
            mat[colonne[0]][pos] = valore; // setto nella matrice il valore
            System.out.println(toString());;
            colonne[0]--; // decremento la posizione dove inserire
        }
        else if(pos == 1){
            mat[colonne[1]][pos] = valore;
            System.out.println(toString());;
            colonne[1]--;            
        }
        else if(pos == 2){
            mat[colonne[2]][pos] = valore;
            System.out.println(toString());;
            colonne[2]--;            
        }
        else if(pos == 3){
            mat[colonne[3]][pos] = valore;
            System.out.println(toString());;
            colonne[3]--;            
        }
        else if(pos == 4){
            mat[colonne[4]][pos] = valore;
            System.out.println(toString());;
            colonne[4]--;            
        }
        else if(pos == 5){
            mat[colonne[5]][pos] = valore;
            System.out.println(toString());;
            colonne[5]--;            
        }   
        else if(pos == 6){
            mat[colonne[6]][pos] = valore;
            System.out.println(toString());;
            colonne[6]--;            
        }          
    }
    
    public void checkWin(int pos){ //controlla se qualcuno ha vinto
        pos++;
     
        if(pos < 3) // Cerca in Verticale
            for(int i = 0; i < 7; i++){
                if(mat[pos][i] != 0 && mat[pos][i] == mat[pos+1][i] && mat[pos][i] == mat[pos+2][i] 
                        && mat[pos][i] == mat[pos+3][i]){
                    win = true;
                    return;
                }
            }
        
        for(int i = 0; i < 4; i++){ // Cerca in Orizzontale
            if(mat[pos][i] != 0 && mat[pos][i] == mat[pos][i+1] && mat[pos][i] == mat[pos][i+2] 
                    && mat[pos][i] == mat[pos][i+3]){
                win = true;
                return;
            }
        }
        
        for(int i = 0; i < 6 && !win ; i++){ // Cerca in Diagonale
            for (int j = 0; j < 7; j++){
                if (mat[i][j] != 0 && i > 2 && j < 4){
                    if(mat[i][j] == mat[i-1][j+1] && mat[i][j] == mat[i-2][j+2] 
                        && mat[i][j] == mat[i-3][j+3]){
                        win = true;
                        return;
                    }
                }
                else if(mat[i][j] != 0 && i < 3 && j < 4){
                    if (mat[i][j] == mat[i+1][j+1] && mat[i][j] == mat[i+2][j+2]
                        && mat[i][j] == mat[i+3][j+3]){
                        win = true;
                        return;
                    }
                }
                    else if(mat[i][j] != 0 && i < 3 && j > 2){
                        if (mat[i][j] == mat[i+1][j-1] && mat[i][j] == mat[i+2][j-2] 
                            && mat[i][j] == mat[i+3][j-3]){
                            win = true;
                            return;
                    }
                }
                else if(mat[i][j] != 0 && i > 2 && j > 2){
                    if (mat[i][j] == mat[i-1][j-1] && mat[i][j] == mat[i-2][j-2] 
                        && mat[i][j]== mat[i-3][j-3]){
                        win = true;
	                return;
                    }
                }
            }
        }
    }

    public void turno(){ // gestice i turni dei giocatori
        if (player){
            player=false;	
            valore = 2;
	}
        else{
            player=true;
            valore = 1;
        }
    }
    
    public void reset(){ // resetto le impostazioni per un nuovo game
        for(int i = 0; i < 6; i++) // resetto matrice
            for(int j = 0; j < 7;j++)
                mat[i][j] = 0;  
        
        for(int i = 0; i < 7; i++) // resetto colonne d'inserimento
            colonne[i] = 5;
        
        player = true;
        win = false; 
        valore = 1;        
    }
    
    @Override
    public String toString(){ // stampa la matrice
        String s = "";
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7;j++)
                s += mat[i][j] + " ";
            s += "\n";
        }
        return s;
    } 
    
    public boolean getPlayer() { // true = giocatoreUno false = gicoatoreDue
        return player;
    }

    public int[] getColonne() { // restitusice il vettore di colonne usato per gestire l'inserimento
        return colonne;
    }
    
    public boolean isWin() { // ritorna win
        return win;
    }
}
