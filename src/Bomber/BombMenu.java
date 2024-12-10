package Bomber;


import CONST.WINDOW_CONST;
import Texture.TextureManager;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class BombMenu {

    private long window;
    private int backgroundID;

    public BombMenu(long window) {
        this.window = window;
    }

    public String run() {

        backgroundID  = TextureManager.loadTexture("Res/img.png");
        boolean esc = false;
        boolean space = false;

        while (!esc && !space){

            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            TextureManager.drawTexture(backgroundID,0,0, WINDOW_CONST.WIDTH, WINDOW_CONST.HEIGHT);


            esc = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS);
            space = (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS);
            glfwSwapBuffers(window);
        }

        if(esc)
        {
            return "esc";
        }
        return "space";
    }

    public void end()
    {
        backgroundID  = TextureManager.loadTexture("Res/Animations.png");

        glfwPollEvents();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        TextureManager.drawTexture(backgroundID,0,0, WINDOW_CONST.WIDTH, WINDOW_CONST.HEIGHT);

        glfwSwapBuffers(window);

    }
}