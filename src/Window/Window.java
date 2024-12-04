package Window;

import Bomber.Bomb;
import Bomber.Scene;
import CONST.WINDOW_CONST;
import Game.*;
import Bomber.Player;
import Game.Vector2;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Bomb> bombs = new ArrayList<Bomb>();
        players.add(new Player(0, new Vector2(10,10),this.window));
        Scene map = Scene.getInstance();

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            map.draw();

            Iterator<Bomb> iterator = bombs.iterator();
            while (iterator.hasNext()) {
                Bomb b = iterator.next();
                b.update();
                b.draw();
                if (b.endExplode()) {
                    iterator.remove();
                }
            }

            for (Player p : players) {
                p.update();
                p.draw();

                if(p.setBomb()){
                    bombs.add(new Bomb(p.getPos()));
                }
            }
            glfwSwapBuffers(window);
        }
    }



    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // Opcional, los valores por defecto ya están establecidos
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // La ventana estará oculta después de su creación
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // La ventana será redimensionable

        window = glfwCreateWindow(WINDOW_CONST.WIDTH, WINDOW_CONST.HEIGHT, WINDOW_CONST.TITLE, NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Configurar un callback para teclas
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        // Obtener el tamaño de la ventana y centrarla en la pantalla
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

    public static void main(String[] args) {
        new Window().run();
    }
}
