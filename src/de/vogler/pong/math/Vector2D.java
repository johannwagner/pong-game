package de.vogler.pong.math;

/**
 * Pong
 * Alle Rechte vorbehalten.
 */
public class Vector2D {


	private float x, y;

	/**
	 * Erstellt einen leeren Vektor.
	 */
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Erstellt einen Vektor mit x und y
	 * @param x Erster Wert
	 * @param y Zweiter Wert
	 */
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Kopiert einen bestehnden Vektor
	 * @param velocityVector Vektor zum Kopieren
	 */
	public Vector2D(Vector2D velocityVector) {
		this.x = velocityVector.x;
		this.y = velocityVector.y;
	}

	/**
	 * Addieren eines Vektors
	 * @param vector Vektor
	 */
	public void add(Vector2D vector) {
		this.x += vector.x;
		// this.x = this.x + vector.x

		this.y += vector.y;
	}

	/**
	 * Substrahieren eines Vektors
	 * @param vector Vektors
	 */
	public void sub(Vector2D vector) {
		vector.scale(-1);
		this.add(vector);
	}

	/**
	 * Skalieren eines Vektors
	 * @param scaleFactor Skalierung
	 */
	public void scale(float scaleFactor) {
		this.x *= scaleFactor;
		this.y *= scaleFactor;
	}

	/**
	 * Normalisierung des Vektors
	 * @param factor Länge des Vektors nach der Normalisierung
	 */
	public void normalize(float factor) {
		this.scale(factor / length());
	}

	/**
	 * Länge des Vektors
 	 * @return Länge
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}


}
