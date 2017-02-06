package de.vogler.pong.controller;

import de.vogler.pong.Game;
import de.vogler.pong.constants.GameMode;
import de.vogler.pong.constants.GameState;
import de.vogler.pong.model.GameModel;

import java.awt.event.KeyEvent;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class GameController {
	/**
	 * Referenz aufs Spiel
	 */
	private Game game;

	/**
	 * Schwierigkeit des Spiels
	 */
	private int gameDifficulty;
	/**
	 * Spielmodus
	 */
	private GameMode gameMode;

	/**
	 * Erstellt einen neuen Spielcontroller mit einer Referenz auf das Spiel
	 * @param game
	 */
	public GameController(Game game) {
		this.game = game;


		gameDifficulty = 1;
		gameMode = GameMode.PLAYER;
	}


	/**
	 * Hier wird auf einmalige Tastenanschläge reagiert.
	 *
	 * @param keyEvent
	 */
	public void updateKeysTyped(KeyEvent keyEvent) {

		// Wir betrachten die GameStates unterschiedlich. Hauptsächlich wird die Menüsteuerung
		// gemacht.
		if (game.getGameState() == GameState.RUNNING) {


			if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
				game.setGameState(GameState.PAUSED);
			}
		} else {
			if (keyEvent.getKeyCode() == KeyEvent.VK_1) {
				gameDifficulty = 1;
				game.getGameModel().setDifficulty(gameDifficulty);
			}
			if (keyEvent.getKeyCode() == KeyEvent.VK_2) {
				gameDifficulty = 2;
				game.getGameModel().setDifficulty(gameDifficulty);
			}
			if (keyEvent.getKeyCode() == KeyEvent.VK_3) {
				gameDifficulty = 3;
				game.getGameModel().setDifficulty(gameDifficulty);
			}

			if (keyEvent.getKeyCode() == KeyEvent.VK_7) {
				gameMode = GameMode.KI;
				remakeGame();

			}
			if (keyEvent.getKeyCode() == KeyEvent.VK_9) {
				gameMode = GameMode.PLAYER;
				remakeGame();

			}


			if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
				game.setGameState(GameState.RUNNING);
			}

			if (keyEvent.getKeyCode() == KeyEvent.VK_R) {
				remakeGame();
			}
		}

	}

	/**
	 * Ermöglicht ein Neustarten des Spiels
	 */
	public void remakeGame() {
		game.setGameModel(new GameModel(game, gameMode, gameDifficulty));
	}


}
