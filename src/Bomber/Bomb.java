package Bomber;

import CONST.BOMB_CONST;
import CONST.WINDOW_CONST;
import Game.CollisionShapes;
import Game.Rectangle;
import Game.Vector2;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class Bomb implements Runnable {

    private ArrayList<Rectangle> hitboxes;
    private Vector2 center;
    private int timer;
    private Rectangle pos;

    public Bomb(Vector2 pos) {
        hitboxes = new ArrayList<Rectangle>();

        CollisionShapes cs = new CollisionShapes();
        Scene scene = Scene.getInstance();
        ArrayList<Rectangle> casillas = scene.getCasillas();

        for (Rectangle r : casillas) {
            if (cs.checkCollisionPointRec(pos, r)) {
                this.pos = r.copy();
                scene.addBomb(new Vector2(this.pos.x,this.pos.y));
                break;
            }
        }

        assert this.pos != null;
        this.center = new Vector2(this.pos.x + this.pos.width/2, this.pos.y+this.pos.height/2);
    }

    private void calculateExpodeHitBox() {
        int CASILLA = WINDOW_CONST.BOX;

        Vector2 left = findExplodeLimit(-CASILLA,0);
        Vector2 right = findExplodeLimit(CASILLA,0);
        Vector2 top = findExplodeLimit(0,-CASILLA);
        Vector2 bottom = findExplodeLimit(0,CASILLA);

        float rad = BOMB_CONST.HITBOX_RAD;

        hitboxes.add(new Rectangle(
                left.x-rad,
                left.y-rad,
                right.x-left.x+2*rad,
                2*rad));

        hitboxes.add(new Rectangle(
                top.x-rad,
                top.y-rad,
                2*rad,
                bottom.y-top.y+2*rad));

    }

    public Vector2 findExplodeLimit(float stepX,float stepY) {
        Scene map = Scene.getInstance();
        ArrayList<Obstacle> obstacles = map.getObstacles();
        CollisionShapes cs = new CollisionShapes();

        int CASILLA = WINDOW_CONST.BOX;
        int LEFT = WINDOW_CONST.LEFT+ CASILLA;
        int RIGHT = WINDOW_CONST.RIGHT;
        int TOP = WINDOW_CONST.TOP + CASILLA;
        int BOTTOM = WINDOW_CONST.BOTTOM;

        boolean tope = false;
        Vector2 limit = new Vector2(center.x,center.y);

        int i = 0;
        while (!tope && i < BOMB_CONST.EXPLODE_LONG) {

            limit.x+=stepX;
            limit.y+=stepY;

            if(stepX != 0) {
                if (limit.x < LEFT)
                    tope = true;

                if(limit.x > RIGHT)
                    tope = true;

            }

            if(stepY != 0) {
                if (limit.y < TOP)
                    tope = true;

                if(limit.y > BOTTOM)
                    tope = true;

            }

            for (Obstacle o : obstacles) {
                if (cs.checkCollisionPointRec(limit,o.getRec())){
                    tope = true;
                    break;
                }
            }

            i++;
        }

        if (tope) {
            limit.x-=stepX;
            limit.y-=stepY;
        }


        return limit;
    }

    public Rectangle getRec(){
        return pos;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(BOMB_CONST.TTL_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Scene scene = Scene.getInstance();
        scene.removeBomb(new Vector2(pos.x,pos.y));

        calculateExpodeHitBox();
        for(Rectangle rec : hitboxes)
        {
            scene.addExplode(rec);
        }

        try {
            Thread.sleep(BOMB_CONST.EXPLODE_TTL_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Rectangle rec : hitboxes)
        {
            scene.removeExplode(rec);
        }
    }
}
