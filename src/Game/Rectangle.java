package Game;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle {
    public float x;
    public float y;
    public float width;
    public float height;

    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(float r, float g, float b) {
        float left = x;
        float right = x + width;
        float top = y;
        float bottom = y+height;

        glBegin(GL_QUADS);
        glColor3f(r, g, b);

        glVertex2f(left, top);   // Vértice superior izquierdo
        glVertex2f(right, top);  // Vértice superior derecho
        glVertex2f(right, bottom); // Vértice inferior derecho
        glVertex2f(left, bottom);  // Vértice inferior izquierdo

        glEnd();
    }

}
