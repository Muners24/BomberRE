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

    public Rectangle copy(){
        return new Rectangle(x, y, width, height);
    }

    public Vector2 getCenter(){
        return new Vector2(x+width/2, y+height/2);
    }

    public void repelRectangle(Rectangle rec){
        MathB m = new MathB();
        Vector2 center = rec.getCenter();
        Vector2 distance = m.vectorDistancePointPoint(getCenter(),center);

        float LEFT = x;
        float RIGHT = x + width;
        float TOP = y;
        float BOTTOM = y + height;

        if (distance.y <= distance.x){
            if (center.x  < LEFT)
                rec.x = x - rec.width;

            if (center.x > RIGHT)
                rec.x = x + width;
            return;
        }
        if (center.y < TOP)
            rec.y = y - rec.height;

        if (center.y > BOTTOM)
            rec.y = y + height;
    }
}
