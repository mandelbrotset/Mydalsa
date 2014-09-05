package com.mydalsa.myflaxa.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mydalsa.myflaxa.MyFlaxaGame;

public class GameState extends State {
	private SpriteBatch batch;
	
	public GameState(MyFlaxaGame game) {
		super(game);
		batch = game.getSpriteBatch();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		System.out.println(dt);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
