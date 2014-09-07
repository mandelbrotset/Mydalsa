package com.mydalsa.myflaxa.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mydalsa.myflaxa.states.GameState;

public class Player {
	private Bird bird;
	private String name;
	private long id;
	
	public Player(Bird bird, String name, long id, GameState state){
		this.bird = bird;
		this.name = name;
		this.id = id;
		state.addSprite(bird);
	}
	
	
	public void handleInput(){
		if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
			bird.switchDirection();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			bird.flyUp();
		}

	}
	
	public Vector2 getPosition(){
		return bird.getPosition();
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
