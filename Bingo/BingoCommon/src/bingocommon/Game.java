/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingocommon;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ljubomir
 */
public class Game implements Serializable {
    LocalDateTime time;
    int brojPokusaja;
    List<Integer> dobitna;
    StringBuilder istorija = new StringBuilder();
    int naSvomMestu;
    int nisuNaMestu;
    boolean isOver;
    User user;

    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

   public Game checkGame(List<Integer> pokusaj) {
    naSvomMestu = 0;
    nisuNaMestu = 0;

    List<Integer> dobitnaCopy = new ArrayList<>(dobitna);
    List<Integer> pokusajCopy = new ArrayList<>(pokusaj);

    // Count correct positions
    for (int i = 0; i < 4; i++) {
        if (pokusajCopy.get(i).equals(dobitnaCopy.get(i))) {
            naSvomMestu++;
            dobitnaCopy.set(i, null);
            pokusajCopy.set(i, null);
        }
    }

    // Count correct numbers, wrong positions
    for (Integer p : pokusajCopy) {
        if (p != null && dobitnaCopy.contains(p)) {
            nisuNaMestu++;
            dobitnaCopy.set(dobitnaCopy.indexOf(p), null); // remove matched
        }
    }

    if (naSvomMestu == 4) setIsOver(true);
    brojPokusaja++;
    istorija.append(pokusaj.toString())
            .append("\n");

    return this;
}


    public boolean isIsOver() {
        return isOver;
    }

    public Game() {
        time = LocalDateTime.now();
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public StringBuilder getIstorija() {
        return istorija;
    }

    public void setIstorija(StringBuilder istorija) {
        this.istorija = istorija;
    }

    public int getBrojPokusaja() {
        return brojPokusaja;
    }

    public void setBrojPokusaja(int brojPokusaja) {
        this.brojPokusaja = brojPokusaja;
    }

    public List<Integer> getDobitna() {
        return dobitna;
    }

    public void setDobitna(List<Integer> dobitna) {
        this.dobitna = dobitna;
    }

    public int getNaSvomMestu() {
        return naSvomMestu;
    }

    public void setNaSvomMestu(int naSvomMestu) {
        this.naSvomMestu = naSvomMestu;
    }

    public int getNisuNaMestu() {
        return nisuNaMestu;
    }

    public void setNisuNaMestu(int nisuNaMestu) {
        this.nisuNaMestu = nisuNaMestu;
    }

}
