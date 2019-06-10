/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forzaquattro;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 *
 * @author IlCordio
 */

public class FXMLDocumentController implements Initializable {
    @FXML
    private Canvas campo; // canvas per disegnare
    @FXML
    private GridPane grid; // grid per l'inserimento dei due nomi
    @FXML
    private TextField playerOne; // campo dove inserire il nome
    @FXML
    private TextField playerTwo; // campo dove inserire il nome
    
    Color coloreCoin = Color.YELLOW; // colore del coin da inserire
    Gioco gioca = new Gioco();
    int matX[] = new int [7]; // matrice per asseX cerchi
    int matY[] = new int [6]; // matrice per asseY cerchi
    int colonne[] = gioca.getColonne(); // vettore per le colonne dove inserire
    
    Alert alert = new Alert(AlertType.INFORMATION);//per avvisare chi ha vinto e selezionare tra due opzioni
    ButtonType buttonExit= new ButtonType("Esci"); // bottone per uscire
    ButtonType buttonNewGame = new ButtonType("Nuova Partita"); // bottone per creare un nuovo game
    
    Dialog<Pair<String, String>> dialog = new Dialog<>(); // per l'inserimento dei nomi dei player
    ButtonType buttonGioca = new ButtonType("Gioca"); // bottone per giocare
    String giocatoreUno = "", giocatoreDue = ""; // Stringhe per i nomi dei due giocatori

    
    @Override
    public void initialize(URL url, ResourceBundle rb) { // Disegna il campo iniziale
        dialog.setTitle("Forza Quattro"); // setto titolo pane 
        dialog.setHeaderText("Inserire i nome dei due giocatori"); // intestastazione pane
        dialog.getDialogPane().getButtonTypes().addAll(buttonGioca,buttonExit);// aggiungo i bottoni
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            
        if (dialogButton == buttonGioca) { // comincio la partita se premuto gioca
          return new Pair<>(playerOne.getText(), playerTwo.getText());
        }
        else if (dialogButton == buttonExit){
            System.exit(0); // esco se premuto exit
        }
            return null;
        });        
        giocatori(); // chiamo la funzione per inserire i due nomi
        
        GraphicsContext gc = campo.getGraphicsContext2D(); // canvas per disegnare
        gc.setStroke(Color.BLACK); // setto contorno cerchi
        int x = 30;
        int y = 85 ;
        for(int i = 0; i < 6; i++){ // disegno i cerchi
            for(int j = 0; j < 7; j++){
                matX[j] = x;
                gc.strokeOval(x, y, 60, 60);
                x += 70;
            }
            matY[i] = y;
            y += 70;
            x = 30;
        }
        gc.setFill(coloreCoin); // setto il colore del coin da inserire
        gc.fillOval(240,5,60,60); // disegno il coin da inserire
        
        alert.setGraphic(new ImageView("win.jpg")); // inserisco l'immagine come icona
        alert.getButtonTypes().setAll(buttonNewGame,buttonExit); // aggiungo i bottoni all'avviso
        alert.setTitle("Information Dialog"); //setto il titolo
        alert.setHeaderText(null); // setto l'header vuoto
    }    

    @FXML
    private void mouviCoin(MouseEvent e) {// posiziono il coin da inserire in base a dove si muove il mouse   
        GraphicsContext gc = campo.getGraphicsContext2D(); // canvas per disegnare
        
        if(gioca.getPlayer()) // True = coinGiallo False = coinRosso
            coloreCoin = Color.YELLOW;
        else coloreCoin = Color.RED;     
        gc.setFill(coloreCoin); //setto il colore
        
        if(e.getX() >= 0 && e.getX() <= matX[1]){ // verifico dove si trova il puntatore e muovo il coin da inserire
            gc.clearRect(0, 0, 550, 80); //pulisco canvas dove c'è il coin di inserimento
            gc.fillOval(matX[0],5,60,60); // disegno il coin da inserires
        }
        else if(e.getX() > matX[1] && e.getX() <= matX[2]){
            gc.clearRect(0, 0, 550, 80);
            gc.fillOval(matX[1],5,60,60);
        }
        else if(e.getX() > matX[2] && e.getX() <= matX[3]){
            gc.clearRect(0, 0, 550, 80);
            gc.fillOval(matX[2],5,60,60);
        }
        else if(e.getX() > matX[3] && e.getX() <= matX[4]){
            gc.clearRect(0, 0, 550, 80);
            gc.fillOval(matX[3],5,60,60);
        }        
        else if(e.getX() > matX[4] && e.getX() <= matX[5]){
            gc.clearRect(0, 0, 550, 80);
            gc.fillOval(matX[4],5,60,60);
        }
        else if(e.getX() > matX[5] && e.getX() <= matX[6]){
            gc.clearRect(0, 0, 550, 80);
            gc.fillOval(matX[5],5,60,60);
        }
        else if(e.getX() > matX[6] && e.getX() <= campo.getWidth()){
            gc.clearRect(0, 0, 550, 80);
            gc.fillOval(matX[6],5,60,60);
        }
    }

    @FXML
    private void posizionaCoin(MouseEvent e) { // posiziono il coin in base a dove si è cliccato
        int pos = 0; // intero che indica la colonna dove si è inserito il coin
        GraphicsContext gc = campo.getGraphicsContext2D(); // canvas per disegnare
        
        if(e.getX() >= 0 && e.getX() <= matX[1]){// verifico dove si trova il puntatore
            if(colonne[0] >= 0){
                gc.fillOval(matX[0], matY[colonne[0]], 60, 60); // disegno il coin nel campo da gioco
                gioca.setPos(0); // inserisco nella colonna nella matrice logica 
                pos = 0; // salvo il valore della colonna dove si è inserito
            }
        }
        else if(e.getX() > matX[1] && e.getX() <= matX[2]){
            if(colonne[1] >= 0){
                gc.fillOval(matX[1],matY[colonne[1]],60,60);
                gioca.setPos(1);
                pos = 1;
            }
        }
        else if(e.getX() > matX[2] && e.getX() <= matX[3]){
            if(colonne[2] >= 0){
                gc.fillOval(matX[2],matY[colonne[2]],60,60);
                gioca.setPos(2);
                pos = 2;
            }
        }
        else if(e.getX() > matX[3] && e.getX() <= matX[4]){
            if(colonne[3] >= 0){
                gc.fillOval(matX[3],matY[colonne[3]],60,60);
                gioca.setPos(3);
                pos = 3;
            }
        }        
        else if(e.getX() > matX[4] && e.getX() <= matX[5]){
            if(colonne[4] >= 0){
                gc.fillOval(matX[4],matY[colonne[4]],60,60);
                gioca.setPos(4);
                pos = 4;
            }
        }
        else if(e.getX() > matX[5] && e.getX() <= matX[6]){
            if(colonne[5] >= 0){
                gc.fillOval(matX[5],matY[colonne[5]],60,60);
                gioca.setPos(5);
                pos = 5;
            }
        }
        else if(e.getX() > matX[6] && e.getX() <= campo.getWidth()){
            if(colonne[6] >= 0){
                gc.fillOval(matX[6],matY[colonne[6]],60,60);
                gioca.setPos(6);
                pos = 6;
            }
        }
        
        //Controllo vittoria
        gioca.checkWin(colonne[pos]); // controllo se qualcuno vinto
        if(gioca.isWin()){ // True = vittoria , False = continua con il gioco
            if(gioca.getPlayer()) // controllo chi ha vinto
                alert.setContentText(giocatoreUno +  " ha vinto!!");
            else alert.setContentText(giocatoreDue + " ha vinto!!");

            Optional<ButtonType> result = alert.showAndWait(); //Apro una finestra per dire chi ha vinto
            if (result.get() == buttonExit)
                System.exit(0);// chiudo la finestra di gioco
            else if (result.get() == buttonNewGame){
                resetGame();// Creo un nuovo game
                giocatori();//reinserisco i nomi
                return;
            }
        } // Controllo se nessuno può inserire e nessuno ha vinto
        else if(colonne[0] < 0 && colonne[1] < 0 && colonne[2] < 0 && colonne[3] < 0 && colonne[4] < 0 && colonne[5] < 0 &&
            colonne[6] < 0 && (!gioca.isWin())){
                alert.setContentText("Pareggio, nessuno dei due giocatori ha vinto"); //dico che nessuno ha vinto
            Optional<ButtonType> result = alert.showAndWait(); // apro finestra per notificare
            if (result.get() == buttonExit)
                System.exit(0); // chiudo la finestra di gioco se premuto Exit
            else if (result.get() == buttonNewGame){
                resetGame();// Creo un nuovo game se premuto newGame
                giocatori();//reinserisco i nomi 
                return;
            }
        }
        gioca.turno(); // cambio il tuorno, tocca all'altro giocatore
        
        if(gioca.getPlayer()) // True = coinGiallo False = coinRosso
            coloreCoin = Color.YELLOW;
        else coloreCoin = Color.RED;
        
        gc.setFill(coloreCoin); //setto il colore
        gc.clearRect(0, 0, 550, 80); // pulisco il canvas dove c'è il coin da inserire
        gc.fillOval(matX[pos],5,60,60); // disegno il nuovo coin
    } 
    
    private void resetGame(){ // resetto le impsotazioni di gioco
        GraphicsContext gc = campo.getGraphicsContext2D(); // canvas per disegnare
        gioca.reset(); // resetto le impostazioni per un nuovo game
        gc.clearRect(0, 0, 550, 530); // pulisco canvas
        coloreCoin = Color.YELLOW;   // setto il colore del coin
        gc.setStroke(Color.BLACK); // disegno il campo vuoto
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                gc.strokeOval(matX[j], matY[i], 60, 60);
            }
        }
        gc.setFill(coloreCoin); 
        gc.fillOval(240,5,60,60); 
    }
    
    private void giocatori(){ // finestra per inserire i nomi dei giocatori     
        Optional<Pair<String, String>> result = dialog.showAndWait(); // apro la finestra per inserire nomi
        result.ifPresent(nomi -> {
        giocatoreUno = nomi.getKey(); // recupero il nome del primo giocatore
        giocatoreDue = nomi.getValue(); // recupero il nome del secondo giocatore
        });
    }
}
