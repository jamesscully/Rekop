package com.scully.game;

import com.scully.enums.PlayerInfo;
import com.scully.server.TIdentityFile;
import com.scully.server.TPokerThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Future;

public class Player {

    public String username;

    static int _id = 0;


    public int id = 0;

    public Socket socket;


    public ObjectInputStream  objIn;
    public ObjectOutputStream objOut;

    public TPokerThread thread;

    public TIdentityFile identityFile;

    public boolean folded = false;

    // determines if they have explicitly disconnected
    public boolean disconnected = false;

    // determines if they have crashed or lost connection
    public boolean error = false;

    public Future future;

    public int chips = 1000;

    public Player(Socket s) {
        id = _id++;
        this.socket = s;

        try {

            // these need to be arranged this way; they wait for the peer (client-program) to create their in/outs
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objIn  = new ObjectInputStream (socket.getInputStream());

            identityFile = (TIdentityFile) objIn.readObject();

            System.out.println("Player: Retrieved identity file with token: " + identityFile.token);

            thread = new TPokerThread(socket, Integer.toString(_id));

            // assign the threads in/out here; we can keep track of it.
            thread.in   = objIn;
            thread.out  = objOut;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Player: Error occured");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            objOut.writeUTF(message);
            objOut.flush();
        } catch (IOException e) {
            System.err.println("Player: error sending message to player");
        }
    }

    public String receiveMessage() {
        String ret = "IF YOU SEE THIS, THERE IS AN ERROR!";
        try {
            ret = objIn.readUTF();
        } catch (IOException e) {
            System.err.println("Player: error retrieving message from player");
        }

        return ret;
    }

    public void sendObject(Object o) {
        try {
            objOut.writeObject(o);
            objOut.flush();
        } catch (IOException e) {
            System.err.println("Player: error sending object to server");
        }

    }

    public Object receiveObject() {
        Object ret = null;
        try {
            ret = objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Player: error retrieving object from player");
        }
        return ret;
    }

    public void close() {
        try {
            objIn.close();
            objOut.close();
        } catch (IOException e) {
            System.err.println("Player: Error closing in/out sockets");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public PlayerInfo getPlayerInfo() {
        return new PlayerInfo(id, chips);
    }
}
