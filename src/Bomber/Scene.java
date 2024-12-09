package Bomber;

import CONST.WINDOW_CONST;
import Game.Rectangle;
import Game.Vector2;
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

    private long window;

    private Scene() {
        this.window = Window.getWindow();
        bombHitBoxes = new ArrayList<>();
        bombBoxes = new ArrayList<>();
        casillas = new ArrayList<>();
        borde = new ArrayList<>();
        obstacles = new ArrayList<>();
        initObstacles();
    }

    public static Scene getInstance(){
        if (instance == null) {
            instance = new Scene();
        }
        return instance;
    }

    public void draw(){
        for(Obstacle obs : obstacles){
            obs.draw();
        }

        for(Obstacle obs : borde){
            obs.draw();
        }

        for(Rectangle cas : casillas){
            cas.draw(0.3f,0.3f,0.3f);
        }

        for(Rectangle bomb : bombBoxes)
        {
            bomb.draw(0,0,1);
        }

        for (Rectangle expode : bombHitBoxes)
        {
            expode.draw(1,0.2f,0.2f);
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

    public void addBomb(Vector2 pos)
    {
        int BOX = WINDOW_CONST.BOX;
        bombBoxes.add(new Rectangle(pos.x,pos.y,BOX,BOX));
    }

    public void removeBomb(Vector2 pos)
    {
        Iterator<Rectangle> iterator = bombBoxes.iterator();
        while (iterator.hasNext()) {
            Rectangle bomb = iterator.next();
            if (pos.x == bomb.x && pos.y == bomb.y) {
                iterator.remove();
            }
        }
    }

    public void addExplode(Rectangle explode)
    {
        this.bombHitBoxes.add(explode);
    }

    public void removeExplode(Rectangle explode)
    {
        this.bombHitBoxes.remove(explode);
    }

    public ArrayList<Rectangle> getExplodeHitBox()
    {
        return this.bombHitBoxes;
    }

}
