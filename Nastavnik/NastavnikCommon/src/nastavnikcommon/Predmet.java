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
public class Predmet implements Serializable {
    private int Id;
    private String naziv;

    public Predmet() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "Predmet{" + "Id=" + Id + ", naziv=" + naziv + '}';
    }

    
    
    
}
