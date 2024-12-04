package Bomber;

import CONST.BOMB_CONST;
import CONST.PLAYER_CONST;
import CONST.WINDOW_CONST;
import Game.*;

import java.util.ArrayList;

public class Player {
    private long window;

    private int id;
    private Rectangle pos;
    private Vector2 vel;
    private float speed;
    private Input keys;
    private int atk_timer;


    public Player(int id, Vector2 pos,long window) {
        this.window = window;

        this.id = id;
        this.pos = new Rectangle(
                pos.x,
                pos.y,
                PLAYER_CONST.WIDTH,
                PLAYER_CONST.HEIGHT);

        this.speed = PLAYER_CONST.SPEED;
        this.vel = new Vector2(0,0);

        this.keys = new Input(this.window);
        this.atk_timer = 0;
    }

    public int getId(){
        return id;
    }

    public Rectangle getRec(){
        return pos;
    }

    public void update(){
        if (atk_timer <= BOMB_CONST.COLD_DOWN){
            atk_timer++;
        }
        keys.input();
        calculateVel();
        collisionObstacle();
        mov();
        adjustPositionBorde();
    }

    private void calculateVel() {
        float velX = 0;
        float velY = 0;

        if (keys.up) {
            if (keys.right) {
                velX = (float) Math.sin(Math.PI / 4) * speed;
                velY = -(float) Math.sin(Math.PI / 4) * speed;
            } else if (keys.left) {
                velX = -(float) Math.sin(Math.PI / 4) * speed;
                velY = -(float) Math.sin(Math.PI / 4) * speed;
            } else {
                velX = 0;
                velY = -speed;
            }
        }
        else if (keys.down) {
            if (keys.right) {
                velX = (float) Math.sin(Math.PI / 4) * speed;
                velY = (float) Math.sin(Math.PI / 4) * speed;
            } else if (keys.left) {
                velX = -(float) Math.sin(Math.PI / 4) * speed;
                velY = (float) Math.sin(Math.PI / 4) * speed;
            } else {
                velX = 0;
                velY = speed;
            }
        }
        else if (keys.right) {
            velX = speed;
            velY = 0;
        }
        else if (keys.left) {
            velX = -speed;
            velY = 0;
        }


        this.vel.x = velX;
        this.vel.y = velY;
    }

    private void mov(){
        this.pos.x += vel.x;
        this.pos.y += vel.y;
    }

    private void adjustPositionBorde(){
        final float BOX = WINDOW_CONST.BOX;
        final float LEFT = WINDOW_CONST.BORDE_LEFT + BOX;
        final float RIGHT = WINDOW_CONST.BORDE_RIGHT;
        final float TOP = WINDOW_CONST.BORDE_TOP + BOX;
        final float BOTTOM = WINDOW_CONST.BORDE_BOTTOM;

        if(this.pos.x < LEFT) {
            this.pos.x = LEFT;
        }

        if(this.pos.x + this.pos.width > RIGHT) {
            this.pos.x = RIGHT - this.pos.width;
        }

        if(this.pos.y < TOP) {
            this.pos.y = TOP;
        }

        if(this.pos.y + this.pos.height > BOTTOM) {
            this.pos.y = BOTTOM - this.pos.height;
        }

    }

    private void collisionObstacle(){
        Scene map = Scene.getInstance();
        ArrayList<Obstacle> obstacles = map.getObstacles();
        CollisionShapes C = new CollisionShapes();

        for (Obstacle obs : obstacles) {
            Rectangle rec = obs.getRec();
            final float LEFT = pos.x;
            final float RIGHT = pos.x + pos.width;
            final float TOP = pos.y;
            final float BOTTOM = pos.y + pos.height;

            if (keys.down) {
                Triangle tri = obs.getTriangleTop();
                tri.draw(1,0,0);
                boolean left = C.checkCollisionPointTriangle(new Vector2(LEFT, BOTTOM), tri);
                boolean right = C.checkCollisionPointTriangle(new Vector2(RIGHT, BOTTOM), tri);

                if (left && right) {
                    tri.draw(0,1,0);

                    vel.y = 0;
                    pos.y = rec.y - pos.height;
                    return;
                }

                if (left) {
                    tri.draw(0,1,0);

                    vel.y = 0;

                    pos.y = rec.y - pos.height;

                    if (vel.x >= 0)
                        vel.x = speed;

                    return;
                }

                if (right) {
                    tri.draw(0,1,0);

                    vel.y = 0;
                    pos.y = rec.y - pos.height;

                    if(vel.x <= 0)
                        vel.x = -speed;

                    return;
                }
            }

            if (keys.up) {
                Triangle tri = obs.getTriangleBottom();
                tri.draw(1,0,0);
                boolean left = C.checkCollisionPointTriangle(new Vector2(LEFT, TOP), tri);
                boolean right = C.checkCollisionPointTriangle(new Vector2(RIGHT, TOP), tri);

                if (left && right) {
                    tri.draw(0,1,0);

                    vel.y = 0;
                    pos.y = rec.y + rec.height;
                    return;
                }

                if (left) {
                    tri.draw(0,1,0);

                    vel.y = 0;
                    pos.y = rec.y + rec.height;

                    if (vel.x >= 0)
                        vel.x = speed;

                    return;
                }

                if (right) {
                    tri.draw(0,1,0);

                    vel.y = 0;
                    pos.y = rec.y + rec.height;

                    if (vel.x <= 0)
                        vel.x = -speed;

                    return;
                }
            }

            if (keys.left) {
                Triangle tri = obs.getTriangleRight();
                tri.draw(1,0,0);
                boolean top = C.checkCollisionPointTriangle(new Vector2(LEFT, TOP), tri);
                boolean bottom = C.checkCollisionPointTriangle(new Vector2(LEFT, BOTTOM), tri);

                if (top && bottom) {
                    vel.x = 0;
                    pos.x = rec.x + rec.width;
                    return;
                }


                if (top) {
                    tri.draw(0,1,0);

                    vel.x = 0;
                    pos.x = rec.x + rec.width;

                    if (vel.y >= 0)
                        vel.y = speed;

                    return;
                }

                if (bottom) {
                    tri.draw(0,1,0);

                    vel.x = 0;
                    pos.x = rec.x + rec.width;

                    if (vel.y <= 0)
                        vel.y = -speed;

                    return;
                }
            }

            if (keys.right) {
                Triangle tri = obs.getTriangleLeft();
                tri.draw(1,0,0);
                boolean top = C.checkCollisionPointTriangle(new Vector2(RIGHT, TOP), tri);
                boolean bottom = C.checkCollisionPointTriangle(new Vector2(RIGHT, BOTTOM), tri);

                if (top && bottom) {
                    tri.draw(0,1,0);

                    vel.x = 0;
                    pos.x = rec.x - pos.width;
                    return;
                }

                if (top) {
                    tri.draw(0,1,0);

                    vel.x = 0;
                    pos.x = rec.x - pos.width;

                    if (vel.y >= 0)
                        vel.y = speed;

                    return;
                }

                if (bottom) {
                    tri.draw(0,1,0);

                    vel.x = 0;
                    pos.x = rec.x - pos.width;

                    if (vel.y <= 0)
                        vel.y = -speed;

                    return;
                }
            }
        }
    }

    public void draw(){
        pos.draw(1,0,0);
    }

    public Vector2 getPos(){
        return new Vector2(pos.x+pos.width/2,pos.y+pos.height/2);
    }

    public boolean setBomb(){
        if (atk_timer < BOMB_CONST.COLD_DOWN)
            return false;

        if (keys.key){
            atk_timer = 0;
            return true;
        }
        return  false;
    }

}
