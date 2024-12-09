package Bomber;

import CONST.BOMB_CONST;
import CONST.PLAYER_CONST;
import CONST.WINDOW_CONST;
import Game.*;
import Texture.TextureManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class Player {

    private final Rectangle pos;
    private final Vector2 vel;
    private final float speed;
    private int vida;
    private Input keys;
    private int atk_timer;
    private int dmg_timer;
    private final Vector2 spawn;
    private boolean colision_with_solid_rec;
    private int deaths;

    private int textureID;
    private float keyPressTimer;
    private boolean keyPressed;
    private int textureIDstop;
    private int textureIDd1;
    private int textureIDd2;
    private int textureIDd3;

    private int textureIDu1;
    private int textureIDu2;
    private int textureIDu3;

    private int textureIDr1;
    private int textureIDr2;
    private int textureIDr3;

    private int textureIDl1;
    private int textureIDl2;
    private int textureIDl3;


    private int currentWalkTexture;  // Controlador para la textura actual de caminar
    private float walkTimer;  // Temporizador para cambiar las texturas


    public Player(Vector2 spawn,Input input) {
        this.deaths = 0;
        this.keys = input;

        this.pos = new Rectangle(
                spawn.x,
                spawn.y,
                PLAYER_CONST.WIDTH,
                PLAYER_CONST.HEIGHT);

        this.speed = PLAYER_CONST.SPEED;
        this.vel = new Vector2(0,0);

        this.vida = PLAYER_CONST.MAX_VIDA;
        this.atk_timer = BOMB_CONST.COLD_DOWN;
        this.dmg_timer = PLAYER_CONST.INVULERABILITY;

        this.spawn = spawn;
        this.colision_with_solid_rec = false;

        this.textureIDstop = TextureManager.loadTexture("Res/d1.png");

        this.textureIDd1 = TextureManager.loadTexture("Res/d1.png");
        this.textureIDd2 = TextureManager.loadTexture("Res/d2.png");
        this.textureIDd3 = TextureManager.loadTexture("Res/d3.png");


        this.textureIDu1 = TextureManager.loadTexture("Res/u1.png");
        this.textureIDu2 = TextureManager.loadTexture("Res/u2.png");
        this.textureIDu3 = TextureManager.loadTexture("Res/u3.png");

        this.textureIDr1 = TextureManager.loadTexture("Res/r1.png");
        this.textureIDr2 = TextureManager.loadTexture("Res/r2.png");
        this.textureIDr3 = TextureManager.loadTexture("Res/r3.png");

        this.textureIDr1 = TextureManager.loadTexture("Res/r1.png");
        this.textureIDr2 = TextureManager.loadTexture("Res/r2.png");
        this.textureIDr3 = TextureManager.loadTexture("Res/r3.png");

        this.textureIDl1 = TextureManager.loadTexture("Res/l1.png");
        this.textureIDl2 = TextureManager.loadTexture("Res/l2.png");
        this.textureIDl3 = TextureManager.loadTexture("Res/l3.png");



        this.textureID = textureIDstop;  // Inicialmente con la textura de descanso

        this.keyPressTimer = 0;
        this.keyPressed = false;
        this.walkTimer = 0;
        this.currentWalkTexture = 1;

    }

    public Rectangle getRec(){
        return pos;
    }

    public void update(){
        timers();
        keys.input();
        calculateVel();
        collisionObstacle();
        mov();
        colision_with_solid_rec = false;
        collisionBomb();
        collisonExplode();
        adjustPositionBorde();

        // Verificar si se presiona alguna tecla de movimiento
        boolean isMoving = keys.up || keys.down || keys.left || keys.right;

        // Si se presiona una tecla, reiniciar el contador y cambiar la textura
        if (isMoving) {
            if (!keyPressed) {
                keyPressTimer = 0;  // Reiniciar el contador cuando se presiona una tecla
                keyPressed = true;
            }
        } else {
            keyPressed = false;  // Si no se presiona ninguna tecla, detener el contador
            walkTimer = 0;  // Reiniciar el temporizador de caminar cuando no se mueve
            currentWalkTexture = 1;  // Volver a la primera textura de caminar
            textureID = textureIDstop;  // Cambiar a la textura de descanso
        }

        // Si se está moviendo, aumentar el contador
        if (keyPressed) {
            keyPressTimer += 2;  // Aumentar el tiempo transcurrido
        }

        // Cambiar la textura de caminar según el tiempo transcurrido
        if (keyPressTimer > 10) {
            walkTimer += 1;
            if (walkTimer >= 5) {
                walkTimer = 0;
                currentWalkTexture++;

                if (currentWalkTexture > 3) {
                    currentWalkTexture = 1;
                }
            }
        }

        // Asignar la textura según la dirección y la secuencia de caminata
        if (keys.up) {
            switch (currentWalkTexture) {
                case 1:
                    textureID = textureIDu1;  // Textura de caminar hacia arriba (1)
                    break;
                case 2:
                    textureID = textureIDu2;  // Textura de caminar hacia arriba (2)
                    break;
                case 3:
                    textureID = textureIDu3;  // Textura de caminar hacia arriba (3)
                    break;
                default:
                    textureID = textureIDstop;  // Textura de descanso si no está caminando
                    break;
            }
        } else if (keys.down) {
            switch (currentWalkTexture) {
                case 1:
                    textureID = textureIDd1;  // Textura de caminar hacia abajo (1)
                    break;
                case 2:
                    textureID = textureIDd2;  // Textura de caminar hacia abajo (2)
                    break;
                case 3:
                    textureID = textureIDd3;  // Textura de caminar hacia abajo (3)
                    break;
                default:
                    textureID = textureIDstop;  // Textura de descanso si no está caminando
                    break;
            }
        } else if (keys.left) {
            switch (currentWalkTexture) {
                case 1:
                    textureID = textureIDl1;  // Textura de caminar hacia izquierda (1)
                    break;
                case 2:
                    textureID = textureIDl2;  // Textura de caminar hacia izquierda (2)
                    break;
                case 3:
                    textureID = textureIDl3;  // Textura de caminar hacia izquierda (3)
                    break;
                default:
                    textureID = textureIDstop;  // Textura de descanso si no está caminando
                    break;
            }
        } else if (keys.right) {
            switch (currentWalkTexture) {
                case 1:
                    textureID = textureIDr1;  // Textura de caminar hacia derecha (1)
                    break;
                case 2:
                    textureID = textureIDr2;  // Textura de caminar hacia derecha (2)
                    break;
                case 3:
                    textureID = textureIDr3;  // Textura de caminar hacia derecha (3)
                    break;
                default:
                    textureID = textureIDstop;  // Textura de descanso si no está caminando
                    break;
            }
        }
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
        final float LEFT = WINDOW_CONST.LEFT + BOX;
        final float RIGHT = WINDOW_CONST.RIGHT;
        final float TOP = WINDOW_CONST.TOP + BOX;
        final float BOTTOM = WINDOW_CONST.BOTTOM;

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
        CollisionShapes cs = new CollisionShapes();

        if (colision_with_solid_rec){
            for (Obstacle obstacle : obstacles) {
                Rectangle rec = obstacle.getRec();
                if(cs.checkCollisionRecs(pos,rec))
                    rec.repelRectangle(pos);
            }
            return;
        }

        for (Obstacle obs : obstacles) {
            Rectangle rec = obs.getRec();
            final float LEFT = pos.x;
            final float RIGHT = pos.x + pos.width;
            final float TOP = pos.y;
            final float BOTTOM = pos.y + pos.height;

            if (keys.down) {
                Triangle tri = obs.getTriangleTop();
                tri.draw(1,0,0);
                boolean left = cs.checkCollisionPointTriangle(new Vector2(LEFT, BOTTOM), tri);
                boolean right = cs.checkCollisionPointTriangle(new Vector2(RIGHT, BOTTOM), tri);

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
                boolean left = cs.checkCollisionPointTriangle(new Vector2(LEFT, TOP), tri);
                boolean right = cs.checkCollisionPointTriangle(new Vector2(RIGHT, TOP), tri);

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
                boolean top = cs.checkCollisionPointTriangle(new Vector2(LEFT, TOP), tri);
                boolean bottom = cs.checkCollisionPointTriangle(new Vector2(LEFT, BOTTOM), tri);

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
                boolean top = cs.checkCollisionPointTriangle(new Vector2(RIGHT, TOP), tri);
                boolean bottom = cs.checkCollisionPointTriangle(new Vector2(RIGHT, BOTTOM), tri);

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
        TextureManager.drawTexture(textureID, pos.x, pos.y, pos.width, pos.height);  // Usar la textura correspondiente
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

    private void timers(){
        if (atk_timer <= BOMB_CONST.COLD_DOWN)
            atk_timer++;

        if (dmg_timer <= PLAYER_CONST.INVULERABILITY)
            dmg_timer++;
    }

    public void damage(){
        if (dmg_timer > PLAYER_CONST.INVULERABILITY){
            dmg_timer = 0;
            vida -= BOMB_CONST.DAMAGE;

            if(vida < 0){
                death();
            }
        }
    }

    public void death(){
        vida = PLAYER_CONST.MAX_VIDA;
        pos.x = spawn.x;
        pos.y = spawn.y;
        deaths++;
    }

    private void collisionBomb() {
        MathB m = new MathB();
        CollisionShapes cs = new CollisionShapes();
        Scene scene = Scene.getInstance();
        ArrayList<Rectangle> bombHitboxesCopy = new ArrayList<>(scene.getBombBoxes());

        for (Rectangle rec : bombHitboxesCopy) {
            if (m.distancePointPoint(rec.getCenter(), pos.getCenter()) < pos.width / 2 + rec.width / 2 - PLAYER_CONST.SPEED)
                continue;

            if (cs.checkCollisionRecs(rec, pos)) {
                colision_with_solid_rec = true;
                rec.repelRectangle(pos);
            }
        }
    }

    private void collisonExplode()
    {
        CollisionShapes cs = new CollisionShapes();
        Scene scene = Scene.getInstance();
        ArrayList<Rectangle> exp = new ArrayList<>(scene.getExplodeHitBox());
        for (Rectangle explode : exp) {
            if (cs.checkCollisionRecs(this.pos,explode))
                damage();
        }
    }

    public int getDeaths()
    {
        return this.deaths;
    }

    public boolean lose()
    {
        return  this.deaths > PLAYER_CONST.DEATH_NUM;
    }

}
