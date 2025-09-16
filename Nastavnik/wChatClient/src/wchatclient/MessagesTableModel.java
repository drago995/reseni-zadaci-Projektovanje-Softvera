/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wchatclient;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import wchatcommon.Message;

/**
 *
 * @author Ljubomir
 */
public class MessagesTableModel extends AbstractTableModel {

    List<Message> messages;
    String[] cols = {"Sender", "Receiver", "Test", "Time"};

    @Override
    public int getRowCount() {
        return messages.size();
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
        Message m = messages.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return m.getSender().getName();
            case 1:
                return m.getReceiver().getName();
            case 2:
                return m.getText();
            case 3:
                return m.getTime();

            default:
                throw new AssertionError();
        }
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    

}
