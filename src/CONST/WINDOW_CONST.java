package CONST;

import Game.Vector2;

public class WINDOW_CONST {
    public static final String TITLE = "BOMBER RE";
    public static final int WIDTH = 1020;
    public static final int HEIGHT = 780;
    public static final int BOX = 60;
    public static final int RIGHT = WIDTH - BOX*2;
    public static final int BOTTOM = HEIGHT - BOX*2;
    public static final int LEFT = BOX;
    public static final int TOP = BOX;

    public static Vector2 OUT_SCREEN  = new Vector2(WIDTH, HEIGHT);
}
