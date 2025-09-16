/**
 *
 * @author Ljubomir
 */
package hhangmanclient;

import hhangmancommon.GameState;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;

public class ClientController {

    static ClientController instance;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    NewJFrame gui;

    private ClientController(NewJFrame gui) throws IOException {
        this.gui = gui;
        socket = new Socket("localhost", 12344);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        // Start the listener thread
        new Thread(this::listenForServer).start();
    }

    static ClientController getInstance(NewJFrame gui) throws IOException {
        if (instance == null) {
            instance = new ClientController(gui);
        }
        return instance;
    }

    public void sendLetter(String letter) throws IOException {
        out.writeObject(letter);
        out.flush();
    }

    private void listenForServer() {
        try {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof GameState gs) {
                    // Update GUI on the Swing thread
                    gui.updateGUI(gs);

                    // Handle game over
                    if (gs.isGameOver()) {

                        if (gs.getWinner() != null) {
                            gui.showGameOver("Winner is player: " + gs.getWinner());
                        } else {
                            gui.showGameOver("Game over, no more tries left!");
                        }

                        break; // stop listening after game over
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
