/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hhangmanserver;

import hhangmancommon.GameState;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ljubomir
 */
public class HHANGMANSERVER {

    static List<ClientThread> clients = new ArrayList<>();
    static final List<String> words = new ArrayList<>(List.of("petao", "avion", "ptica"));
    static String izabranaRec = words.get((int) (3 * Math.random()));

    public static void main(String[] args) {
        try {
            System.out.println("Izabrana rec :" + izabranaRec);
            ServerSocket ss = new ServerSocket(12344);
            System.out.println("Server started... waiting for 2 players.");

            while (true) {
                Socket s = ss.accept();

                if (clients.size() >= 2) {
                    // reject extra clients
                    System.out.println("A client tried to connect, but 2 players are already in the game.");
                    s.close();
                    continue;
                }

                ClientThread ct = new ClientThread(s);
                clients.add(ct);
                ct.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void broadcastWinner(ClientThread ctt) throws IOException {
        GameState gs = new GameState();
        gs.setWinner(ctt.name);
        gs.setGameOver(true);
        for (ClientThread ct : clients) {
            ct.out.writeObject(gs);

        }

    }

    static class ClientThread extends Thread {

        Socket socket;
        ObjectOutputStream out;
        ObjectInputStream in;
        String name;
        int brojPokusaja;
        List<String> pokusaji;
        StringBuilder revealed;
        GameState gameState;
        boolean isOver;

        public ClientThread(Socket s) throws IOException {
            this.socket = s;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            gameState = new GameState();
            name = String.valueOf(clients.size() + 1);
            pokusaji = new ArrayList();
            brojPokusaja = 10;
            revealed = new StringBuilder("_".repeat(izabranaRec.length()));
        }

        @Override
        public void run() {
            try {
                while (!isOver && brojPokusaja > 0) {
                    Object obj = in.readObject();
                    if (obj instanceof String guess) {
                        System.out.println("Player guessed: " + guess);
                        pokusaji.add(guess);
                        validate(guess);

                    }
                }
                if (isOver) {

                    broadcastWinner(this);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void validate(String guess) {
            if (guess.length() != 1) {
                return; // only single letters
            }
            char c = guess.charAt(0);
            boolean hit = false;

            for (int i = 0; i < izabranaRec.length(); i++) {
                if (izabranaRec.charAt(i) == c) {
                    revealed.setCharAt(i, c);
                    hit = true;
                }
            }

            if (!hit) {
                brojPokusaja--;
            }

            isGameOver(); // sets isOver = true if someone has won

            if (isOver) {
                // immediately broadcast to all clients
                try {
                    HHANGMANSERVER.broadcastWinner(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                sendState(); // send normal update
            }
        }

        private void isGameOver() {
            if (revealed.toString().equals(izabranaRec)) {
                isOver = true;
            }

        }

        private void sendState() {
            GameState gs = new GameState();
            gs.setBrojPokusaja(brojPokusaja);
            gs.setIskoriscenaSlova(new ArrayList<>(pokusaji));
            gs.setRevealed(revealed.toString());

            try {
                out.writeObject(gs);
                out.flush();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
