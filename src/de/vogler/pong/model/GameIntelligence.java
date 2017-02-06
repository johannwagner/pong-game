package de.vogler.pong.model;

import de.vogler.pong.constants.GameParameter;
import de.vogler.pong.math.Vector2D;
import de.vogler.pong.model.GameModel;
import de.vogler.pong.model.PongBall;
import de.vogler.pong.model.PongStick;

import java.awt.*;
import java.util.Vector;

import  java.lang.Math.*;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class GameIntelligence {

	GameModel model;
	PongStick moveStick;

	/**
	 * Erstellt neues KI-Objekt
	 * @param model GameModel
	 * @param stickToMove Referenz auf den Stick, welcher bewegt werden soll.
	 */
	public GameIntelligence(GameModel model, PongStick stickToMove){
		this.moveStick = stickToMove;
		this.model = model;



	}

	/**
	 * Berechnungsmethode
	 * @return Neuer Vektor, welcher die Bewegung des Balls beschreibt.
	 */
	public Vector2D calculateMovement(){

		int difficulty = model.getDifficulty();

		// KI fängt erst an, zu steuern, wenn der Ball in der Nähe des Schlägers ist.
		Rectangle stickHitbox = moveStick.getStickHitbox();
		PongBall pongBall = model.getPongBall();

		if(Math.abs(stickHitbox.getX() - pongBall.getEllipse().getX()) > GameParameter
				.KI_ACTION_RADIUS  * difficulty /  GameParameter.KI_ACTION_FACTOR) {
			// Abbrechen, wenn zu weit weg. Leerer Vektor impliziert keine Bewegung.
			return new Vector2D();
		}

		double diff = (pongBall.getEllipse().getCenterY() - stickHitbox.getCenterY());

		if(Math.abs(diff) < GameParameter.STICK_HEIGHT / 2){
			return new Vector2D();
		}
		Vector2D velocityVector = new Vector2D(0, (float) diff);
		velocityVector.normalize((difficulty/GameParameter.STICK_SPEED_DIVISOR));

		return velocityVector;





	}
}
