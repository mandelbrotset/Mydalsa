package com.mydalsa.myflaxa.handlers;

import java.util.Stack;

import com.mydalsa.myflaxa.MyFlaxaGame;
import com.mydalsa.myflaxa.states.GameState;
import com.mydalsa.myflaxa.states.State;

public class StateHandler {

	public static final int GAME_STATE = 1;

	private Stack<State> stateStack;
	private MyFlaxaGame game;

	public StateHandler(MyFlaxaGame game) {
		stateStack = new Stack<State>();
		this.game = game;
		pushState(GAME_STATE);
	}

	public void pushState(int state) {
		stateStack.push(getState(GAME_STATE));
	}
	
	public void popState(){
		stateStack.pop().dispose();
	}
	
	public void update(float dt){
		stateStack.peek().update(dt);
	}
	
	public void render(){
		stateStack.peek().render();
	}
	
	public MyFlaxaGame getGame() {
		return game;
	}
	
	public State getState(int state) {
		switch (state) {
		case 1:
			return new GameState(game, true, false);
		}
		return null;
	}
	


}
