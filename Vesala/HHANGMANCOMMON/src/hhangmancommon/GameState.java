/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hhangmancommon;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ljubomir
 */
public class GameState implements Serializable {

    List<String> iskoriscenaSlova;
    int brojPokusaja;
    String revealed;
    String playerName;
    boolean gameOver;
    String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public GameState() {
    }
    

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public GameState(List<String> iskoriscenaSlova, int brojPokusaja, String revealed) {
        this.iskoriscenaSlova = iskoriscenaSlova;
        this.brojPokusaja = brojPokusaja;
        this.revealed = revealed;
    }

    public List<String> getIskoriscenaSlova() {
        return iskoriscenaSlova;
    }

    public void setIskoriscenaSlova(List<String> iskoriscenaSlova) {
        this.iskoriscenaSlova = iskoriscenaSlova;
    }

    public int getBrojPokusaja() {
        return brojPokusaja;
    }

    public void setBrojPokusaja(int brojPokusaja) {
        this.brojPokusaja = brojPokusaja;
    }

    public String getRevealed() {
        return revealed;
    }

    public void setRevealed(String revealed) {
        this.revealed = revealed;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }



}
