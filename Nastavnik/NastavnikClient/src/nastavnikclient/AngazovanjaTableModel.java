/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nastavnikclient;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import nastavnikcommon.Angazovanje;
import nastavnikcommon.Nastavnik;
import nastavnikcommon.Predmet;

/**
 *
 * @author Ljubomir
 */
public class AngazovanjaTableModel extends AbstractTableModel {

    List<Angazovanje> angazovanja = new ArrayList<>();
    String[] cols = {"Nastavnik", "Predmet", "Tip angazovanja"};

    public List<Angazovanje> getAngazovanja() {
        return angazovanja;
    }

    public void setAngazovanja(List<Angazovanje> angazovanja) {
        this.angazovanja = angazovanja;
    }

    @Override
    public int getRowCount() {
        return angazovanja.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Angazovanje a = angazovanja.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return a.getNastavnik().getIme();
            case 1:
                return a.getPredmet().getNaziv();
            case 2:
                return a.getTipAngazovanja();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Angazovanje a = angazovanja.get(rowIndex);
        switch (columnIndex) {
            case 0:
                
                break;
            case 1:
               a.getPredmet().setNaziv( aValue.toString());
                break;
            case 2:
                a.setTipAngazovanja(aValue.toString());
                break;
            default:
                throw new AssertionError();

        }
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 || columnIndex == 2;
    }

}
