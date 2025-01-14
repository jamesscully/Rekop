package com.scully.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Represents a Thread for keeping a player's connection alive
 */
public class TPokerThread implements Runnable {

    /** Socket player is connected to */
    Socket client;

    /** Player's Username */
    String clientID;

    /** Player's Object Input */
    public ObjectInputStream  in;
    /** Player's Object Output */
    public ObjectOutputStream out;

    public TPokerThread(Socket client, String id) {
        this.client = client;
        this.clientID = id;
    }

    /**
     * Should we keep the connection alive?
     */
    public boolean KEEP_ALIVE = true;

    @Override
    public void run() {

        while(KEEP_ALIVE) {
            if(Thread.currentThread().isInterrupted()) {
                return;
            }
        }
    }

}
