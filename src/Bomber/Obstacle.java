package Bomber;

import Game.Rectangle;
import Game.Triangle;
import Game.Vector2;

public class Obstacle {

    private Rectangle pos;
    private Triangle top;
    private Triangle bottom;
    private Triangle left;
    private Triangle right;

    public Obstacle(float x,float y,float width,float height) {
        this.pos = new Rectangle(x,y,width,height);
        Vector2 topLeft = new Vector2(pos.x,pos.y);
        Vector2 topRight = new Vector2(pos.x+width,pos.y);
        Vector2 bottomLeft = new Vector2(pos.x,pos.y+height);
        Vector2 bottomRight = new Vector2(pos.x+width,pos.y+height);
        Vector2 center = new Vector2(pos.x+width/2,pos.y+height/2);

        this.top = new Triangle(center, topRight, topLeft);
        this.bottom = new Triangle(center,bottomLeft, bottomRight);
        this.left = new Triangle(center, topLeft, bottomLeft);
        this.right = new Triangle(center, bottomRight, topRight);
    }

    public void draw(){
        this.pos.draw(0,0,0);
    }

    public Rectangle getRec() {
        return this.pos;
    }

    public Triangle getTriangleTop(){
        return this.top;
    }

    public Triangle getTriangleBottom(){
        return this.bottom;
    }

    public Triangle getTriangleLeft(){
        return this.left;
    }

    public Triangle getTriangleRight(){
        return this.right;
    }
}
