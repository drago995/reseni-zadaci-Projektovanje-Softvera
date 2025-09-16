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
public class Recnik implements Serializable {
    int id;
    String srpski;
    String engleski;

    public Recnik(int id, String srpski, String engleski) {
        this.id = id;
        this.srpski = srpski;
        this.engleski = engleski;
    }

    @Override
    public String toString() {
        return "Recnik{" + "id=" + id + ", srpski=" + srpski + ", engleski=" + engleski + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrpski() {
        return srpski;
    }

    public void setSrpski(String srpski) {
        this.srpski = srpski;
    }

    public String getEngleski() {
        return engleski;
    }

    public void setEngleski(String engleski) {
        this.engleski = engleski;
    }
    
    
}
