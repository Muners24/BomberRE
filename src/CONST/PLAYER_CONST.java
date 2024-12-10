package CONST;

import  Game.*;
import java.util.Vector;

public class PLAYER_CONST {
    public static final int HEIGHT  = WINDOW_CONST.BOX-4;
    public static final int WIDTH  = WINDOW_CONST.BOX-4;
    public static final float SPEED = 3;

    public static final int INVULERABILITY = 200;
    public static final int MAX_VIDA = 1;
    public static final int DEATH_NUM = 5;

    public static final Vector2 SPAWN_TL = new Vector2(WINDOW_CONST.LEFT,WINDOW_CONST.TOP);
    public static final Vector2 SPAWN_TR = new Vector2(WINDOW_CONST.RIGHT,WINDOW_CONST.TOP-WINDOW_CONST.BOX);
    public static final Vector2 SPAWN_BL = new Vector2(WINDOW_CONST.LEFT,WINDOW_CONST.BOTTOM-WINDOW_CONST.BOX);
    public static final Vector2 SPAWN_BR = new Vector2(WINDOW_CONST.RIGHT-WINDOW_CONST.BOX,WINDOW_CONST.BOTTOM-WINDOW_CONST.BOX);
}