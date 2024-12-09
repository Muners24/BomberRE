package RED;

import CONST.SERVER_CONST;
import Game.Input;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private final ArrayList<Input> inputs;
    private int client_counter;

    public Server(int port) {
        this.port = port;
        inputs = new ArrayList<Input>();
        client_counter = 0;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void init() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        while(true){
            System.out.println("Esperando cliente");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente encontrado");

            HandleClient clientThread = new HandleClient(client_counter++,clientSocket, this);
            clientThread.start();
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public ArrayList<Input> getInputs(){
        return inputs;
    }



    public synchronized void addInput(Input newInput) {
        boolean find = false;
        Iterator<Input> iterator = inputs.iterator();
        while (iterator.hasNext()) {
            Input i = iterator.next();
            if (i.getId() == newInput.getId()) {

                i.up = newInput.up;
                i.down = newInput.down;
                i.left = newInput.left;
                i.right = newInput.right;
                i.key = newInput.key;
                find = true;
                break;
            }
        }

        if (!find) {
            inputs.add(newInput);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(SERVER_CONST.PORT);
        try {
            server.init();
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
