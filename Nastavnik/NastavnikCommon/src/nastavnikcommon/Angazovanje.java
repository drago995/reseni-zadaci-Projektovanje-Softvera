/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nastavnikcommon;

import java.io.Serializable;

/**
 *
 * @author Ljubomir
 */
public class Angazovanje implements Serializable {
    private Nastavnik nastavnik;
    private Predmet predmet;
    private String tipAngazovanja;
    private int id; // !!!!!!

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Angazovanje() {
    }
    
    public Nastavnik getNastavnik() {
        return nastavnik;
    }

    public void setNastavnik(Nastavnik nastavnik) {
        this.nastavnik = nastavnik;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public String getTipAngazovanja() {
        return tipAngazovanja;
    }

    public void setTipAngazovanja(String tipAngazovanja) {
        this.tipAngazovanja = tipAngazovanja;
    }

    @Override
    public String toString() {
        return "Angazovanje{" + "nastavnik=" + nastavnik + ", predmet=" + predmet + ", tipAngazovanja=" + tipAngazovanja + "idAngazovanje" + id;
    }

    
    
    
}
