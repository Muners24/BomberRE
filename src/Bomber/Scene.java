package Bomber;

import CONST.WINDOW_CONST;
import Game.Rectangle;
import Game.Vector2;
import Texture.TextureManager;
import Window.Window;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Iterator;

public class Scene {

    private static Scene instance = null;
    ArrayList<Rectangle> casillas;
    ArrayList<Obstacle> borde;
    ArrayList<Obstacle> obstacles;
    ArrayList<Rectangle> bombBoxes;
    ArrayList<Rectangle> bombHitBoxes;
    private static int timer;

    private int bombtextureID;
    private int explosionTextureID;
    private int centerTextureID;
    private int bomb1textureID;
    private int bomb2textureID;

    private int bombTickCounter = 0;


    private Scene() {
        bombHitBoxes = new ArrayList<>();
        bombBoxes = new ArrayList<>();
        casillas = new ArrayList<>();
        borde = new ArrayList<>();
        obstacles = new ArrayList<>();
        initObstacles();

        this.bombtextureID = TextureManager.loadTexture("Res/Bomb.png");
        this.explosionTextureID = TextureManager.loadTexture("Res/Explotion.png");
        this.centerTextureID = TextureManager.loadTexture("Res/Explotion_Center.png");
        this.bomb1textureID = TextureManager.loadTexture("Res/Bomb2.png");
        this.bomb2textureID = TextureManager.loadTexture("Res/Bomb4.png");

    }

    public static Scene getInstance(){
        if (instance == null) {
            instance = new Scene();
        }
        return instance;
    }

    public static void destroy() {
        if (instance != null) {
            instance.clearResources();
            instance = null;
        }
    }

    private void clearResources() {
        if (casillas != null) casillas.clear();
        if (borde != null) borde.clear();
        if (obstacles != null) obstacles.clear();
        if (bombBoxes != null) bombBoxes.clear();
        if (bombHitBoxes != null) bombHitBoxes.clear();
        timer = 0;
    }

    public void draw() {



        for (Obstacle obs : obstacles) {
            obs.draw();
        }

        for (Obstacle obs : borde) {
            obs.draw();
        }

        for (Rectangle cas : casillas) {
            cas.draw(0.5f, 1, 0.2f);
        }

        bombTickCounter++;

        if (bombTickCounter >= 50) {
            bombTickCounter = 0;
        }

        Iterator<Rectangle> bombIterator = bombBoxes.iterator();
        while (bombIterator.hasNext()) {
            Rectangle bomb = bombIterator.next();
            if (bombTickCounter < 25) {
                TextureManager.drawTexture(bomb1textureID, bomb.x, bomb.y, bomb.width, bomb.height);
            } else {
                TextureManager.drawTexture(bomb2textureID, bomb.x, bomb.y, bomb.width, bomb.height);
            }
        }

        Iterator<Rectangle> expodeIterator = bombHitBoxes.iterator();
        while (expodeIterator.hasNext()) {
            Rectangle expode = expodeIterator.next();
            TextureManager.drawTexture(explosionTextureID, expode.x, expode.y, expode.width, expode.height);
        }
    }

    private void initObstacles(){
        final int CASILLA = WINDOW_CONST.BOX;
        final int LEFT = WINDOW_CONST.LEFT;
        final int RIGHT = WINDOW_CONST.RIGHT;
        final int TOP = WINDOW_CONST.TOP;
        final int BOTTOM = WINDOW_CONST.BOTTOM;

        int x, y;
        for (x = LEFT; x <= RIGHT; x += CASILLA)
        {
            borde.add(new Obstacle(x,TOP,CASILLA,CASILLA));
            borde.add(new Obstacle(x,BOTTOM,CASILLA,CASILLA));
        }
        for (y = TOP+CASILLA; y < BOTTOM ;y += CASILLA)
        {
            borde.add(new Obstacle(LEFT,y,CASILLA,CASILLA));
            borde.add(new Obstacle(RIGHT,y,CASILLA,CASILLA));
        }

        x = CASILLA*2;
        y = CASILLA*2;
        for(int i = 0;i<9;i++){
            for(int j = 0;j<13;j++){
                if (j%2 == 0 || i%2 == 0){
                    casillas.add(new Rectangle(x+j*CASILLA,y+i*CASILLA,CASILLA,CASILLA));
                    continue;
                }
                obstacles.add(new Obstacle(x+j*CASILLA,y+i*CASILLA,CASILLA,CASILLA));
            }
        }
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<Rectangle> getCasillas() {
        return casillas;
    }

    public ArrayList<Rectangle> getBombBoxes() {
        return bombBoxes;
    }

    public ArrayList<Rectangle> getBombHitBoxes()
    {
        return  bombHitBoxes;
    }

    public void addBomb(Rectangle bomb)
    {
        bombBoxes.add(bomb);
    }

    public synchronized void removeBomb(Rectangle bomb)
    {
        bombBoxes.remove(bomb);
    }

    public void addExplode(Rectangle explode)
    {
        this.bombHitBoxes.add(explode);
    }

    public synchronized void removeExplode(Rectangle explode)
    {
        this.bombHitBoxes.remove(explode);
    }

    public ArrayList<Rectangle> getExplodeHitBox()
    {
        return this.bombHitBoxes;
    }

}
