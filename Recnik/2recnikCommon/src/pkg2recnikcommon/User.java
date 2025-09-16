/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikcommon;

import java.io.Serializable;

/**
 *
 * @author Ljubomir
 */
public class User implements Serializable {
    String ime;
    String prezime;
    String email;
    String sifra;
    boolean isOnline;

    public User() {
    }

    public User(String ime, String prezime, String email, String sifra) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.sifra = sifra;
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

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    @Override
    public String toString() {
        return "User{" + "ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", sifra=" + sifra + '}';
    }
    
    
}
