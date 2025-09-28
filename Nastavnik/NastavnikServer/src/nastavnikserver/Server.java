/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nastavnikserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ljubomir
 */
public class Server {

    List<ClientThread> clients = new ArrayList<>();

    public static void main(String[] args) {

        new Server().run();

    }

    public void run() {

        try {
            ServerSocket ss = new ServerSocket(12345);
            while (true) {
                System.out.println("waiting for connection...");
                Socket s = ss.accept();
                System.out.println("client connected !");
                ClientThread ct = new ClientThread(s, this);
                clients.add(ct);
                ct.start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
