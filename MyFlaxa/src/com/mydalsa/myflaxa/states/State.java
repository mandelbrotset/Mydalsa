package com.mydalsa.myflaxa.states;

import com.mydalsa.myflaxa.MyFlaxaGame;

public abstract class State {
	
	protected MyFlaxaGame game;
	
	public State(MyFlaxaGame game){
		this.game = game;
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
}
