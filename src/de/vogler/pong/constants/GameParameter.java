package de.vogler.pong.constants;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class GameParameter {


    /**
     * Radius des Balls
     */
    public static int BALL_RADIUS = 5;

    /**
     * Abstand zwischen Spielfeldende und Stick
     */
    public static int STICK_GAP = 10;

    /**
     * Stick-Breite
     */
    public static int STICK_WIDTH = 8;

    /**
     * Stick-Höhe
     */
    public static int STICK_HEIGHT = 50;

    /**
     * Spielfeld-Höhe
     */
    public static int GAME_HEIGHT = 600;

    /**
     * Spielfeld-Breite
     */
    public static int GAME_WIDTH= 1000;
    /**
     * Die Höhe des Fensters
     */
    public static int HEIGHT = 800;

    /**
     * Die Breite des Fensters
     */
    public static int WIDTH = 1200;

    /**
     * Frames per Second
     */
    public static int FPS = 30;

    /**
     * Standardbeschleunigung des Balls
     */
    public static float BALL_X_VELOCITY = 0.08f;

    /**
     * Standardbeschleunigung des Balls
     */
    public static float BALL_Y_VELOCITY = 0.12f;

    /**
     * Konstante, die angibt, wie stark der Spin des Balls beim Auftreffen auf den Schläger in
     * Bewegung verstärkt wird.
     */
    public static double STICK_SPIN_ON_TOUCH = 0.25d;

    /**
     * Ballgeschwindigkeit am Anfang
     */
    public static float BALL_SPEED = 0.5f;

    /**
     * Faktor der Reaktionsgeschwindigkeit
     */
    public static float KI_ACTION_FACTOR = 2f;
    /**
     * Aktionsradius der KI
     */
    public static float  KI_ACTION_RADIUS = 200;

    /**
     * Konstante für die Geschwindigkeit des Balls
     */
    public static float STICK_SPEED_DIVISOR = 6f;
    /**
     * Konstante für die Geschwindigkeit des Balls
     */
    public static float STICK_SPEED = 0.25f;
}
