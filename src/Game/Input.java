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

    public Input(int id,long window) {
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
        up = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS;
        down = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS;
        left = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS;
        right = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS;
        key = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_X) == GLFW.GLFW_PRESS;


    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(id);
        builder.append("/");
        builder.append(up);
        builder.append("/");
        builder.append(down);
        builder.append("/");
        builder.append(left);
        builder.append("/");
        builder.append(right);
        builder.append("/");
        builder.append(key);
        builder.append("/");
        return builder.toString();
    }

    public static Input fromString(String str) {
        String[] parts = str.split("/");

        if (parts.length != 6) {
            throw new IllegalArgumentException("La cadena no tiene el formato esperado.");
        }

        int id = Integer.parseInt(parts[0]);
        boolean up = Boolean.parseBoolean(parts[1]);
        boolean down = Boolean.parseBoolean(parts[2]);
        boolean left = Boolean.parseBoolean(parts[3]);
        boolean right = Boolean.parseBoolean(parts[4]);
        boolean key = Boolean.parseBoolean(parts[5]);

        Input input = new Input(id, 0);
        input.up = up;
        input.down = down;
        input.left = left;
        input.right = right;
        input.key = key;

        return input;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isKey() {
        return key;
    }
}
