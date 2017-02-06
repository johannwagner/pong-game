package de.vogler.pong.controller;

import de.vogler.pong.Game;
import de.vogler.pong.model.GameModel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */

/**
 * GameKeyListener - Wird an das Spielfenster und leitet die Tastendrücke weiter.
 */
public class GameKeyListener implements KeyListener {

	private Game game;
	private GameModel gameModel;
	private GameController gameController;
	private boolean[] keyPressedArray;

	/**
	 * Erstellt einen neuen GameKeyListener.
	 * @param game Spielreferenz
	 */
	public GameKeyListener(Game game){
		this.game = game;
		this.keyPressedArray = new boolean[256];
	}


	@Override
	public void keyTyped(KeyEvent e) {
		//game.updateKeysTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e){
		gameModel = game.getGameModel();
		keyPressedArray[e.getKeyCode()]  = true;
		gameModel.updateKeysPressed(keyPressedArray);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gameModel = game.getGameModel();
		gameController = game.getGameController();
		keyPressedArray[e.getKeyCode()]  = false;
		gameController.updateKeysTyped(e);
		gameModel.updateKeysPressed(keyPressedArray);
	}
}
