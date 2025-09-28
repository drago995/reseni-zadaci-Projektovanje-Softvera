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
public class Nastavnik implements Serializable {
    private int Id;
    private String ime;
    private String prezime;
    private String email;
    String sifra;

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    
    

    public Nastavnik() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Nastavnik{" + "Id=" + Id + ", ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", sifra=" + sifra + '}';
    }
    
    
}
