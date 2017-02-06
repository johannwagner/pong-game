package de.vogler.pong.model;

import de.vogler.pong.Game;
import de.vogler.pong.constants.GameMode;
import de.vogler.pong.constants.GameParameter;
import de.vogler.pong.math.Vector2D;

import java.awt.event.KeyEvent;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class GameModel {

	private int difficulty;
	/**
	 * Referenz auf die Game-Instance
	 */
	private Game game;
	/**
	 * Gedrückte Tasten, basierend auf KeyEvent IDs
	 */
	private boolean[] keysPressed;

	/**
	 * Aktueller Spielmodus
	 */
	private GameMode gameMode;

	/**
	 * PongStick 1 (links)
	 */
	private PongStick pongStickOne;

	/**
	 * PongStick 2 (rechts)
	 */
	private PongStick pongStickTwo;

	/**
	 * Ball
	 */
	private PongBall pongBall;


	/**
	 * AI
	 */
	private GameIntelligence gameIntelligence;

	/**
	 * Punkte des Spiels
	 */
	private int[] playerPoints;


	/**
	 * Hier liegt das Modell des Spiels.
	 *
	 * @param game GameContainerKlasse
	 */
	public GameModel(Game game, GameMode gameMode, int difficulty) {
		this.game = game;
		this.keysPressed = new boolean[256];
		this.difficulty = difficulty;

		// Initialisierung der Spielelemente
		this.pongStickOne = new PongStick(GameParameter.STICK_GAP, (GameParameter
				.GAME_HEIGHT - GameParameter.STICK_HEIGHT) / 2);

		this.pongStickTwo = new PongStick(GameParameter.GAME_WIDTH - GameParameter.STICK_WIDTH -
				GameParameter.STICK_GAP, (GameParameter.GAME_HEIGHT - GameParameter.STICK_HEIGHT) / 2);

		this.pongBall = new PongBall(GameParameter.GAME_WIDTH / 2, GameParameter.GAME_HEIGHT / 2,
				GameParameter.BALL_RADIUS);

		this.gameIntelligence = new GameIntelligence(this, pongStickTwo);
		this.gameMode = gameMode;

		this.playerPoints = new int[2];

	}

	/**
	 * Updaten der keysPressed aus dem Listener
	 *
	 * @param keysPressed
	 */
	public void updateKeysPressed(boolean[] keysPressed) {
		this.keysPressed = keysPressed;
	}


	/**
	 * Hier kann auf dauerhaft gedrückte Tasten reagiert werden.
	 */
	public void updateModel() {

		float moveFloat1 = (keysPressed[KeyEvent.VK_W] ? -GameParameter.STICK_SPEED : 0) +
				(keysPressed[KeyEvent.VK_S] ? GameParameter.STICK_SPEED : 0);
		float moveFloat2;

		// Abhängig davon, ob wir gegen Player oder KI spielen
		if (gameMode == GameMode.PLAYER) {
			moveFloat2 = (keysPressed[KeyEvent.VK_UP] ? -GameParameter.STICK_SPEED : 0) +
					(keysPressed[KeyEvent.VK_DOWN] ? GameParameter.STICK_SPEED : 0);

		} else {
			moveFloat2 = gameIntelligence.calculateMovement().getY();
		}


		pongStickOne.setVelocityVector(new Vector2D(0, moveFloat1));
		pongStickTwo.setVelocityVector(new Vector2D(0, moveFloat2));
	}

	/**
	 * Aufgerufen aus dem Game-Loop.
	 * Das Model wird, abhängig von der vergangenden Zeit verändert.
	 *
	 * @param passedTimeInMs Vergangene Zeit seit dem letzten Update in ms.
	 */
	public void tickModel(long passedTimeInMs) {

		PongStick[] pongSticks = new PongStick[]{pongStickOne, pongStickTwo};
		pongBall.checkCollision(passedTimeInMs, pongSticks);

		pongBall.checkBallInGame(playerPoints);

		pongStickOne.moveStick(passedTimeInMs);
		pongStickTwo.moveStick(passedTimeInMs);
		pongBall.moveBall(passedTimeInMs);


	}

	public PongStick getPongStickOne() {
		return pongStickOne;
	}

	public PongStick getPongStickTwo() {
		return pongStickTwo;
	}

	public PongBall getPongBall() {
		return pongBall;
	}

	public int[] getPoints() {
		return playerPoints;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
