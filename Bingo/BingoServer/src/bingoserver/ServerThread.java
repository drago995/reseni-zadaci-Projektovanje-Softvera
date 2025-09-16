/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingoserver;

import bingocommon.Game;
import bingocommon.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ljubomir
 */
public class ServerThread extends Thread {

    List<ClientThread> clients;
    List<Integer> dobitna;
    DbBroker broker;

    public ServerThread(List<Integer> dobirna) throws SQLException {
        clients = new ArrayList<>();
        dobitna = dobirna;
        broker = new DbBroker();
    }

    public void run() {

        try {
            ServerSocket ss = new ServerSocket(12346);
            while (!ss.isClosed()) {
                System.out.println("awaiting connection");
                Socket s = ss.accept();
                System.out.println("client connected");
                ClientThread ct = new ClientThread(s, this, dobitna);
                clients.add(ct);
                ct.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void sacuvajIgre() throws SQLException {
        User u = null;
        for (ClientThread ct : clients) {
            if (ct.getGame().isIsOver()) {
                u = ct.getGame().getUser();
            }

        }

        for (ClientThread ct : clients) {
            System.out.println("CUVAM IGRE");
            broker.saveGame(ct.getGame(), u);

        }
    }

    class ClientThread extends Thread {

        Socket s;
        ObjectOutputStream ous;
        ObjectInputStream ois;
        DbBroker broker;
        Game game;
        ServerThread st;
        List<String> pokusaji;

        ClientThread(Socket s, ServerThread server, List<Integer> dobitna) throws IOException, SQLException {
            this.s = s;
            ous = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            broker = new DbBroker();
            game = new Game();
            game.setDobitna(dobitna);
            this.st = server;
        }

        public void run() {

            try {
                Object response;

                while (game.getBrojPokusaja() <= 6 && !game.isIsOver()) {
                    System.out.println(game.getBrojPokusaja() + " BROJ POKUSAJA");
                    Object request = ois.readObject();

                    if (request instanceof User) {
                        response = broker.login((User) request);
                        game.setUser((User) response);
                    } else {
                        System.out.println("do i get here");
                        response = game.checkGame((ArrayList<Integer>) request);
                        System.out.println(response);
                    }

                    sendResponse(response);

                }
                if(game.getBrojPokusaja() >= 6){
                    sendResponse(game); // mora sever da odgovori uvek poslednji !!! ne da client salje na ugasen run
                }else{
                
                }
                

            } catch (Exception e) {
                sendResponse(e);
                e.printStackTrace();
            }

        }

        private void sendResponse(Object response) {
            try {
                ous.reset(); 
                ous.writeObject(response);
                ous.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public Game getGame() {
            return game;
        }

        public void setGame(Game game) {
            this.game = game;
        }

    }

}
