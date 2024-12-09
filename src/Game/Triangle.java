package Game;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;

public class Triangle {
    public Vector2 p1, p2, p3;

    public Triangle(Vector2 p1, Vector2 p2, Vector2 p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Vector2 getVertex1() {
        return p1;
    }
    public Vector2 getVertex2() {
        return p2;
    }

    public Vector2 getVertex3() {
        return p3;
    }

    public void draw(float r, float g, float b) {
        float x1 = p1.getX();
        float y1 = p1.getY();
        float x2 = p2.getX();
        float y2 = p2.getY();
        float x3 = p3.getX();
        float y3 = p3.getY();

        glBegin(GL_TRIANGLES);

        glColor4f(1, 1, 1,0);  // Color RGB (rojo, verde, azul)

        glVertex2f(x1, y1);  // Vértice 1
        glVertex2f(x2, y2);  // Vértice 2
        glVertex2f(x3, y3);  // Vértice 3

        glEnd();
    }

}
