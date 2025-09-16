/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wchatserver;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.swing.JTable;
import wchatcommon.Message;
import wchatcommon.Response;
import wchatcommon.User;

/**
 *
 * @author Ljubomir
 */
public class ServerThread extends Thread {

    List<ClientThread> clients;
    List<ClientThread> onlineClients;
    int maxBrojKlijenata;
    JTable table;
    UserTableModel model;

    public ServerThread(JTable table, UserTableModel u) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("app.config"));
            maxBrojKlijenata = Integer.valueOf(prop.getProperty("max_broj_klijenata"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.clients = new ArrayList<>();
        onlineClients = new ArrayList<>();
        model = u;
        u.setLoggedClients(onlineClients);
        this.table = table;
        table.setModel(model);
        System.out.println(maxBrojKlijenata);
    }

    public void run() {

        try {
            ServerSocket ss = new ServerSocket(12345);
            System.out.println("Ocekujem klijenta...");
            while (true) {
                Socket s = ss.accept();
                System.out.println("Klijent je primljen");
                ClientThread ct = new ClientThread(this, s);
                clients.add(ct);
                ct.start();
                System.out.println("MAXIMALAN BROJ KLIJENATA " + maxBrojKlijenata);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void handleLogin(ClientThread aThis, User user) throws Exception {
        if (!serverIsFull() && !userIsLoggedIn(user)) {
            onlineClients.add(aThis);
            model.fireTableDataChanged();

        }
    }

    void broadcastMessageHistory(List<Message> history) {
        Response r = new Response(history, null);
        for (ClientThread ct : onlineClients) {
            ct.sendResponse(r);
        }
    }

    void broadcastOnlineUserList() {
        Response r = new Response(getOnlineUserNamesList(), null);
        for (ClientThread ct : onlineClients) {
            ct.sendResponse(r);
        }
    }

    private boolean serverIsFull() throws Exception {
        if (onlineClients.size() >= maxBrojKlijenata) {
            throw new Exception("server is full");
        } else {
            return false;
        }
    } //obrati paznju ! ako je pun bacas EXCEPTION

    private boolean userIsLoggedIn(User user) throws Exception {
        List<String> onlineUserNames = getOnlineUserNamesList();
        if (onlineUserNames.contains(user.getName())) {
            throw new Exception("user is already logged in");
        }

        return false;
    }

    private List<String> getOnlineUserNamesList() {
        return onlineClients.stream()
                .map(ClientThread::getUser)
                .map(User::getName)
                .toList();
    }

    void removeUserFromOnlineList(ClientThread remove) {
        remove.logout();
        onlineClients.remove(remove);
        
        model.fireTableDataChanged();
        
    }

    void sendMessageToSpecific(Message message) {
        Response r = new Response(message, null);
        for (ClientThread ct : onlineClients) {
            if (ct.getUser().getName().equals(message.getReceiver().getName())) {
                ct.sendResponse(r);
            }
        }
    }

}
