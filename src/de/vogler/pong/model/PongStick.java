package de.vogler.pong.model;

import de.vogler.pong.constants.GameParameter;
import de.vogler.pong.math.Vector2D;

import java.awt.*;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class PongStick {

	/**
	 * Rechteck, welcher die Kollision berechnet
	 */
	private Rectangle stickHitbox;
	/**
	 * Bewegungsvektor des Sticks
	 */
	private Vector2D velocityVector;


	/**
	 * Erstellt einen neuen Sticks
	 * @param posX Startposition X
	 * @param posY Startposition Y
	 */
	public PongStick(int posX, int posY) {

		this.stickHitbox = new Rectangle(posX, posY, GameParameter.STICK_WIDTH, GameParameter.STICK_HEIGHT);
		this.velocityVector = new Vector2D();

	}

	public Rectangle getStickHitbox() {
		return stickHitbox;
	}

	public Vector2D getVelocityVector() {
		return velocityVector;
	}

	public void setVelocityVector(Vector2D velocityVector) {
		this.velocityVector = velocityVector;
	}

	/**
	 * Bewegung des Sticks
	 * @param passedTime Vergangene Zeit seit dem letzten Update
	 */
	public void moveStick(long passedTime) {

		if(this.velocityVector.getY() == 0){
			return;
		}

		Vector2D velocityVector = new Vector2D(this.velocityVector);
		velocityVector.scale(passedTime);

		if (stickHitbox.y + velocityVector.getY() <= 0) {
			stickHitbox.setLocation(stickHitbox.x, 0);
		} else if ((stickHitbox.y + velocityVector.getY() + GameParameter.STICK_HEIGHT) >
				GameParameter.GAME_HEIGHT) {
			stickHitbox.setLocation(stickHitbox.x, GameParameter.GAME_HEIGHT - GameParameter.STICK_HEIGHT);
		} else {
			stickHitbox.setLocation(stickHitbox.x, (int) (stickHitbox.y + velocityVector.getY()));
		}

	}
}
