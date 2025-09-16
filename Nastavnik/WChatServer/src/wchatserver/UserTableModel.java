/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wchatserver;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import wchatcommon.User;

/**
 *
 * @author Ljubomir
 */
public class UserTableModel extends AbstractTableModel {
    List<ClientThread> loggedClients;
    String[] cols = {"Name", "Surname"};
    public UserTableModel(List<ClientThread> loggedClients) {
        this.loggedClients = loggedClients;
    }

    public UserTableModel() {
    }

    public void setLoggedClients(List<ClientThread> loggedClients) {
        this.loggedClients = loggedClients;
    }

    @Override
    public int getRowCount() {
        return loggedClients.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = loggedClients.get(rowIndex).getUser();
        switch(columnIndex){
            case 0: return u.getName();
            case 1: return u.getSurname();
            default: return null;
        }
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }
    
    public List<ClientThread> getList(){
        return loggedClients;
    }
    
}