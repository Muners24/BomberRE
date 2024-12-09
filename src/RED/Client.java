package RED;

import Bomber.Bomb;
import Bomber.Player;
import Bomber.Scene;
import CONST.PLAYER_CONST;
import CONST.SERVER_CONST;
import CONST.WINDOW_CONST;
import Game.Input;
import Window.Window;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Client extends Window {

    private String serverAddress;
    private int port;
    private Socket socket;
    private Input input;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<Input> otherInputs;


    private ArrayList<Player> players;

    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.otherInputs = new ArrayList<>();
        this.players = new ArrayList<>();

        try {
            initClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectionLoop() {
        try {

            Thread sendThread = new Thread(this::sendInput);
            Thread recvThread = new Thread(this::recvInputs);

            recvThread.start();
            sendThread.start();

            while (sendThread.isAlive() && recvThread.isAlive()) {
                Thread.sleep(100);  // Espera de 100 ms
            }

            in.close();
            out.close();
            socket.close();

        } catch (IOException | InterruptedException  e) {
            e.printStackTrace();
        }
    }

    private void initClient() throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void initPlayer() throws IOException {
        try {
            String idStr = in.readLine();
            int id = Integer.parseInt(idStr);

            input = new Input(id, this.window);

            players.add(new Player(id, PLAYER_CONST.SPAWN_TL,window,input));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendInput() {
        try {
            while (!socket.isClosed()) {
                input.input();
                out.println(input.toString());
                //System.out.println("send: " + input.toString());
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void recvInputs() {
        try {
            int i=0;
            while (!socket.isClosed()) {
                System.out.println(i);
                i++;
                String inputStr = in.readLine();
                System.out.println("recv" + inputStr);
                Input input = Input.fromString(inputStr);

                addInputUsingIterator(input);

                Thread.sleep(100); // Retardo de 100 ms
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected synchronized void loop() {

        ArrayList<Bomb> bombs = new ArrayList<Bomb>();
        Scene map = Scene.getInstance();

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            map.draw();

            // Iteración sobre las bombas utilizando Iterator
            Iterator<Bomb> bombIterator = bombs.iterator();
            while (bombIterator.hasNext()) {
                Bomb b = bombIterator.next();
                b.update(players);
                b.draw();
                if (b.endExplode()) {
                    bombIterator.remove(); // Removemos la bomba si ya terminó de explotar
                }
            }

            // Iteración sobre los jugadores utilizando Iterator
            Iterator<Player> playerIterator = players.iterator();
            while (playerIterator.hasNext()) {
                Player p = playerIterator.next();
                p.update(bombs);
                p.draw();

                if (p.setBomb()) {
                    bombs.add(new Bomb(p.getPos())); // Añadimos una nueva bomba si el jugador coloca una
                }
            }

            glfwSwapBuffers(window);
        }
    }

    public void addInputUsingIterator(Input newInput) {
        if (newInput.getId() == input.getId()) {
            return;
        }

        boolean find = false;
        Iterator<Input> iterator = otherInputs.iterator();
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
            otherInputs.add(newInput);
            Player player = new Player(newInput.getId(), PLAYER_CONST.SPAWN_TL, this.window, newInput);
            players.add(player);
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", SERVER_CONST.PORT);
        client.initLWJGL();
        client.initPlayer();

        Thread connection = new Thread(client::connectionLoop);
        connection.start();

        try
        {
            Thread.sleep(100);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        client.runGame();
    }
}
