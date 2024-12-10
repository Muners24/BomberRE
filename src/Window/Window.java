package Window;

import Bomber.Bomb;
import Bomber.BombMenu;
import Bomber.Scene;
import CONST.PLAYER_CONST;
import CONST.WINDOW_CONST;
import Game.*;
import Bomber.Player;
import Game.Vector2;
import Texture.TextureManager;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.util.freetype.*;

import java.awt.*;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private static long window;

    public void runGame() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");


        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }



    protected void loop() {

        BombMenu menu = new BombMenu(window);

        while (!glfwWindowShouldClose(window)) {


            String result = menu.run();

            if (result.equals("esc")) {
                break;
            }

            Scene.destroy();
            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player(PLAYER_CONST.SPAWN_TL, new Input(0,window,true)));
            players.add(new Player(PLAYER_CONST.SPAWN_TR, new Input(0,window,false)));
            Scene scene = Scene.getInstance();

            while (!players.get(0).lose() && !players.get(1).lose() && !glfwWindowShouldClose(window))
            {

                System.out.println(players.get(0).getDeaths() + ", " + players.get(1).getDeaths());
                glfwPollEvents();

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                scene.draw();

                for (Player p : players) {
                    p.update();
                    p.draw();

                    if(p.setBomb()){
                        Bomb newBomb = new Bomb(p.getPos());
                        Thread bombThread = new Thread(newBomb);
                        bombThread.start();
                    }
                }
                glfwSwapBuffers(window);
            }

            menu.end();

            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }



    protected void initLWJGL() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // Opcional, los valores por defecto ya están establecidos
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // La ventana estará oculta después de su creación
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // La ventana será redimensionable

        window = glfwCreateWindow(WINDOW_CONST.WIDTH, WINDOW_CONST.HEIGHT, WINDOW_CONST.TITLE, NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        // Hacer el contexto OpenGL actual
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Habilitar v-sync

        // Hacer la ventana visible
        glfwShowWindow(window);

        GL.createCapabilities();

        glClearColor(0.35f, 0.35f, 0.35f, 0.35f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WINDOW_CONST.WIDTH, WINDOW_CONST.HEIGHT, 0, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public static void main(String[] args) throws IOException {
        Window w = new Window();
        w.initLWJGL();
        w.runGame();
    }
}
