/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nastavnikserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import nastavnikcommon.Angazovanje;
import nastavnikcommon.Nastavnik;
import nastavnikcommon.Predmet;
import nastavnikcommon.Request;
import nastavnikcommon.Response;

/**
 *
 * @author Ljubomir
 */
public class ClientThread extends Thread {

    Socket socket;
    ObjectOutputStream ous;
    ObjectInputStream ois;
    Server server;
    DbBroker broker;

    public ClientThread(Socket s, Server aThis) throws IOException, SQLException {
        this.socket = s;
        ous = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        this.server = server;
        broker = new DbBroker();
    }

    public void run() {

        try {
            while (!socket.isClosed()) {
                Request request = (Request) ois.readObject();
                Response response = handleRequest(request);
                ous.writeObject(response);
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
                    result = broker.login((Nastavnik) request.getArgument());
                    break;
                case RETURN_LISTA_PREDMETA_BY_NASTAVNIK:
                    result = broker.getListaPredmetaByNastavnil((Nastavnik) request.getArgument());
                    break;
                case GET_ALL_ANGAZOVANJA_BY_PREDMET:

                    result = broker.getAllAngazovanjaByPredmet((Predmet) request.getArgument());
                    break;
                case SAVE_ANGAZOVANJE:
                    
                    broker.saveAngazovanje((List<Angazovanje>) request.getArgument());
                  
                    break;
                case GET_PREDMET_COUNT:
                    result = broker.getTipPredavanjaCount((Nastavnik) request.getArgument());
                    break; // PUT THE FUCKING BREAK
                default:
                    throw new AssertionError("Operation not supported");

            }
        } catch (Exception e) {
            response.setEx(e);
        }

        response.setResult(result);
        return response;

    }

}
