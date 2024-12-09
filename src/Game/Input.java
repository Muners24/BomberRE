package Game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.io.Serializable;

public class Input implements Serializable {
    private long window;

    private int id;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean key;
    public boolean tipoInput;

    public Input(int id,long window,boolean wasd) {
        tipoInput = wasd;
        this.id = id;
        this.window = window;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.key = false;
    }

    public int getId(){
        return id;
    }

    public void input(){
        if (tipoInput)
        {
            up = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS;
            down = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS;
            left = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS;
            right = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS;
            key = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_X) == GLFW.GLFW_PRESS;
        }
        else
        {
            up = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS;
            down = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS;
            left = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS;
            right = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS;
            key = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_KP_0) == GLFW.GLFW_PRESS;
        }
    }

}
