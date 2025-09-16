/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikserver;

import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import pkg2recnikcommon.User;

/**
 *
 * @author Ljubomir
 */
public class UserTableModel extends AbstractTableModel {

    List<User> users;
    String[] cols = {"Ime", "Prezime", "Onlajn Status"};

    @Override
    public int getRowCount() {
        return users.size();
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
        User u = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return u.getIme();
            case 1:
                return u.getPrezime();
            case 2:
                return u.isIsOnline() ? "Online" : "-";
            default:
                throw new AssertionError();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    void refreshTable(User user) {
        for(User u : users){
            if(u.getIme().equals(user.getIme())){
                u.setIsOnline(true);
                
            }
        }
        // add lambda
        users.sort((u1,u2) -> Boolean.compare(u2.isIsOnline(), u1.isIsOnline()));
        
        fireTableDataChanged();
    }
    
}
