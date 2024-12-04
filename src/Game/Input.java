package Game;

import org.lwjgl.glfw.GLFW;

public class Input {
    private long window;

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean key;

    public Input(long window) {
        this.window = window;
        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;
        this.key = false;
    }

    public void input(){
        up = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS;
        down = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS;
        left = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS;
        right = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS;
        key = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_X) == GLFW.GLFW_PRESS;
    }
}
