/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikserver;

import java.net.ServerSocket;
import java.net.Socket;
import pkg2recnikcommon.User;

/**
 *
 * @author Ljubomir
 */
public class ServerThread extends Thread {

    UserTableModel modelUser;
    RecnikTableModel modelRecnik;

    public ServerThread(UserTableModel modelUser, RecnikTableModel modelRecnik) {
        this.modelRecnik = modelRecnik;
        this.modelUser = modelUser;
    }

    public void run() {

        try {
            ServerSocket ss = new ServerSocket(12345);
            System.out.println("awaiting connection...");
            while (!ss.isClosed()) {
                Socket s = ss.accept();

                System.out.println("client connected");
                ClientThread ct = new ClientThread(s, this);
                ct.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    

    void refreshTableModel(User user) {
        modelUser.refreshTable(user);
    }

}
