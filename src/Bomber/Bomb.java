package Bomber;

import CONST.BOMB_CONST;
import CONST.WINDOW_CONST;
import Game.CollisionShapes;
import Game.Rectangle;
import Game.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class Bomb {

    private ArrayList<Rectangle> hitbox;
    private Vector2 center;
    private int timer;
    private Rectangle pos;

    public Bomb(Vector2 pos) {
        timer = 0;
        CollisionShapes cs = new CollisionShapes();
        Scene map = Scene.getInstance();
        ArrayList<Rectangle> casillas = map.getCasillas();

        for (Rectangle r : casillas) {
            if (cs.checkCollisionPointRec(pos, r)) {
                this.pos = r;
                break;
            }
        }

        assert this.pos != null;
        this.center = new Vector2(this.pos.x + this.pos.width/2, this.pos.y+this.pos.height/2);
        this.hitbox = new ArrayList<Rectangle>();
    }

    public void draw() {
        this.pos.draw(0,0,1);
        for (Rectangle r : hitbox) {
            r.draw(1,0.2f,0.2f);
        }
    }

    public void update() {
        timer++;
        explode();
    }


    public boolean endExplode(){
        return (timer > BOMB_CONST.TTL + BOMB_CONST.EXPLDOE_TLL);
    }

    //recibira a los jugadores, si la explosion toco a alguno, su vida se restara
    private void explode() {
        if(timer >= BOMB_CONST.TTL){
            if (hitbox.isEmpty()) {
                hitbox = getExpodeHitBox();
            }
        }
    }

    private ArrayList<Rectangle> getExpodeHitBox() {
        int CASILLA = WINDOW_CONST.BOX;

        Vector2 left = findExplodeLimit(-CASILLA,0);
        Vector2 right = findExplodeLimit(CASILLA,0);
        Vector2 top = findExplodeLimit(0,-CASILLA);
        Vector2 bottom = findExplodeLimit(0,CASILLA);

        float rad = BOMB_CONST.HITBOX_RAD;
        ArrayList<Rectangle> hitBoxes = new ArrayList<>();

        hitBoxes.add(new Rectangle(
                left.x-rad,
                left.y-rad,
                right.x-left.x+2*rad,
                2*rad));

        hitBoxes.add(new Rectangle(
                top.x-rad,
                top.y-rad,
                2*rad,
                bottom.y-top.y+2*rad));

        return hitBoxes;

    }

    public Vector2 findExplodeLimit(float stepX,float stepY) {
        Scene map = Scene.getInstance();
        ArrayList<Obstacle> obstacles = map.getObstacles();
        CollisionShapes cs = new CollisionShapes();

        int CASILLA = WINDOW_CONST.BOX;
        int LEFT = WINDOW_CONST.BORDE_LEFT+CASILLA;
        int RIGHT = WINDOW_CONST.BORDE_RIGHT;
        int TOP = WINDOW_CONST.BORDE_TOP +CASILLA;
        int BOTTOM = WINDOW_CONST.BORDE_BOTTOM;

        boolean tope = false;
        Vector2 limit = new Vector2(center.x,center.y);

        int i = 0;
        while (!tope && i < BOMB_CONST.EXPLDOE_LONG) {

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
}
