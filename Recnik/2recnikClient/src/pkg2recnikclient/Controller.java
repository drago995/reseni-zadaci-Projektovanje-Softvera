/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkg2recnikclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import pkg2recnikcommon.Operation;
import pkg2recnikcommon.Recnik;
import pkg2recnikcommon.Request;
import pkg2recnikcommon.Response;
import pkg2recnikcommon.User;

/**
 *
 * @author Ljubomir
 */
public class Controller {
    ObjectOutputStream ous;
    ObjectInputStream ois;
   
    
    public Controller() throws IOException {
        Socket socket = new Socket("localhost", 12345);
        
        ous = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }
    
    
    
    
    Object sendRequestReceiveResponse(Object o, Operation op) throws IOException, ClassNotFoundException, Exception{
        Request r = new Request(o, op);
        ous.writeObject(r);
        ous.flush();
        
        Response response = (Response) ois.readObject();
        
        if(response.getEx() == null){
            return response.getResult();
            
        }
        
        throw response.getEx();
    
    }

    User login(User u) throws Exception {
        User user = (User) sendRequestReceiveResponse(u, Operation.LOGIN);
        return user;
    }

    List<Recnik> getAllRecnici() throws ClassNotFoundException, Exception {
        List<Recnik> recnici = (List<Recnik>) sendRequestReceiveResponse(null, Operation.GET_RECNICI);
        return recnici;
    }
    
    Recnik searchRecnik(Recnik r) throws Exception{
        Recnik recnik = (Recnik) sendRequestReceiveResponse(r, Operation.SEARCH_RECNIK);
        return recnik;
    
    }

    void saveRecnik(Recnik r) throws Exception {
        sendRequestReceiveResponse(r, Operation.SAVE_RECNIK);

    }

}
