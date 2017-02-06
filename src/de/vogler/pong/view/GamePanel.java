package de.vogler.pong.view;

import de.vogler.pong.Game;
import de.vogler.pong.constants.GameMode;
import de.vogler.pong.constants.GameParameter;
import de.vogler.pong.constants.GameState;
import de.vogler.pong.model.GameModel;
import de.vogler.pong.model.PongBall;
import de.vogler.pong.model.PongStick;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class GamePanel extends JPanel {

	Game game;
	GameModel gameModel;

	Font fontMenu;
	Font fontGame;

	FontMetrics fontMetricsMenu;
	FontMetrics fontMetricsGame;
	int gameStartPointX;
	int gameStartPointY;
	/**
	 * Erstellt ein neues GamePanel, welches den ganzen Quatsch zeichnet.
	 *
	 * @param game
	 */
	public GamePanel(Game game) {
		super();
		this.game = game;
		this.fontMenu = new Font("monospaced", Font.BOLD, 50);
		this.fontGame = new Font("monospaced", Font.BOLD, 60);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.gameModel = game.getGameModel();

		gameStartPointX = (GameParameter.WIDTH - GameParameter.GAME_WIDTH) / 2;
		gameStartPointY = (GameParameter.HEIGHT - GameParameter.GAME_HEIGHT) / 2;

		int surroundingWidth = GameParameter.STICK_WIDTH;
		/*
		Wichtig ist, dass aus den Daten, die im vorherigen Schritt des Gameloops verändert
        wurden, gezeichnet wird und nicht während des Updaten gezeichnet wird.
        Der Drawing-Thread läuft async und untriggered, da müssen wir uns nicht drum kümmern.
         */

		if (fontMetricsMenu == null || fontMetricsGame == null) {
			fontMetricsMenu = g.getFontMetrics(fontMenu);
			fontMetricsGame = g.getFontMetrics(fontGame);
		}

		// Unterscheidung in den GameStates
		if (game.getGameState() == GameState.RUNNING) {

			g.setColor(Color.RED);
			//g.drawRect(0, 0, GameParameter.WIDTH /2, GameParameter.HEIGHT);

			g.setColor(Color.WHITE);

			g.fillRect(gameStartPointX - surroundingWidth, gameStartPointY -
							surroundingWidth, GameParameter.GAME_WIDTH + 2 * surroundingWidth,
					GameParameter.GAME_HEIGHT + 2 * surroundingWidth);
			g.setColor(Color.BLACK);
			g.fillRect(gameStartPointX, gameStartPointY, GameParameter.GAME_WIDTH, GameParameter
					.GAME_HEIGHT);

			g.setColor(Color.WHITE);
			g.fillRect(gameStartPointX + (GameParameter.GAME_WIDTH - GameParameter.STICK_WIDTH)
							/ 2,
					gameStartPointY, GameParameter.STICK_WIDTH, GameParameter.GAME_HEIGHT);

			g.setColor(Color.BLACK);
			int partsOfSep = 10;
			int widthPerTile = GameParameter.GAME_HEIGHT / partsOfSep;
			for (int i = 0; i < partsOfSep; i++) {
				g.fillRect(gameStartPointX + (GameParameter.GAME_WIDTH - GameParameter.STICK_WIDTH)
								/ 2, gameStartPointY + (i * widthPerTile) + widthPerTile / 4,
						GameParameter
								.STICK_WIDTH, widthPerTile / 2);
			}


			g.setColor(Color.WHITE);
			PongStick pongStickOne = gameModel.getPongStickOne();
			Rectangle pongStickOneHitbox = pongStickOne.getStickHitbox();
			g.fillRect((int) (gameStartPointX + pongStickOneHitbox.getX()), (int) (gameStartPointY +
					pongStickOneHitbox.getY()), (int) pongStickOneHitbox.getWidth(), (int)
					pongStickOneHitbox.getHeight());


			PongStick pongStickTwo = gameModel.getPongStickTwo();
			Rectangle pongStickTwoHitbox = pongStickTwo.getStickHitbox();
			g.fillRect((int) (gameStartPointX + pongStickTwoHitbox.getX()), (int) (gameStartPointY +
					pongStickTwoHitbox.getY()), (int) pongStickTwoHitbox.getWidth(), (int)
					pongStickTwoHitbox.getHeight());

			PongBall pongBall = gameModel.getPongBall();
			Ellipse2D pongCircle = pongBall.getEllipse();
			g.fillOval(gameStartPointX + (int) pongCircle.getX(), gameStartPointY + (int) pongCircle
					.getY(), (int)
					pongCircle.getWidth(), (int) pongCircle.getHeight());

			g.setFont(fontGame);


			String displayString = gameModel.getPoints()[0] + " : " + gameModel.getPoints()[1];
			g.drawString(displayString, getXValue(fontMetricsGame, displayString),
					gameStartPointY - 30);
			g.setFont(fontMenu);
		} else if (game.getGameState() == GameState.STARTING || game.getGameState() == GameState.PAUSED) {
			// Menu oder Options Screen
			g.setFont(fontMenu);
			g.setColor(Color.WHITE);
			String displayString = game.getGameState() == GameState.STARTING ?
					"Ping-Pong-Master-2D" :
					"Pausiert";
			g.drawString(displayString, getXValue(fontMetricsMenu, displayString), 60);

			int xOffset = 150;

			GameMode mode = gameModel.getGameMode();
			String[] difficultStrings = {"1 - Schwierigkeit 1", "2 - Schwierigkeit 2", "3 - Schwierigkeit 3"};
			for (int i = 0; i < difficultStrings.length; i++) {
				g.setColor(gameModel.getDifficulty() == i + 1 ? Color.RED : Color.WHITE);
				g.drawString(difficultStrings[i], getXValue(fontMetricsMenu, difficultStrings[i]),
						gameStartPointY + 100 + (i * 60));

			}

			g.setColor(mode == GameMode.KI ? Color.RED : Color.WHITE);
			String ki = "7 - Spiel gegen KI";
			g.drawString(ki, getXValue(fontMetricsMenu, ki), gameStartPointY + 280);
			g.setColor(mode == GameMode.PLAYER ? Color.RED : Color.WHITE);
			String player = "9 - Spiel gegen Player";
			g.drawString(player, getXValue(fontMetricsMenu, player), gameStartPointY +
					340);

			g.setColor(Color.WHITE);
			String displayString2 = "LEER - Spiel " + (game.getGameState() == GameState.STARTING ?
					"starten" : "fortsetzen");
			g.drawString(displayString2, 30, GameParameter.HEIGHT - 30);

			g.setColor(Color.WHITE);
			String displayString3 = "R - Reset";
			g.drawString(displayString3, GameParameter.WIDTH - fontMetricsMenu.stringWidth
					(displayString3) - 35, GameParameter.HEIGHT - 30);
		}

	}

	/**
	 * Berechnung der Breite eines Stings mithilfe einer Font
	 * @param metrics FontMetrics
	 * @param string String
	 * @return Anfangsposition des Strings beim Zeichnen
	 */
	public int getXValue(FontMetrics metrics, String string) {
		int displayWidth = metrics.stringWidth(string);
		return gameStartPointX + (GameParameter.GAME_WIDTH - displayWidth) / 2;
	}
}
