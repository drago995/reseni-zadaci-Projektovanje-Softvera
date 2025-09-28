/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nastavnikclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import javax.swing.JFrame;
import nastavnikcommon.Angazovanje;
import nastavnikcommon.Nastavnik;
import nastavnikcommon.Operation;
import nastavnikcommon.Predmet;
import nastavnikcommon.Request;
import nastavnikcommon.Response;

/**
 *
 * @author Ljubomir
 */
public class Controller {

    Socket socket;
    ObjectOutputStream ous;
    ObjectInputStream ois;
    MainForm form;

    public Controller() throws IOException {
        socket = new Socket("localhost", 12345);
        ous = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    Object sendRequestReceiveResponse(Object argument, Operation op) throws IOException, ClassNotFoundException, Exception {
        Request request = new Request(op, argument);
        ous.writeObject(request);
        ous.flush();

        Response response = (Response) ois.readObject();

        if (response.getEx() == null) {
            return response.getResult();
        }

        throw response.getEx();

    }

    Nastavnik login(Nastavnik n) throws Exception {
        Nastavnik nastavnik = (Nastavnik) sendRequestReceiveResponse(n, Operation.LOGIN);
        return nastavnik;

    }

    void setForm(MainForm main) {
        this.form = main;
    }

    List<Predmet> getListaPredmetaByNastavnik(Nastavnik nastavnik) throws ClassNotFoundException, Exception {
        List<Predmet> predmeti = (List<Predmet>) sendRequestReceiveResponse(nastavnik, Operation.RETURN_LISTA_PREDMETA_BY_NASTAVNIK);
        return predmeti;
    }

    List<Angazovanje> getAllAngazovanjaByPredmet(Predmet predmet) throws ClassNotFoundException, Exception {
        List<Angazovanje> angazovanja = (List<Angazovanje>) sendRequestReceiveResponse(predmet, Operation.GET_ALL_ANGAZOVANJA_BY_PREDMET);
        return angazovanja;
    }

    void saveAngazovanje(List<Angazovanje> a) throws Exception {
        sendRequestReceiveResponse(a, Operation.SAVE_ANGAZOVANJE);
    }

    List<String> getCountByTipAngazovanja(Nastavnik nastavnik) throws Exception {        

        List<String> list = (List<String>) sendRequestReceiveResponse(nastavnik, Operation.GET_PREDMET_COUNT);
        return list;
    }

}
