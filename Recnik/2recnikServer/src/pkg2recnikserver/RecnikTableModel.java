/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikserver;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import pkg2recnikcommon.Recnik;

/**
 *
 * @author Ljubomir
 */
public class RecnikTableModel extends AbstractTableModel {

    List<Recnik> recnici;
    String[] cols = {"Srpski", "Engleski"};

    @Override
    public int getRowCount() {
        return recnici.size();
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recnik r = recnici.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return r.getSrpski();
            case 1:
                return r.getEngleski();
            default:
                throw new AssertionError();
        }

    }

    public List<Recnik> getRecnici() {
        return recnici;
    }

    public void setRecnici(List<Recnik> recnici) {
        this.recnici = recnici;
    }
    
}
