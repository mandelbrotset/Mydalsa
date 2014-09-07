package com.mydalsa.myflaxa.multiplayer;

import com.badlogic.gdx.math.Vector2;

public class MultiplayerSprite {
	private long id;
	private Vector2 position;
	private Vector2 velocity;
	private float angularVelocity;
	private float angle;

	public MultiplayerSprite(long id) {
		this.id = id;
	}

	public void setProperties(Vector2 position, Vector2 velocity, float angularVelocity, float angle) {
		this.position = position;
		this.velocity = velocity;
		this.angularVelocity = angularVelocity;
		this.angle = angle;
	}

	public long getId() {
		return id;
	}

	public float getAngle() {
		return angle;
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getAngularVelocity() {
		return angularVelocity;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	@Override
	public int hashCode() {
		return (int) (31 * this.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MultiplayerSprite)
			return ((MultiplayerSprite) obj).getId() == getId();

		return false;
	}
}
