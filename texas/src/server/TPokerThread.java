package server;

import cards.Card;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class TPokerThread implements Runnable {

    Socket client;

    String clientID;

    public ObjectInputStream  in;
    public ObjectOutputStream out;

    public TPokerThread(Socket client, String id) {
        this.client = client;
        this.clientID = id;
    }

    boolean PRE_FLOP_DONE = false;
    boolean FLOP_DONE     = false;
    boolean TURN_DONE     =  false;
    boolean RIVER_DONE    = false;

    boolean CONNECTED = true;

    @Override
    public void run() {

        while(!PRE_FLOP_DONE) {
            
        }

        while(!FLOP_DONE) {

        }
    }
}