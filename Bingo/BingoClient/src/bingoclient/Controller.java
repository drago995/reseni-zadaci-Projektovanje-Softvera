/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingoclient;

import bingocommon.Game;
import bingocommon.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author Ljubomir
 */
public class Controller {

    ObjectOutputStream ous;
    ObjectInputStream ois;

    public Controller() throws IOException {
        Socket s = new Socket("localhost", 12346);
        ous = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
    }

    User login(User u) throws IOException, ClassNotFoundException, Exception {
        ous.writeObject(u);
        ous.flush();

        Object obj = ois.readObject();
        
        if(obj instanceof User){
            return (User) obj;
        }
        else{
            throw (Exception) obj;
        }
    }

    Game sendReceiveGame(List<Integer> game) throws IOException, ClassNotFoundException, Exception {
        ous.writeObject(game);
        ous.flush();

        Object obj = ois.readObject();
        
        if(obj instanceof Game){
            return (Game) obj;
        }
        else{
            throw (Exception) obj;
        }
        
        


    }

}
