package Bomber;

import CONST.WINDOW_CONST;
import Game.Rectangle;

import java.util.ArrayList;

public class Scene {

    private static Scene instance = null;
    ArrayList<Rectangle> casillas;
    ArrayList<Obstacle> borde;
    ArrayList<Obstacle> obstacles;

    private Scene(){
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

}
