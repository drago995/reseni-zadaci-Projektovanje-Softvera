/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wchatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import wchatcommon.Message;
import wchatcommon.Operation;
import static wchatcommon.Operation.LOGIN;
import wchatcommon.Request;
import wchatcommon.Response;
import wchatcommon.User;

/**
 *
 * @author Ljubomir
 */
public class ClientThread extends Thread {

    Socket socket;
    ObjectOutputStream ous;
    ObjectInputStream ois;
    ServerThread server;
    DbBroker broker;
    User user;

    public ClientThread(ServerThread aThis, Socket s) throws SQLException, IOException {

        this.socket = s;
        ous = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        this.server = aThis;
        broker = new DbBroker();
    }

    public void run() {
        try {

            while (!socket.isClosed()) {
                Request request = (Request) ois.readObject();
                handleRequest(request);
            }
        } catch (Exception e) {
            logout();
            e.printStackTrace();
        }

    }

    private void handleRequest(Request request) {
        try {
            switch (request.getOp()) {
                case LOGIN:
                    login(request);
                    break;
                case SEND_MESSAGE:
                    sendMessage(request);
                    break;
                case LOGOUT:
                    logout();
                    break;
                default:
                    throw new AssertionError();
            }

        } catch (Exception e) {
            Response r = new Response();
            r.setException(e);
            sendResponse(r);
            e.printStackTrace();
        }
    }

    private void login(Request request) throws Exception {
        User user = broker.loginUser((User) request.getArg());
        server.handleLogin(this, user);
        this.setUser(user);
        Response response = new Response(user, null);
        sendResponse(response);
        server.broadcastOnlineUserList();
        List<Message> history = broker.getMessageHistory();
        server.broadcastMessageHistory(history);

    }

    private void sendMessage(Request request) {
        server.sendMessageToSpecific((Message) request.getArg());
    }

    public void logout() {
        try {
            server.removeUserFromOnlineList(this);
            server.broadcastOnlineUserList();
            sendResponse(new Response(new String(), null));
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendResponse(Response r) {
        try {
            ous.writeObject(r);
            ous.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
