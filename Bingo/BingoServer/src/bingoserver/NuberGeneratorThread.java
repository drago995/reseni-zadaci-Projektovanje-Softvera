/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingoserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Ljubomir
 */
public class NuberGeneratorThread extends Thread{
    JLabel l1;
    JLabel l2;
    JLabel l3;
    JLabel l4;
    boolean stop;
    
    public NuberGeneratorThread(JLabel jLabel1, JLabel jLabel2, JLabel jLabel3, JLabel jLabel4) {
        this.l1 = jLabel1;
        this.l2 = jLabel2;
        this.l3 = jLabel3;
        this.l4 = jLabel4;
    }
    
    public void run(){
        
        while(!stop){
            l1.setText(String.valueOf((int)(5 * Math.random() )));
            l2.setText(String.valueOf((int)(5 * Math.random() )));
            l3.setText(String.valueOf((int)(5 * Math.random() )));
            l4.setText(String.valueOf((int)(5 * Math.random() )));
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NuberGeneratorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
}
