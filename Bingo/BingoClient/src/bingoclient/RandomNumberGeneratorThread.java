/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingoclient;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Ljubomir
 */
public class RandomNumberGeneratorThread extends Thread {
    JLabel label;
    public RandomNumberGeneratorThread(JLabel label) {
        this.label = label;
    }
    
    
    
    public void run(){
        
        while(!isInterrupted()){
            
            label.setText(String.valueOf((int) (5 * Math.random())));
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                 interrupt();
                ex.printStackTrace();
            }
            
        }
    }
    
}
