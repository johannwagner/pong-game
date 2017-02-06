package de.vogler.pong;

import de.vogler.pong.constants.GameMode;
import de.vogler.pong.constants.GameParameter;
import de.vogler.pong.constants.GameState;
import de.vogler.pong.controller.GameController;
import de.vogler.pong.controller.GameKeyListener;
import de.vogler.pong.model.GameModel;
import de.vogler.pong.view.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class Game {

	/**
	 * Fenster, in dem das Spiel zu sehen ist.
	 */
	private JFrame gameFrame;

	/**
	 * Feld, in dem gezeichnet wird und was im JFrame liegt.
	 */
	private GamePanel gamePanel;

	/**
	 * Modell des aktuellen Spiels
	 */
	private GameModel gameModel;

	/**
	 * KeyListener, der auf die Tastendrücke antwortet und sie ins Modell durchschiebt
	 */
	private GameKeyListener gameKeyListener;

	/**
	 * GameController, der die Eingaben des Spielers ins Modell umsetzt.
	 */
	private GameController gameController;

	/**
	 * Aktueller Status des Spiels.
	 */
	private GameState gameState;


	private long lastUpdateTime;

	/**
	 * GameContainerKlasse.
	 * Hier werden alle Referenzen(Speicherverweise, Adressen) auf alles Mögliche gehalten und der
	 * Game-Loop läuft hier.
	 *
	 * @param frame Frame, auf dem das Spiel angezeigt werden soll.
	 */
	public Game(JFrame frame) {
		this.gameFrame = frame;

		// Aufbau des Fensters und Anknüpfen der Listenen
		this.gameModel = new GameModel(this, GameMode.PLAYER, 1);
		this.gameController = new GameController(this);
		this.gameKeyListener = new GameKeyListener(this);

		this.gamePanel = new GamePanel(this);
		this.gameFrame.requestFocus();
		this.gameFrame.setResizable(true);
		this.gameFrame.addKeyListener(gameKeyListener);
		this.gameFrame.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				GameParameter.WIDTH = gameFrame.getWidth() - 7;
				GameParameter.HEIGHT = gameFrame.getHeight() - 30;
			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}

		});

		// Die Verschiebungen hier sind willkürlich gewählt, da ich unfähig bin mit Layouts
		// umzugehen.
		this.gameFrame.setPreferredSize(new Dimension(GameParameter.WIDTH + 7, GameParameter.HEIGHT + 30));

		this.gamePanel.setPreferredSize(new Dimension(GameParameter.WIDTH, GameParameter.HEIGHT));
		this.gamePanel.setBackground(Color.black);

		this.gameState = GameState.STARTING;


		// Building Window.
		frame.setLayout(new BorderLayout());
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.pack();

		frame.setVisible(true);

	}

	/**
	 * Methode zum Starten des Game-Loops.
	 * Game-Loop endet, wenn gameState = GameState.CLOSING ist.
	 */
	public void start() {
		// Standard Game Loop

		long startTime, endTime, diffTime, sleepTime;
		lastUpdateTime = System.currentTimeMillis();

		while (gameState != GameState.CLOSING) {

			// Das Verarbeiten der Eingaben geschiet bei Java in Listenern.

			startTime = System.currentTimeMillis();

			if (gameState == GameState.RUNNING) {

				//Updaten des Models
				gameModel.updateModel();
				//Berechnen des Models
				gameModel.tickModel(startTime - lastUpdateTime);
			}

			//Zeichnen des Models
			gameFrame.repaint();

			// Zeitberechnungen
			lastUpdateTime = startTime;
			endTime = System.currentTimeMillis();
			diffTime = endTime - startTime;
			sleepTime = (1000 / GameParameter.FPS) - diffTime;

			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				sleepTime *= -1;

				// Skippen ist eigentlich nicht richtig.. Formulierung muss man überdenken.

				System.out.println("Skipped " + sleepTime + " ms..");
			}


		}


	}

	public JFrame getGameFrame() {
		return gameFrame;
	}


	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public GameModel getGameModel() {
		return gameModel;
	}

	/**
	 * Alle Klassen, die darauf referenzieren, kümmern sich darum, dass sie immer die aktuelle
	 * Referenz da haben und nicht auf alten Referenzen rumeiern.
	 *
	 * @param gameModel Neues GameModel
	 */
	public void setGameModel(GameModel gameModel) {
		this.gameModel = gameModel;

	}

	public GameKeyListener getGameKeyListener() {
		return gameKeyListener;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public GameController getGameController() {
		return gameController;
	}

}
