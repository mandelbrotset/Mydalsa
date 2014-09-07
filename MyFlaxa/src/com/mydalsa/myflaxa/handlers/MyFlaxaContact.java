package com.mydalsa.myflaxa.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mydalsa.myflaxa.states.GameState;

public class MyFlaxaContact implements ContactListener  {
	GameState game;
	public MyFlaxaContact(GameState game) {
		this.game = game;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	@Override
	public void endContact(Contact contact) {
		// System.out.println("End contact" +
		// body.getLinearVelocity().x);
	}

	@Override
	public void beginContact(Contact contact) {
		//game.isDead = true;
		/*
		// System.out.println("Begin contact" +
		// body.getLinearVelocity().x);
		if (contact.getFixtureA().isSensor()) {
			birdVelocity = -birdVelocity;
			System.out.println("SENSOR");
		}

		if (contact.getFixtureB().isSensor()) {
			birdVelocity = -birdVelocity;
			System.out.println("SENSOR");
		} */
	}

}
