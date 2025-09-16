/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wchatclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import wchatcommon.Message;
import wchatcommon.Operation;
import wchatcommon.Request;
import wchatcommon.Response;
import wchatcommon.User;

/**
 *
 * @author Ljubomir
 */
public class Controller {

    MainForm frame;
    Socket socket;
    ObjectOutputStream ous;
    ObjectInputStream ois;

    public MainForm getFrame() {
        return frame;
    }

    public void setFrame(MainForm frame) {
        this.frame = frame;
    }

    public Controller() throws IOException {

        socket = new Socket("localhost", 12345);
        ous = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

    }

    public Object sendRequestReceiveResponse(Object arg, Operation op) throws IOException, ClassNotFoundException, Exception {
        Request request = new Request();
        request.setArg(arg);
        request.setOp(op);
        ous.writeObject(request);
        ous.flush();

        Response response = (Response) ois.readObject();

        if (response.getException() == null) {
            return response.getResult();
        }

        throw response.getException();

    }

    public void sendRequest(Object arg, Operation op) throws IOException, ClassNotFoundException, Exception {
        Request request = new Request();
        request.setArg(arg);
        request.setOp(op);
        ous.writeObject(request);
        ous.flush();

    }

    User login(User u) throws Exception {

        User user = (User) sendRequestReceiveResponse(u, Operation.LOGIN);

        return user;
    }

    void startListener() {
        Thread listener = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    Response response = (Response) ois.readObject();
                    if (response.getResult() instanceof List) {
                        List<?> list = (List<?>) response.getResult();
                        if (!list.isEmpty()) {
                            Object first = list.get(0);
                            if (first instanceof Message) {
                                frame.fillTable((List<Message>) response.getResult());
                            } else if (first instanceof String) {
                                frame.fillComboBox((List<User>) response.getResult());
                            }
                        }

                    }else if (response.getResult() instanceof Message) {
                        frame.fillInbox((Message) response.getResult());
                    }
                    else if(response.getResult() instanceof String){
                        JOptionPane.showMessageDialog(frame, "IZGUBILI STE KONEKCIJU SA SERVEROM");
                        Thread.sleep(5000);
                         System.exit(1);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        listener.start();
    }

}
