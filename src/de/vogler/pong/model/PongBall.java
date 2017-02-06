package de.vogler.pong.model;

import de.vogler.pong.constants.GameParameter;
import de.vogler.pong.math.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class PongBall {

	private Ellipse2D hitboxBall;
	private Vector2D velocityVector;

	private int[] startPos;


	/**
	 * Erstellt das Modell eines Balls
	 *
	 * @param posX   Start-Position X
	 * @param posY   Start-Position Y
	 * @param radius Radius des Balls
	 */
	public PongBall(int posX, int posY, int radius) {
		hitboxBall = new Ellipse2D.Float(posX, posY, 2 * radius, 2 * radius);
		startPos = new int[]{posX, posY};

		resetBall();
	}

	/**
	 * Ball anhand des Velocity-Vektors bewegen
	 *
	 * @param passedTime Vergangene Zeit
	 */
	public void moveBall(long passedTime) {

		Vector2D scaledVector = new Vector2D(velocityVector);
		scaledVector.scale(passedTime);

		double newX = hitboxBall.getMinX() + scaledVector.getX();
		double newY = hitboxBall.getMinY() + scaledVector.getY();

		//System.out.println("new X: " + newX + " - new Y:" + newY);

		if (newY <= 0) {
			hitboxBall.setFrame(newX, 0, hitboxBall.getWidth(), hitboxBall.getHeight());

			velocityVector = new Vector2D(velocityVector.getX(), velocityVector.getY() * -1);
		} else if (newY + hitboxBall.getHeight() > GameParameter.GAME_HEIGHT) {
			hitboxBall.setFrame(newX, GameParameter.GAME_HEIGHT - hitboxBall.getHeight(),
					hitboxBall.getWidth(), hitboxBall.getHeight());

			velocityVector = new Vector2D(velocityVector.getX(), velocityVector.getY() * -1);
		} else {
			hitboxBall.setFrame(newX, newY, hitboxBall.getWidth(), hitboxBall.getHeight());
		}

	}

	/**
	 * Überprüfen, ob der Ball die PongSticks berührt.
	 *
	 * @param passedTimeInMs Vergangene Zeit
	 * @param pongSticks     Alle Sticks
	 */
	public void checkCollision(long passedTimeInMs, PongStick[] pongSticks) {

		Vector2D scaledVector = new Vector2D(velocityVector);
		scaledVector.scale(passedTimeInMs);


		Ellipse2D predictedPosition = new Ellipse2D.Double(hitboxBall.getX() + scaledVector.getX()
				, hitboxBall.getY() + scaledVector.getY(),
				hitboxBall.getWidth(), hitboxBall.getHeight());

		for (PongStick actualStick : pongSticks) {
			if (predictedPosition.intersects(actualStick.getStickHitbox())) {

				Vector2D veloVec = actualStick.getVelocityVector();
				veloVec.scale((float) (Math.random() * GameParameter.STICK_SPIN_ON_TOUCH));
				Vector2D newVelocityVector = new Vector2D(velocityVector.getX() * -1,
						velocityVector.getY());
				newVelocityVector.add(veloVec);
				this.setVelocityVector(newVelocityVector);
			}
		}
	}

	/**
	 * Wir schauen, ob der Ball noch im Spielfeld ist.
	 *
	 * @param playerPoints Feld der Spielerpunkte
	 */
	public void checkBallInGame(int[] playerPoints) {
		if (hitboxBall.getMinX() > GameParameter.GAME_WIDTH) {
			playerPoints[0] += 1;
			this.resetBall();

		} else if (hitboxBall.getMaxX() < 0) {
			playerPoints[1] += 1;
			this.resetBall();
		}

	}

	/**
	 * Setzt den Bewegungsvektor des Balls neu.
	 * @param velocityVector Bewegungsvektor des Balls
	 */
	private void setVelocityVector(Vector2D velocityVector) {
		velocityVector.normalize(GameParameter.BALL_SPEED);
		this.velocityVector = velocityVector;
	}

	/**
	 * Zurücksetzen des Balls
 	 */

	private void resetBall() {

		this.setVelocityVector(new Vector2D(Math.random() < 0.5 ? -GameParameter.BALL_X_VELOCITY :
				GameParameter.BALL_X_VELOCITY,
				(float) (GameParameter.BALL_Y_VELOCITY * (Math.random() * 2 - 1))));


		hitboxBall.setFrame(startPos[0], startPos[1], hitboxBall.getWidth(), hitboxBall.getHeight());

	}

	public Ellipse2D getEllipse() {
		return hitboxBall;
	}
}
