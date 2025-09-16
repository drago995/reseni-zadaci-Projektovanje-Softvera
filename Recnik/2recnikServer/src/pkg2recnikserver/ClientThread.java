/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import static pkg2recnikcommon.Operation.LOGIN;
import pkg2recnikcommon.Recnik;
import pkg2recnikcommon.Request;
import pkg2recnikcommon.Response;
import pkg2recnikcommon.User;

/**
 *
 * @author Ljubomir
 */
public class ClientThread extends Thread {

    Socket socket;
    ObjectOutputStream ous;
    ObjectInputStream ois;
    DbBroker broker;
    ServerThread st;

    public ClientThread(Socket socket, ServerThread st) throws IOException, SQLException {
        this.socket = socket;
        ous = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        broker = new DbBroker();
        this.st = st;
    }

    public void run() {
        try {
            while (!socket.isClosed()) {
                Request request = (Request) ois.readObject();

                Response r = handleRequest(request);
                ous.writeObject(r);
                ous.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Response handleRequest(Request request) {
        Response response = new Response();
        Object result = null;
        try {
            switch (request.getOp()) {
                case LOGIN:
                    result = broker.loginUser((User) request.getArg());
                    User user = (User) result;
                    st.refreshTableModel(user);

                    break;
                case GET_RECNICI:
                    result = broker.getAllRecnici();

                    break;
                case SAVE_RECNIK:
                    broker.saveRecnik((Recnik) request.getArg());

                    break;
                case SEARCH_RECNIK:
                    result = broker.getPrevod((Recnik) request.getArg());

                    break;
                default:
                    throw new AssertionError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setEx(e);
        }
        response.setResult(result);
        return response;
    }

}
