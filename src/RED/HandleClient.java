package RED;

import Game.Input;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HandleClient extends Thread {

    private int id;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Server server;


    public HandleClient(int id, Socket socket, Server server) {
        this.id = id;
        this.socket = socket;
        this.server = server;


        initInOut();
    }

    private void initInOut() {
        try {
            // Usamos BufferedReader y PrintWriter en lugar de ObjectInputStream y ObjectOutputStream
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true); // Auto-flush activado

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.println(id); // Enviar el ID como String
            out.flush();
            System.out.println("ID enviada: " +id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread sendThread = new Thread(this::sendInputs);
        sendThread.start();

        while (!socket.isClosed() && sendThread.isAlive()) {
            try {
                recvInput();
                Thread.sleep(100);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            out.close();
            in.close();
            socket.close();
            System.out.println("Conexion cerrada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvInput() throws IOException, ClassNotFoundException {
        String inputStr = in.readLine();
        System.out.println("recv: " + inputStr);
        if (inputStr != null) {
            Input i = Input.fromString(inputStr);
            server.addInput(i);
        }
    }

    private void sendInputs() {
        while (!socket.isClosed()) {
            List<Input> inputsCopy = new ArrayList<>(server.getInputs());
            System.out.println(inputsCopy.size() + " inputs" + inputsCopy.size() + "sizes");
            for (Input input : inputsCopy) {
                String inputStr = input.toString();
                try {
                    System.out.println("send: "+ inputStr);
                    out.println(inputStr);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
